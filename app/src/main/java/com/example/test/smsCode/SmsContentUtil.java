package com.example.test.smsCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by liubin on 2017/7/13.
 */

public class SmsContentUtil extends ContentObserver {

    public static final String SMS_URI_INBOX = "content://sms/inbox";

    private Activity activity = null;

    private String smsContent = "";

    private EditText verifyText = null;

    public SmsContentUtil(Handler handler) {
        super(handler);
    }

    public SmsContentUtil(Activity activity, Handler handler, EditText verifyText) {
        super(handler);
        this.activity = activity;
        this.verifyText = verifyText;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        if (uri.toString().equals("content://sms/raw")) {        // 第一次回调
            return;
        }

        Uri inboxUri = Uri.parse("content://sms/inbox");        // 第二次回调 查询收件箱里的内容
        Cursor c = activity.getContentResolver().query(inboxUri, null, null, null, "date desc");  // 按时间顺序排序短信数据库
        if (c != null) {
            if (c.moveToFirst()) {
                String address = c.getString(c.getColumnIndex("address"));//发送方号码
                String body = c.getString(c.getColumnIndex("body")); // 短信内容
                if (TextUtils.isEmpty(body) || !body.startsWith("【陕西食药监管】")) {
                    return;
                }
                Pattern pattern = Pattern.compile("(\\d{6})");//正则表达式匹配验证码
                Matcher matcher = pattern.matcher(body);
                if (matcher.find()) {
                    String code = matcher.group(0);
                    verifyText.setText(code);
                }
            }
            c.close();
        }


//        Cursor cursor = null;// 光标
//        // 读取收件箱中指定号码的短信 //15596895812
//        cursor = activity.getContentResolver().query(Uri.parse(SMS_URI_INBOX), new String[] {
//                        "_id", "address", "body", "read", "date" }, "address=? and read=?",
//                new String[] { "18629665928", "0" }, "date desc");
//
//        if (cursor != null) {// 如果短信为未读模式
//            cursor.moveToFirst();
//            if (cursor.moveToFirst()) {
//
//                String smsbody = cursor
//                        .getString(cursor.getColumnIndex("body"));
//                Log.e("SmsContentUtil","smsbody:" + smsbody);
//                String regEx = "(?<![0-9])([0-9]{6})(?![0-9])";
//                Pattern p = Pattern.compile(regEx);
//                Matcher m = p.matcher(smsbody.toString());
//                Log.d("SmsContentUtil","m = "+ m);
//                Log.d("SmsContentUtil","smsContent = "+ smsContent);
//                if (m.find()) {
//                    System.out.println(m.group());
//                    if (!TextUtils.isEmpty(m.group())) {
//                        verifyText.setText(m.group());
//                    }
//                }
//            }
//            cursor.close();
//        }
    }


}