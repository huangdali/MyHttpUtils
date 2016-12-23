package com.hdl.myhttputils.bean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Http的基本属性类
 * Created by HDL on 2016/12/21.
 */

public class HttpBody {
    private String url;//请求的url
    private String uploadUrl;//文件上传的url
    private Class javaBean = String.class;//javabean对象
    private int readTimeOut = 30 * 1000;//默认30s
    private int connTimeOut = 5 * 1000;//默认5s
    private Map<String, Object> params = new HashMap<>();//请求的参数
    private String fileSaveDir = "/sdcard/download";//保存文件的目录
    private List<File> files = new ArrayList<>();//需要上传的文件

    public String getUploadUrl() {
        return uploadUrl;
    }

    public HttpBody setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
        return this;
    }

    public HttpBody addFile(String filePath) {
        files.add(new File(filePath));
        return this;
    }

    public HttpBody addFile(File file) {
        files.add(file);
        return this;
    }

    public HttpBody addFiles(List<File> fileList) {
        files.addAll(fileList);
        return this;
    }

    public HttpBody addParam(String key, Object value) {
        params.put(key, value);
        return this;
    }

    public List<File> getFiles() {
        return files;
    }

    public HttpBody setFiles(List<File> files) {
        this.files = files;
        return this;
    }

    public String getFileSaveDir() {
        return fileSaveDir;
    }

    public HttpBody setFileSaveDir(String fileSaveDir) {
        this.fileSaveDir = fileSaveDir;
        return this;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public HttpBody setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
        return this;
    }

    public int getConnTimeOut() {
        return connTimeOut;
    }

    public HttpBody setConnTimeOut(int connTimeOut) {
        this.connTimeOut = connTimeOut;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public HttpBody setUrl(String url) {
        this.url = url;
        return this;
    }

    public Class getJavaBean() {
        return javaBean;
    }

    public HttpBody setJavaBean(Class javaBean) {
        this.javaBean = javaBean;
        return this;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public HttpBody setParams(Map<String, Object> params) {
        this.params = params;
        return this;
    }

}
