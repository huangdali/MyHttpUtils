package hdl.com.myhttputils;


/**
 * Created by HDL on 2016/8/31.
 */
public interface CommCallback<T> {
    void onSucess(T t);

    void onFailed(String msg);
}
