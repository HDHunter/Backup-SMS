package com.steam.android.androidsteam;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by beicky on 18/4/13.
 */

public class SetPost {
    public void HttpPostData(String json1) {
        try {
            StringEntity stringEntity = new StringEntity(json1, "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            HttpClient httpclient = new DefaultHttpClient();

            String uri = "http://192.168.1.106:8080/testSSM/sms/sms";

            HttpPost httppost = new HttpPost(uri);
            //添加http头
            //信息
            httppost.addHeader("Authorization", "token"); //认证token

            httppost.addHeader("User-Agent", "imgfornote");
            httppost.setHeader("Content-Type", "application/json; charset=UTF-8");
            httppost.setEntity(stringEntity);
            //http post的json数据格式：  {"name": "your name","parentId": "id_of_parent"}
            //System.out.println("test111111");

            //JSONObject obj=json1.getJSONObject(0);
//            obj.put("phonenum", "2121212121");
//            obj.put("sms_id", "51");
//            obj.put("sms", "asdad");
//            obj.put("smsdate", "12442");、


            //System.out.println("bbbbbbbbb");
            //System.out.println("eeeeeeeeee");
            HttpResponse response;
            System.out.println("rrrrrrrr");
            response = httpclient.execute(httppost);
            // System.out.println("mmmmmmmmm");
            //检验状态码，如果成功接收数据
            int code = response.getStatusLine().getStatusCode();
            System.out.println(code);
            if (code == 200) {
//              String rev = EntityUtils.toString(response.getEntity());//返回json格式： {"id": "27JpL~j4vsL0LX00E00005","version": "abc"}
//              System.out.println(rev+"11111111");
//              obj = new JSONObject(rev);
//
//              String id = obj.getString("id");
//              System.out.println(rev+"222222222");
//              String version = obj.getString("version");
//              System.out.println(rev+"333333333");
                //System.out.println("wawawawa");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
