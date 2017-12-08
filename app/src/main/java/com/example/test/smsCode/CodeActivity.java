package com.example.test.smsCode;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.baselibrary.BaseActivity;
import com.example.test.R;
import butterknife.BindView;
import butterknife.ButterKnife;


public class CodeActivity extends BaseActivity{

    private static final String TAG = "CodeActivity";

    @BindView(R.id.et_text)
    EditText editText;

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        ButterKnife.bind(this);

    }


    public void onClick(View view){
        btn = (Button) view;
        requestPermission(this);
    }


    private static final  int READ_SMS = 1001;
    public void requestPermission(final Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            //未授权，提起权限申请
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.CAMERA)) {
                //用于开发者提示用户权限的用途
            } else {
                //申请权限
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_SMS},
                        READ_SMS);
            }
        } else {
            //权限已授权，功能操作
            SmsContentUtil smsObserver = new SmsContentUtil(activity, new Handler(),editText);
            activity.getContentResolver().registerContentObserver(
                    Uri.parse("content://sms/"), true, smsObserver);
            btn.setText("注册完成");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //判断请求码，确定当前申请的权限
        if (requestCode == READ_SMS) {
            //判断权限是否申请通过
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //授权成功
                SmsContentUtil smsObserver = new SmsContentUtil(this, new Handler(),editText);
                this.getContentResolver().registerContentObserver(
                        Uri.parse("content://sms/"), true, smsObserver);
            } else {
                //授权失败
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


}
