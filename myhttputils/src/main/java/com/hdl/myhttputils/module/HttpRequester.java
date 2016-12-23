package com.hdl.myhttputils.module;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.hdl.myhttputils.base.GlobalFied;
import com.hdl.myhttputils.bean.HttpBody;
import com.hdl.myhttputils.bean.ICommCallback;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 网络请求器
 * Created by HDL on 2016/12/21.
 */

public abstract class HttpRequester {
    private static final String TAG = "HttpRequester";
    HttpBody mHttpBody;
    ICommCallback callback;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GlobalFied.WHAT_REQ_SUCCESS://请求成功
                    String json = (String) msg.obj;
                    printJson(json);//print json string
                    if (mHttpBody.getJavaBean() == String.class) {//check the javabean is String
                        callback.onSucceed(json);
                    } else {
                        callback.onSucceed(new Gson().fromJson(json, mHttpBody.getJavaBean()));
                    }
                    callback.onComplete();
                    break;
                case GlobalFied.WHAT_IO_EXCEPTION://网络异常、超时
                    printErrorMsg("IOException");
                    callback.onFailed(new IOException("IOException"));
                    callback.onComplete();
                    break;
                case GlobalFied.WHAT_REQ_FAILED://505等服务器错误
                    printErrorMsg("ServerException");
                    callback.onFailed(new Exception("ServerException"));
                    callback.onComplete();
                    break;
                case GlobalFied.WHAT_MALFORMED_URL_EXCEPTION://url错误异常
                    printErrorMsg("MalformedURLException");
                    callback.onFailed(new MalformedURLException("MalformedURLException"));
                    callback.onComplete();
                    break;
                case GlobalFied.WHAT_DOWNLOAD_PROGRESS://下载进度
                    Bundle bundle = (Bundle) msg.obj;
                    long total = bundle.getLong("contentLength");
                    long cur = bundle.getLong("curProgress");
                    callback.onDownloading(total, cur);
                    System.out.println(total+"----"+cur);
                    break;
                case GlobalFied.WHAT_DOWNLOAD_FINISHED:
                    callback.onSucceed("succeed");
                    callback.onComplete();
                    break;
                case GlobalFied.WHAT_UPLOAD_FINISHED://下载完成
                    callback.onComplete();
                    break;
                case GlobalFied.WHAT_UPLOAD_NOT_FILE://没有文件
                    printErrorMsg("NOFile");
                    callback.onFailed(new Exception("NOFile"));
                    callback.onComplete();
                    break;
            }
        }

    };

    /**
     * 获取请求的参数
     *
     * @return string
     */
    public String getParams() {
        if (mHttpBody.getParams() == null || mHttpBody.getParams().size() == 0) {//判断是否有参数
            return null;
        }
        Map<String, Object> params = mHttpBody.getParams();
        String data = "";
        Set<Map.Entry<String, Object>> entrySet = params.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> me = iterator.next();
            String value = "" + me.getValue();
            try {
                value = URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            data += me.getKey() + "=" + value + "&";
        }
        data = data.substring(0, data.lastIndexOf("&"));//去掉最后一个&
        return data;
    }

    private void printJson(String json) {
        Log.i(TAG, "|-------  The Json start  -------|\n\n\t" + json.toString() + "\n\n|-------  The Json end  -------|");
    }
    public void printErrorMsg(String json) {
        Log.e(TAG, "|————————————  The error msg  ————————————|\n\n\t" + json.toString() + "\n\n|————————————  The error msg  ————————————|");
    }

    /**
     * 开始请求了
     */
    public abstract void request();

}
