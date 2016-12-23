package com.hdl.myhttputilssimple.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hdl.myhttputils.MyHttpUtils;
import com.hdl.myhttputils.bean.CommCallback;
import com.hdl.myhttputils.bean.HttpBody;
import com.hdl.myhttputils.utils.FailedMsgUtils;
import com.hdl.myhttputilssimple.R;
import com.hdl.myhttputilssimple.utils.ToastUtils;

import java.text.DecimalFormat;

public class DownloadActivity extends AppCompatActivity {

    private TextView tvProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        tvProgress = (TextView) findViewById(R.id.tv_download_progress);
    }

    /**
     * 开始下载按钮单击事件
     *
     * @param view
     */
    public void onDownload(View view) {

        HttpBody body = new HttpBody();
        body.setUrl("http://192.168.2.153:8080/MyHttpUtilsServer/wifi.exe")
                .setConnTimeOut(6000)
                .setFileSaveDir("/sdcard/myapp")
                .setReadTimeOut(5 * 60 * 1000);

        MyHttpUtils.build()
                .setHttpBody(body)
                .onExecuteDwonload(new CommCallback() {

                    @Override
                    public void onSucceed(Object o) {
                        ToastUtils.showToast(DownloadActivity.this, "下载完成");
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        ToastUtils.showToast(DownloadActivity.this, FailedMsgUtils.getErrMsgStr(throwable));
                    }

                    @Override
                    public void onDownloading(long total, long current) {
                        System.out.println(total + "-------" + current);
                        tvProgress.setText(new DecimalFormat("######0.00").format(((double) current / total) * 100) + "%");//保留两位小数
                    }
                });
    }
}
