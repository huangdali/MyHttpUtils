package com.hdl.myhttputilssimple.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.hdl.myhttputils.MyHttpUtils;
import com.hdl.myhttputils.bean.CommCallback;
import com.hdl.myhttputils.bean.StringCallBack;
import com.hdl.myhttputils.utils.FailedMsgUtils;
import com.hdl.myhttputilssimple.R;
import com.hdl.myhttputilssimple.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UploadActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("上传中，请稍后");
    }

    /**
     * 单文件上传
     *
     * @param view
     */
    public void onUpload(View view) {
        mProgressDialog.show();
        MyHttpUtils.build()
                .uploadUrl("http://192.168.2.153:8080/MyHttpUtilsServer/upload")
                .addFile("sdcard/download/wifi.exe")
                .onExecuteUpLoad(new CommCallback() {
                    @Override
                    public void onComplete() {
                        mProgressDialog.dismiss();
                        ToastUtils.showToast(UploadActivity.this, "上传完成");
                    }

                    @Override
                    public void onSucceed(Object o) {

                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        ToastUtils.showToast(UploadActivity.this, FailedMsgUtils.getErrMsgStr(throwable));
                    }
                });
    }

    /**
     * 多文件上传
     *
     * @param view
     */
    public void onUploadMult(View view) {

        mProgressDialog.show();
        MyHttpUtils.build()
                .uploadUrl("http://192.168.2.153:8080/MyHttpUtilsServer/upload")
                .addFile("sdcard/download/wifi.exe")
                .addFile("sdcard/download/g3box_uesr_2.3.1.apk")
                .onExecuteUpLoad(new CommCallback() {
                    @Override
                    public void onComplete() {
                        mProgressDialog.dismiss();
                        ToastUtils.showToast(UploadActivity.this, "上传完成");
                    }

                    @Override
                    public void onSucceed(Object o) {

                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        ToastUtils.showToast(UploadActivity.this, FailedMsgUtils.getErrMsgStr(throwable));
                    }
                });
    }

    /**
                     * 参数与文件同时上传
                     *
                     * @param view
                     */
                    public void onUploadParamFile(View view) {
                        MyHttpUtils.build()//构建myhttputils
                                .url("http://192.168.2.153:8080/MyHttpUtilsServer/string.action")//请求的url
                                .uploadUrl("http://192.168.2.153:8080/MyHttpUtilsServer/upload")
                                .addParam("content", "abc")
                                .addFile("sdcard/download/wifi.exe")
                                .onExecute(new StringCallBack() {//开始执行，并有一个回调（异步的哦---->直接可以更新ui）
                                    @Override
                                    public void onSucceed(String result) {//请求成功之后会调用这个方法
                                        ToastUtils.showToast(UploadActivity.this, "转换成功-------->" + result);
                                    }

                                    @Override
                                    public void onFailed(Throwable throwable) {//请求失败的时候会调用这个方法
                                        ToastUtils.showToast(UploadActivity.this, FailedMsgUtils.getErrMsgStr(throwable));
                                    }
                                })
                                .onExecuteUpLoad(new CommCallback() {
                                    @Override
                                    public void onComplete() {
                                        mProgressDialog.dismiss();
                                        ToastUtils.showToast(UploadActivity.this, "上传完成");
                                    }

                                    @Override
                                    public void onSucceed(Object o) {

                                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        ToastUtils.showToast(UploadActivity.this, FailedMsgUtils.getErrMsgStr(throwable));
                    }
                });
    }
}
