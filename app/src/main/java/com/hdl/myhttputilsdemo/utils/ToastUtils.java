package com.hdl.myhttputilsdemo.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by HDL on 2016/10/18.
 */

public class ToastUtils {
    public static void showMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
