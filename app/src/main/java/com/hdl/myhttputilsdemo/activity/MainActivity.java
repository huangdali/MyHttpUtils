package com.hdl.myhttputilsdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.hdl.myhttputilsdemo.R;
import com.hdl.myhttputilsdemo.bean.IPBean;
import com.hdl.myhttputilsdemo.bean.MoviesBean;
import com.hdl.myhttputilsdemo.bean.RemarkBean;
import com.socks.library.KLog;

import java.util.HashMap;

import hdl.com.myhttputils.CommCallback;
import hdl.com.myhttputils.MyHttpUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 获取IP地址的监听事件
     *
     * @param view
     */
    public void onGetIP(View view) {
        String url = "http://ip.taobao.com/service/getIpInfo.php?ip=182.254.34.74";
        new MyHttpUtils()
                .url(url)//请求的url
                .setJavaBean(IPBean.class)//设置需要解析成的javabean对象
                .setReadTimeout(60000)//设置读取超时时间,不设置的话默认为30s(30000)
                .setConnTimeout(6000)//设置连接超时时间,不设置的话默认5s(5000)
                .onExecute(new CommCallback<IPBean>() {//开始执行异步请求,传入一个通用回调对象,泛型为返回的javabean对象

                    @Override
                    public void onSucess(IPBean ipBean) {//成功之后回调
                        KLog.e("测试:" + ipBean.toString());
                        Toast.makeText(MainActivity.this, ipBean.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed(String msg) {//失败时候回调
                        KLog.e(msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onGetMovie(View view) {
        String urlpath = "https://api.douban.com/v2/movie/top250?start=1&count=4";
        new MyHttpUtils()
                .url(urlpath)
                .setJavaBean(MoviesBean.class)
                .onExecute(new CommCallback<MoviesBean>() {

                    @Override
                    public void onSucess(MoviesBean movies) {
                        Toast.makeText(MainActivity.this, movies.toString(), Toast.LENGTH_SHORT).show();
                        KLog.e("测试:" + movies.toString());
                    }

                    @Override
                    public void onFailed(String msg) {
                        KLog.e(msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void onGetRemark(View view) {
        String remarkUrl = "http://admin.xnshuo.com:8090/G3/userInfoController/updateUser.action";
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
                        Toast.makeText(MainActivity.this, remarkBean.toString(), Toast.LENGTH_SHORT).show();
                        KLog.e("解析出来的对象为:" + remarkBean.toString());
                    }

                    @Override
                    public void onFailed(String msg) {
                        KLog.e("错误消息:" + msg);
                    }
                });
    }
}
