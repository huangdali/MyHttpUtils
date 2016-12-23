package com.hdl.myhttputils.utils;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 异常信息工具类
 * Created by HDL on 2016/12/22.
 */

public class FailedMsgUtils {
    private static Map<String, Object> errList = new HashMap<>();//错误信息对照表

    static {
        errList.put("IOException", "网络连接异常");
        errList.put("ServerException", "服务器维护中");
        errList.put("MalformedURLException", "找不到服务器");
        errList.put("NOFile", "找不到文件");
    }

    /**
     * 获取错误信息对应的字符串
     *@param errMsg desc
     * @return msgStr
     */
    public static String getErrMsgStr(String errMsg) {
        String msgStr = (String) errList.get(errMsg);
        if (TextUtils.isEmpty(msgStr)) {
            msgStr = "未知错误";
        }
        return msgStr;
    }

    /**
     * 获取错误信息对应的字符串
     *@param throwable desc
     * @return msgStr
     */
    public static String getErrMsgStr(Throwable throwable) {
        return getErrMsgStr(throwable.getMessage());
    }

}
