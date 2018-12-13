package com.linewell.archive.net.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linewell.archive.net.base.BaseActivity;
import com.linewell.archive.net.util.SelectorFactory;

import butterknife.Bind;
import butterknife.OnClick;

import com.linewell.archive.R;
import static android.graphics.Color.GRAY;

/**
 * 欢迎页
 *
 * @author zhousf
 */
public class WelcomeActivity extends BaseActivity {

    @Bind(R.id.httpProxyBtn)
    Button httpProxyBtn;
    @Bind(R.id.httpBtn)
    Button httpBtn;
    @Bind(R.id.uploadImgBtn)
    Button uploadImgBtn;
    @Bind(R.id.uploadFileBtn)
    Button uploadFileBtn;
    @Bind(R.id.downloadBtn)
    Button downloadBtn;
    @Bind(R.id.downloadPointBtn)
    Button downloadPointBtn;

    @Override
    protected int initLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置按钮圆角样式
        SelectorFactory.newShapeSelector()
                .setStrokeWidth(2)
                .setCornerRadius(15)
                .setDefaultStrokeColor(GRAY)
                .setDefaultBgColor(getResources().getColor(R.color.wihte))
                .setPressedBgColor(getResources().getColor(R.color.light_blue))
                .bind(httpProxyBtn)
                .bind(httpBtn)
                .bind(uploadImgBtn)
                .bind(uploadFileBtn)
                .bind(downloadBtn)
                .bind(downloadPointBtn);
    }

    @OnClick({R.id.httpProxyBtn, R.id.httpBtn, R.id.uploadImgBtn, R.id.uploadFileBtn, R.id.downloadBtn, R.id.downloadPointBtn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.httpProxyBtn:
                startActivity(new Intent(WelcomeActivity.this, HttpProxyActivity.class));
                break;
            case R.id.uploadImgBtn:
                startActivity(new Intent(WelcomeActivity.this, UploadImageActivity.class));
                break;
            case R.id.uploadFileBtn:
                startActivity(new Intent(WelcomeActivity.this, UploadFileActivity.class));
                break;
            case R.id.downloadBtn:
                startActivity(new Intent(WelcomeActivity.this, DownloadActivity.class));
                break;
            case R.id.downloadPointBtn:
                startActivity(new Intent(WelcomeActivity.this, DownloadBreakpointsActivity.class));
                break;
        }
    }
}
