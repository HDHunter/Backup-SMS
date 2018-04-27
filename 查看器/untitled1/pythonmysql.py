#!/usr/bin/python3
import pymysql
class sqll:
    def sqlse(self):
        # 打开数据库连接

        db = pymysql.connect(host='xxx.xxx.xxx.xxx',
                                     port=3306,
                                     user='xxxxx',
                                     password='xxxxxxxxx',
                                     db='phone',
                                     charset='utf8',
                                     cursorclass=pymysql.cursors.DictCursor)
        # 使用 cursor() 方法创建一个游标对象 cursor
        cursor = db.cursor()
        #sql="SELECT contacts.phonename,sms.phonenum, sms.sms, sms.smsdate, sms.sms_id, sms.sms_huihua, sms.smsid FROM sms, contacts WHERE contacts.phonenum =sms.phonenum"
        sql="SELECT sms.phonenum, sms.sms, sms.smsdate, sms.sms_id, sms.sms_huihua, sms.smsid FROM sms"
        print(sql)
        try:
           # 执行sql语句
           cursor.execute(sql)
           results = cursor.fetchall()
        except:
           # 发生错误时回滚
           db.rollback()
           print("发生错误")
        # 关闭数据库连接
        db.close()
        print(results)
        return results

