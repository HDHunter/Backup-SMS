package com.testSSM.test.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.testSSM.test.model.Contacts;
import com.testSSM.test.service.ContactsSer;
import com.testSSM.test.service.SmsSer;


@Controller
@RequestMapping("/img")
public class ImgCon {
	@Resource
	private ContactsSer contactsser;
	
	@RequestMapping(value = "img",produces = "application/json;charset=utf-8")
	@ResponseBody
	public int  sms(HttpServletRequest request, Model model){
		  List<Map<String,String>> list2 = new ArrayList<Map<String,String>>();	
		try {
				request.setCharacterEncoding("utf-8");
				try{  
		        	//获取流
		       InputStream 	requestInputStream = request.getInputStream();  
		        	
		            //接收流缓冲
		        	StringBuffer stringBuffer = new StringBuffer(); 
		            
		            //读取流
		            BufferedReader reader = new BufferedReader(new InputStreamReader(requestInputStream, "utf-8"));  
		           
		            //读入流，转换成字符串  
		            String readRequestInputStream;  
		            while ((readRequestInputStream = reader.readLine()) != null) {  
		            	stringBuffer.append(readRequestInputStream).append("\n");  
		            }  
		            String str = stringBuffer.toString();
		            str=filterEmoji(str);
		            List<Object> list = JSONArray.parseArray(str, Object.class);  
		          
		            for(Object obj : list){  
		                Map<String, String> item = (Map<String, String>) obj;  
		                list2.add(item);
		           }  
		           
		           

		            //关闭资源
		            reader.close();  
		            
		            } catch(Exception e){  
		                 
		            }  
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		Contacts contacts  = new Contacts();
		for(int i = 0 ;i<list2.size();i++){
			  Map<String,String> map2 = new HashMap<String,String>();
			  map2=list2.get(i);
			 
			  
			  	if(map2.get("number")==null||map2.get("number").equals("")||map2.get("number").equals("null")){
			  		contacts.setPhonenum("0");
			  	}else{
			  		 contacts.setPhonenum(map2.get("number"));
			  	}
				if(map2.get("name")==null||map2.get("name").equals("null")||map2.get("name").equals("")){
			  		contacts.setPhonename("");
			  	}else{
			  		 contacts.setPhonename(map2.get("name"));
			  	}
				int aa = contactsser.phone(contacts);
		       if(aa!=1||contactsser.phone(contacts)!=-1){
		    	   		if(aa!=-1){
		    	   			System.out.println("存储报错"+contactsser.phone(contacts));
		    	   			return 0;
		    	   		}
		       }
		}
		return 1;
//		
//		
//		
//		smS.setSmsdate(smsdate);
		
		//return smss.sms(smS);
	}
	 public static String filterEmoji(String source) {
		 String slipStr = "";
	        if(StringUtils.isNotBlank(source)){
	            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
	        }else{
	            return source;
	        }
	    }
	 
  
}
