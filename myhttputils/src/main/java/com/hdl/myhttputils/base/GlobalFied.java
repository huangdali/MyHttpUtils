package com.hdl.myhttputils.base;

/**
 * 全局字段
 * Created by HDL on 2016/12/21.
 */

public class GlobalFied {
    public static final int WHAT_REQ_SUCCESS = 1001;//请求成功
    public static final int WHAT_REQ_FAILED = 1002;//请求失败
    public static final int WHAT_MALFORMED_URL_EXCEPTION = 1003;//url解析异常
    public static final int WHAT_IO_EXCEPTION = 1004;//io异常
    public static final int WHAT_DOWNLOAD_PROGRESS = 1005;//下载回调
    public static final int WHAT_DOWNLOAD_FINISHED = 1006;//下载完成
    public static final int WHAT_UPLOAD_FINISHED = 1007;//上传完成
    public static final int WHAT_UPLOAD_NOT_FILE = 1008;//没有上传的文件
}
