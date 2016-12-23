package com.hdl.myhttputils.bean;

/**
 * 回调的抽象类
 * Created by HDL on 2016/12/21.
 */

public abstract class ICommCallback<T> {
    public void onComplete() {

    }

    public void onDownloading(long total, long current) {

    }

    public abstract void onSucceed(T t);

    public abstract void onFailed(Throwable throwable);
}
