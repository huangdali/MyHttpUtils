package com.hdl.myhttputils.module;

import android.os.Message;

import com.hdl.myhttputils.base.GlobalFied;
import com.hdl.myhttputils.bean.HttpBody;
import com.hdl.myhttputils.bean.ICommCallback;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;


/**
 * 单文件上传
 * Created by HDL on 2016/12/21.
 */

public class UpLoadHttpRequester extends HttpRequester {
    public UpLoadHttpRequester(HttpBody mHttpBody, ICommCallback callback) {
        this.mHttpBody = mHttpBody;
        this.callback = callback;
    }

    @Override
    public void request() {
        new Thread() {
            @Override
            public void run() {
                String BOUNDARY = UUID.randomUUID().toString();
                String PREFIX = "--", LINE_END = "\r\n";
                String CONTENT_TYPE = "multipart/form-data";
                try {
                    URL url = new URL(mHttpBody.getUploadUrl());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(mHttpBody.getReadTimeOut());
                    conn.setConnectTimeout(mHttpBody.getConnTimeOut());
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Charset", "utf-8");
                    // 设置编码
                    conn.setRequestProperty("connection", "keep-alive");
                    conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                            + BOUNDARY);

                    OutputStream outputSteam = conn.getOutputStream();
                    DataOutputStream dos = new DataOutputStream(outputSteam);
                    if (mHttpBody.getFiles() != null && mHttpBody.getFiles().size() > 0) {//有文件才能上传
                        for (File file : mHttpBody.getFiles()) {
                            StringBuffer sb = new StringBuffer();
                            sb.append(PREFIX);
                            sb.append(BOUNDARY);
                            sb.append(LINE_END);
                            sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
                                    + file.getName() + "\"" + LINE_END);
                            sb.append("Content-Type: application/octet-stream; charset=utf-8"
                                    + LINE_END);
                            sb.append(LINE_END);
                            dos.write(sb.toString().getBytes());
                            FileInputStream fis = new FileInputStream(file);
                            byte[] bytes = new byte[1024 * 1024];
                            int len = 0;
                            while ((len = fis.read(bytes)) != -1) {
                                dos.write(bytes, 0, len);
                            }
                            fis.close();
                            dos.write(LINE_END.getBytes());
                        }
                        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
                                .getBytes();
                        dos.write(end_data);
                        dos.flush();
                        dos.close();
                        StringBuilder json = new StringBuilder();
                        if (conn.getResponseCode() == 200) {
                            int length = 0;
                            byte buf[] = new byte[1024 * 1024];
                            InputStream cis = conn.getInputStream();
                            while ((length = cis.read(buf)) != -1) {
                                json.append(new String(buf, 0, length));
                            }
                            cis.close();
                            Message msg = mHandler.obtainMessage();
                            msg.what = GlobalFied.WHAT_UPLOAD_FINISHED;
                            msg.obj = json.toString();
                            mHandler.sendMessage(msg);
                        } else {
                            mHandler.sendEmptyMessage(GlobalFied.WHAT_REQ_FAILED);
                        }

                    }else{
                        mHandler.sendEmptyMessage(GlobalFied.WHAT_UPLOAD_NOT_FILE);
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
