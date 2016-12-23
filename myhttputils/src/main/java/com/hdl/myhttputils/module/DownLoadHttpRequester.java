package com.hdl.myhttputils.module;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.hdl.myhttputils.base.GlobalFied;
import com.hdl.myhttputils.bean.HttpBody;
import com.hdl.myhttputils.bean.ICommCallback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 文件下载的网络请求器
 * Created by HDL on 2016/12/21.
 */

public class DownLoadHttpRequester extends HttpRequester {
    public DownLoadHttpRequester(HttpBody mHttpBody, ICommCallback callback) {
        this.mHttpBody = mHttpBody;
        this.callback = callback;
    }

    @Override
    public void request() {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(mHttpBody.getUrl());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(mHttpBody.getReadTimeOut());
                    conn.setConnectTimeout(mHttpBody.getConnTimeOut());
                    if (conn.getResponseCode() == 200) {
                        InputStream is = conn.getInputStream();
                        int len = 0;
                        byte[] buf = new byte[1024 * 1024];
                        File dir = new File(mHttpBody.getFileSaveDir());
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        long contentLength = conn.getContentLength();
                        FileOutputStream fos = new FileOutputStream(new File(dir, getFileName()));
                        long curProgress = 0;
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                            curProgress += len;
                            Message msg = mHandler.obtainMessage();
                            msg.what = GlobalFied.WHAT_DOWNLOAD_PROGRESS;
                            Bundle bundle = new Bundle();
                            bundle.putLong("contentLength", contentLength);
                            bundle.putLong("curProgress", curProgress);
                            msg.obj = bundle;
                            mHandler.sendMessage(msg);
                        }
                        is.close();
                        fos.close();
                        mHandler.sendEmptyMessage(GlobalFied.WHAT_DOWNLOAD_FINISHED);
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

    private String getFileName() {
        return mHttpBody.getUrl().substring(mHttpBody.getUrl().lastIndexOf("/") + 1);
    }
}
