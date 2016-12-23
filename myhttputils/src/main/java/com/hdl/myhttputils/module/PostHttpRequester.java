package com.hdl.myhttputils.module;

import android.os.Message;
import android.util.Log;

import com.hdl.myhttputils.base.GlobalFied;
import com.hdl.myhttputils.bean.HttpBody;
import com.hdl.myhttputils.bean.ICommCallback;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * post请求器
 * Created by HDL on 2016/12/21.
 */

public class PostHttpRequester extends HttpRequester {
    private static final String TAG = "PostHttpRequester";

    public PostHttpRequester(HttpBody mHttpBody, ICommCallback callback) {
        this.mHttpBody = mHttpBody;
        this.callback = callback;
    }

    @Override
    public void request() {
        new Thread() {
            @Override
            public void run() {
                String urlPath = mHttpBody.getUrl();
                try {
                    URL url = new URL(urlPath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(mHttpBody.getReadTimeOut());
                    conn.setConnectTimeout(mHttpBody.getConnTimeOut());
                    conn.setDoInput(true);
                    conn.setUseCaches(true);
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(getParams());
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
                        Message msg = mHandler.obtainMessage();
                        msg.what = GlobalFied.WHAT_REQ_SUCCESS;
                        msg.obj = json.toString();
                        mHandler.sendMessage(msg);
                    } else {
                        mHandler.sendEmptyMessage(GlobalFied.WHAT_REQ_FAILED);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(GlobalFied.WHAT_MALFORMED_URL_EXCEPTION);
                } catch (IOException e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(GlobalFied.WHAT_IO_EXCEPTION);
                }
            }
        }.start();
    }
}
