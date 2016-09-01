package hdl.com.myhttputils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.google.gson.Gson;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 封装的HttpURLConnection网络请求工具类
 *
 * @function 只需要传入url, javabean, 回调类即可得到你想要的结果(如果是post请求,传入map类型的请求参数就可以了), 次类请求网络采用异步方法, 所以不用再子线程中执行
 * Created by HDL on 2016/9/1.
 */
public class MyHttpUtils {
    private String urlPath;//请求路径
    private Class clazz;//需要解析成的javabean对象
    private CommCallback callback;//成功与失败的回调对象
    private HashMap<String, String> map;//post请求的参数
    private static final int WHAT_URLFAILED = 1001;//url地址错误
    private static final int WHAT_IOFAILED = 1002;//io异常
    private static final int WHAT_REQFAILED = 1003;//请求失败
    private static final int WHAT_REQSUCCESS = 1004;//请求成功
    private static final String TAG = "MyHttpUtils";//请求成功
    private int readTimeout = 30000;//读取超时默认30s
    private int connectTimeout = 5000;//连接超时默认5s
    private Handler mHanler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_URLFAILED:
                    callback.onFailed("服务器出问题,请重试");
                    break;
                case WHAT_IOFAILED:
                    callback.onFailed("网络错误,请检查网络连接!");
                    break;
                case WHAT_REQFAILED:
                    callback.onFailed("请求失败!");
                    break;
                case WHAT_REQSUCCESS:
                    String json = (String) msg.obj;
                    Log.i(TAG, json);
                    callback.onSucess(new Gson().fromJson(json, clazz));
                    break;
            }
        }
    };

    /**
     * 传入url
     *
     * @param urlPath
     */
    public MyHttpUtils url(String urlPath) {
        this.urlPath = urlPath;
        return this;
    }


    /**
     * 传入javaBean对象---要得到的对象
     *
     * @param clazz
     * @return
     */
    public MyHttpUtils setJavaBean(Class clazz) {
        this.clazz = clazz;
        return this;
    }

    /**
     * 传入post的请求参数
     *
     * @param map
     */
    public MyHttpUtils addParam(HashMap<String, String> map) {
        this.map = map;
        return this;
    }

    /**
     * 传入读取超时时间,默认为30秒
     *
     * @param readTimeout
     */
    public MyHttpUtils setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    /**
     * 传入连接超时时间,默认为5秒
     *
     * @param connectTimeout
     */
    public MyHttpUtils setConnTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    /**
     * 执行post请求
     *
     * @param callback
     */
    public void onExecuteByPost(final CommCallback callback) {
        this.callback = callback;
        new Thread() {
            @Override
            public void run() {

                try {
                    URL url = new URL(urlPath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(readTimeout);
                    conn.setConnectTimeout(connectTimeout);
                    //设置输入和输出流
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestMethod("POST");
                    conn.setUseCaches(false);
                    // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，
                    // 要注意的是connection.getOutputStream会隐含的进行connect。
                    conn.connect();
                    //DataOutputStream流
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    //要上传的参数
                    String content = "";
                    if (map.size() > 0) {
                        Set<Map.Entry<String, String>> set = map.entrySet();
                        Iterator<Map.Entry<String, String>> it = set.iterator();
                        while (it.hasNext()) {
                            Map.Entry<String, String> me = it.next();
                            content += me.getKey() + "=" + me.getValue() + "&";

                        }
                        //去掉最后一个&
                        content = content.substring(0, content.length() - 2);
                        Log.i(TAG, content);
                    }
                    //将要上传的内容写入流中
                    out.writeBytes(content);
                    //刷新、关闭
                    out.flush();
                    out.close();
                    if (conn.getResponseCode() == 200) {
                        InputStream is = conn.getInputStream();
                        int len = 0;
                        byte[] buf = new byte[1024 * 1024];
                        StringBuilder json = new StringBuilder();
                        while ((len = is.read(buf)) != -1) {
                            json.append(new String(buf, 0, len));
                        }
                        is.close();
                        Log.i(TAG, "json数据为:" + json);
                        Message msg = mHanler.obtainMessage();
                        msg.what = WHAT_REQSUCCESS;
                        msg.obj = json.toString();
                        mHanler.sendMessage(msg);
                    } else {
                        mHanler.sendEmptyMessage(WHAT_REQFAILED);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    mHanler.sendEmptyMessage(WHAT_URLFAILED);
                } catch (IOException e) {
                    mHanler.sendEmptyMessage(WHAT_IOFAILED);
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 执行get请求
     *
     * @param callback
     */
    public void onExecute(CommCallback callback) {
        this.callback = callback;
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlPath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(readTimeout);
                    conn.setConnectTimeout(connectTimeout);
                    if (conn.getResponseCode() == 200) {
                        InputStream is = conn.getInputStream();
                        int len = 0;
                        byte[] buf = new byte[1024 * 1024];
                        StringBuilder json = new StringBuilder();
                        while ((len = is.read(buf)) != -1) {
                            json.append(new String(buf, 0, len));
                        }
                        is.close();
                        Message msg = mHanler.obtainMessage();
                        msg.what = WHAT_REQSUCCESS;
                        msg.obj = json.toString();
                        mHanler.sendMessage(msg);
                    } else {
                        mHanler.sendEmptyMessage(WHAT_REQFAILED);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    mHanler.sendEmptyMessage(WHAT_URLFAILED);
                } catch (IOException e) {
                    mHanler.sendEmptyMessage(WHAT_IOFAILED);
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
