package com.yuxie.singnature.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.baselib.base.BaseActivity;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.jaeger.library.StatusBarUtil;
import com.yuxie.singnature.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingnatureDetailsActivity extends BaseActivity {


    String packageName;

    public static final String PACKAGE_NAME = "packageName";
    @BindView(R.id.tv_app_name)
    TextView tvAppName;
    @BindView(R.id.tv_package_name)
    TextView tvPackageName;
    @BindView(R.id.tv_is_system)
    TextView tvIsSystem;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindView(R.id.tv_lower_case_signature)
    TextView tvLowerCaseSignature;
    @BindView(R.id.tv_signature_sha1)
    TextView tvSignatureSha1;
    @BindView(R.id.tv_lower_case_signature_sha1)
    TextView tvLowerCaseSignatureSha1;

    public static void start(Activity activity, String packageName) {
        Intent intent = new Intent(activity, SingnatureDetailsActivity.class);
        intent.putExtra(PACKAGE_NAME, packageName);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_singnature_details;
    }

    protected void setStatusBarColor() {
        int mStatusBarColor = ContextCompat.getColor(mContext, com.baselib.R.color.status_bar_color);
        StatusBarUtil.setColorForSwipeBack(this, mStatusBarColor, 0);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        setTitle("应用详情");

        packageName = getIntent().getStringExtra(PACKAGE_NAME);


        tvPackageName.setText(packageName);

        tvAppName.setText(AppUtils.getAppName(packageName));
        if (AppUtils.isAppSystem(packageName)) {
            tvIsSystem.setText("是");
        } else {
            tvIsSystem.setText("否");
        }

        Signature[] signatures = AppUtils.getAppSignature(packageName);
        byte[] signatByte = signatures[0].toByteArray();
        String signature = EncryptUtils.encryptMD5ToString(signatByte);

        tvSignature.setText(signature);
        tvLowerCaseSignature.setText(signature.toLowerCase());


        String signatureSHA1 = AppUtils.getAppSignatureSHA1(packageName);
        tvSignatureSha1.setText(signatureSHA1);
        tvLowerCaseSignatureSha1.setText(signatureSHA1.toLowerCase());

    }
}
