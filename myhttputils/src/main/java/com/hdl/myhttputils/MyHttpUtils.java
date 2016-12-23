package com.hdl.myhttputils;

import android.util.Log;

import com.hdl.myhttputils.bean.CommCallback;
import com.hdl.myhttputils.bean.HttpBody;
import com.hdl.myhttputils.bean.ICommCallback;
import com.hdl.myhttputils.module.DownLoadHttpRequester;
import com.hdl.myhttputils.module.GetHttpRequester;
import com.hdl.myhttputils.module.PostHttpRequester;
import com.hdl.myhttputils.module.ProvideHttpRequester;
import com.hdl.myhttputils.module.UpLoadHttpRequester;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * 轻量级网络请求框架MyHttptUtils
 * Created by HDL on 2016/12/21.
 */

public class MyHttpUtils {
    private static final String TAG = "MyHttpUtils";
    private HttpBody mHttpBody = new HttpBody();//请求体对象
    private ICommCallback callback;

    public static MyHttpUtils build() {
        return new MyHttpUtils();
    }

    /**
     * 构造url
     *
     * @param url desc
     * @return this
     */
    public MyHttpUtils url(String url) {
        mHttpBody.setUrl(url);
        return this;
    }

    /**
     * 构造文件上传的url
     *
     * @param uploadUrl desc
     * @return this
     */
    public MyHttpUtils uploadUrl(String uploadUrl) {
        mHttpBody.setUploadUrl(uploadUrl);
        return this;
    }

    /**
     * 构造javabean对象
     *
     * @param javaBean desc
     * @return this
     */
    public MyHttpUtils setJavaBean(Class javaBean) {
        mHttpBody.setJavaBean(javaBean);
        return this;
    }

    /**
     * 设置读取时间超时时间，模式30s
     *
     * @param readTimeOut desc
     * @return this
     */
    public MyHttpUtils setReadTimeOut(int readTimeOut) {
        mHttpBody.setReadTimeOut(readTimeOut);
        return this;
    }

    /**
     * 设置链接时间超时时间，模式5s
     *
     * @param connTimeOut desc
     * @return this
     */
    public MyHttpUtils setConnTimeOut(int connTimeOut) {
        mHttpBody.setConnTimeOut(connTimeOut);
        return this;
    }

    /**
     * 设置请求体
     *
     * @param mHttpBody desc
     * @return this
     */
    public MyHttpUtils setHttpBody(HttpBody mHttpBody) {
        this.mHttpBody = mHttpBody;
        return this;
    }

    /**
     * 获取httpbody
     *
     * @return this
     */
    public HttpBody getHttpBody() {
        return mHttpBody;
    }

    /**
     * 添加参数----键值对
     *
     * @param key   desc
     * @param value desc
     * @return this
     */
    public MyHttpUtils addParam(String key, Object value) {
        mHttpBody.addParam(key, value);
        return this;
    }

    /**
     * 设置请求参数-----按集合
     *
     * @param params desc
     * @return this
     */
    public MyHttpUtils addParams(Map<String, Object> params) {
        mHttpBody.setParams(params);
        return this;
    }

    /**
     * 设置文件保存的目录
     *
     * @param dir desc
     * @return this
     */
    public MyHttpUtils setFileSaveDir(String dir) {
        mHttpBody.setFileSaveDir(dir);
        return this;
    }

    /**
     * 添加文件---按路径
     *
     * @param filePath desc
     * @return this
     */
    public MyHttpUtils addFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            mHttpBody.addFile(filePath);
        } else {
            callback.onFailed(new FileNotFoundException("NOFile"));
            callback.onComplete();
        }
        return this;
    }

    /**
     * 添加文件---按文件
     *
     * @param file desc
     * @return this
     */
    public MyHttpUtils addFile(File file) {
        if (file.exists()) {
            mHttpBody.addFile(file);
        } else {
            callback.onFailed(new FileNotFoundException("NOFile"));
            callback.onComplete();
        }
        return this;
    }

    /**
     * 添加文件---按文件列表
     *
     * @param fileList desc
     * @return this
     */
    public MyHttpUtils addFiles(List<File> fileList) {
        for (File file : fileList) {
            if (file.exists()) {
                mHttpBody.addFile(file);
            } else {
                callback.onFailed(new FileNotFoundException("NOFile"));
                callback.onComplete();
            }
        }
        return this;
    }

    /**
     * 添加文件---按文件路径列表
     *
     * @param filePaths desc
     * @return this
     */
    public MyHttpUtils addFilesByPath(List<String> filePaths) {
        for (String filePath : filePaths) {
            if (new File(filePath).exists()) {
                mHttpBody.addFile(filePath);
            } else {
                callback.onFailed(new FileNotFoundException("NOFile"));
                callback.onComplete();
            }
        }
        return this;
    }

    /**
     * 执行Get请求
     *
     * @param callback desc
     * @return this
     */
    public MyHttpUtils onExecute(CommCallback callback) {
        this.callback = callback;
        ProvideHttpRequester requester = new ProvideHttpRequester(new GetHttpRequester(mHttpBody, callback));
        requester.startRequest();//开始请求
        return this;
    }

    /**
     * 执行Post请求
     *
     * @param callback desc
     * @return this
     */
    public MyHttpUtils onExecuteByPost(CommCallback callback) {
        this.callback = callback;
        ProvideHttpRequester requester = new ProvideHttpRequester(new PostHttpRequester(mHttpBody, callback));
        requester.startRequest();//开始请求
        return this;
    }

    /**
     * 下载文件
     *
     * @param callback desc
     * @return this
     */
    public MyHttpUtils onExecuteDwonload(CommCallback callback) {
        this.callback = callback;
        ProvideHttpRequester requester = new ProvideHttpRequester(new DownLoadHttpRequester(mHttpBody, callback));
        requester.startRequest();//开始请求
        return this;
    }

    /**
     * 上传文件
     *
     * @param callback desc
     * @return this
     */
    public MyHttpUtils onExecuteUpLoad(CommCallback callback) {
        this.callback = callback;
        ProvideHttpRequester requester = new ProvideHttpRequester(new UpLoadHttpRequester(mHttpBody, callback));
        requester.startRequest();//开始请求
        return this;
    }

}
