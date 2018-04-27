package com.steam.android.androidsteam;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {
	
	
	 /**
	  * 描述：通过文件夹路径获取文件夹中所有文件信息
	  * @author yzh
	  * @param folderPath 文件夹路径
	  * @time 2018年4月16日14:10:28
	  * @return 获取到的文件或文件名
	  */
	public static List<FileInfo> getFileNameAndFileByPath(String folderPath) {
      
		//存储文件信息
		List<FileInfo> fileInfoList = new ArrayList<FileInfo>();
		
		//读取文件
         File files = new File(folderPath);
         
         //判断当前文件夹是否存在
         if (!files.exists()) {
             System.out.println("该文件夹："+folderPath + "不存在！");
             return null;
         }
         
         //依次读取文件并把文件信息保存到集合中
         File fileArray[] = files.listFiles();
		System.out.println(fileArray.toString());
         for (int i = 0; i < fileArray.length; i++) {
            File file = fileArray[i];
            	 fileInfoList.add(new FileInfo(file.getName(),file.length(),file.getPath()));
        }
		 return fileInfoList;
     }
	 
	 public static void main(String[] args) {
		System.out.println(getFileNameAndFileByPath("E:\\ddtechserver\\apache-tomcat-7.0.79jinjia\\webapps\\gsms\\bidFile").get(0).getFilePath());
	 }
	 
	 
	 
}	
