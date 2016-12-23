package com.hdl.myhttputilssimple.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hdl.myhttputils.MyHttpUtils;
import com.hdl.myhttputils.bean.CommCallback;
import com.hdl.myhttputils.bean.StringCallBack;
import com.hdl.myhttputils.utils.FailedMsgUtils;
import com.hdl.myhttputilssimple.R;
import com.hdl.myhttputilssimple.bean.LoginBean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("加载中");
    }

    public void onGet(View view) {
        startActivity(new Intent(this,IPQueryActivity.class));
    }

    public void onPost(View view) {
        startActivity(new Intent(this,LoginActivity.class));
    }

    public void onDownLoad(View view) {
       startActivity(new Intent(this,DownloadActivity.class));
    }

    public void onUpLoad(View view) {
        startActivity(new Intent(this,UploadActivity.class));
    }

    public void onString(View view) {
            startActivity(new Intent(this,UpperCaseActivity.class));
    }
}
