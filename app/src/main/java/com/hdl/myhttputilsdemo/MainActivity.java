package com.hdl.myhttputilsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hdl.myhttputils.CommCallback;
import com.hdl.myhttputils.MyHttpUtils;
import com.hdl.myhttputilsdemo.bean.IPBean;
import com.hdl.myhttputilsdemo.bean.RemarkBean;
import com.hdl.myhttputilsdemo.bean.UploadResultBean;
import com.hdl.myhttputilsdemo.utils.ToastUtils;

import java.io.File;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvProgress = (TextView) findViewById(R.id.tv_progress);
    }

    /**
     * 获取IP地址的监听事件
     *
     * @param view
     */
    public void onGetIP(View view) {
        String url = "http://ip.taobao.com/service/getIpInfo.php?ip=182.254.34.74";//参数直接拼接在这里到后面就可以了
        new MyHttpUtils()
                .url(url)//请求的url
                .setJavaBean(IPBean.class)//设置需要解析成的javabean对象
                .setReadTimeout(60000)//设置读取超时时间,不设置的话默认为30s(30000)
                .setConnTimeout(6000)//设置连接超时时间,不设置的话默认5s(5000)
                .onExecute(new CommCallback<IPBean>() {//开始执行异步请求,传入一个通用回调对象,泛型为返回的javabean对象

                    @Override
                    public void onSucess(IPBean ipBean) {//成功之后回调
                        ToastUtils.showMsg(MainActivity.this, ipBean.toString());
                    }

                    @Override
                    public void onFailed(String msg) {//失败时候回调
                        ToastUtils.showMsg(MainActivity.this, msg);
                    }
                });
    }

    /**
     * 获取备注信息
     *
     * @param view
     */
    public void onGetRemark(View view) {
        final String remarkUrl = "http://admin.xnshuo.com:8090/G3/userInfoController/updateUser.action";
        HashMap<String, String> param = new HashMap<>();
        param.put("userid", "7cf8cb8edbb6871beeed856df47eb189");
        param.put("uid", "8011bd25406db58588ab54");
        new MyHttpUtils()
                .url(remarkUrl)
                .addParam(param)
                .setJavaBean(RemarkBean.class)
                .onExecuteByPost(new CommCallback<RemarkBean>() {
                    @Override
                    public void onSucess(RemarkBean remarkBean) {
                        ToastUtils.showMsg(MainActivity.this, remarkBean.toString());
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtils.showMsg(MainActivity.this, msg);
                    }
                });
    }

    /**
     * 下载文件
     *
     * @param view
     */
    public void onDownloadFile(View view) {
        String url = "http://192.168.0.107:8080/UpLoadDemo/fg.exe";
        new MyHttpUtils()
                .url(url)
                .setFileSavePath("/sdcard/downloadtest")//不要这里只是填写文件保存的路径，不包括文件名哦
                .setReadTimeout(5 * 60 * 1000)//由于下载文件耗时比较大，所以设置读取时间为5分钟
                .downloadFile(new CommCallback<String>() {
                    @Override
                    public void onSucess(String msg) {
                        ToastUtils.showMsg(MainActivity.this, msg);
                    }

                    @Override
                    public void onFailed(String s) {

                    }

                    /**
                     * 可以重写进度回调方法
                     * @param total
                     * @param current
                     */
                    @Override
                    public void onDownloading(long total, long current) {
                        tvProgress.setText("当前进度：" + new DecimalFormat("######0.00").format(((double) current / total) * 100) + "%");
                    }
                });
    }

    public void onUploadFile(View view) {
        new MyHttpUtils()
                .url("http://192.168.0.107:8080/UpLoadDemo/upload")
                .setJavaBean(UploadResultBean.class)
                .addUploadFile(new File("/sdcard/index.png"))//设置需上传文件
                .uploadFile(new CommCallback<UploadResultBean>() {
                    @Override
                    public void onSucess(UploadResultBean uploadResultBean) {
                        ToastUtils.showMsg(MainActivity.this, uploadResultBean.getMessage());
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtils.showMsg(MainActivity.this, msg);
                    }
                });
    }

    public void onUploadFileMult(View view) {
        ArrayList<File>fileList=new ArrayList<>();//文件列表
        fileList.add(new File("/sdcard/demo.exe"));
        fileList.add(new File("/sdcard/mylog.png"));
        new MyHttpUtils()
                .url("http://192.168.0.107:8080/UpLoadDemo/upload")
                .setJavaBean(UploadResultBean.class)
                .addUploadFiles(fileList)//设置需上传的多个文件
                .uploadFileMult(new CommCallback<UploadResultBean>() {
                    @Override
                    public void onSucess(UploadResultBean uploadResultBean) {
                        ToastUtils.showMsg(MainActivity.this, uploadResultBean.getMessage());
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtils.showMsg(MainActivity.this, msg);
                    }
                });
    }
}
