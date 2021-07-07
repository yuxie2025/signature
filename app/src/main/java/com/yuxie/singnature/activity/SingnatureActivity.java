package com.yuxie.singnature.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.baselib.base.BaseActivity;
import com.baselib.ui.widget.LoadingDialog;
import com.baselib.uitls.CommonUtils;
import com.baselib.uitls.NoBugLinearLayoutManager;
import com.blankj.utilcode.util.AppUtils;
import com.jaeger.library.StatusBarUtil;
import com.yuxie.singnature.R;
import com.yuxie.singnature.adapter.SingnatureAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingnatureActivity extends BaseActivity {

    SingnatureAdapter adapter;

    List<AppUtils.AppInfo> datas;
    @BindView(R.id.rl_left)
    RelativeLayout rlLeft;
    @BindView(R.id.content)
    AppCompatEditText content;
    @BindView(R.id.search)
    AppCompatButton search;
    @BindView(R.id.is_show_system)
    SwitchCompat isShowSystem;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_singnature;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        rlLeft.setVisibility(View.INVISIBLE);

        setTitle("应用签名");

        datas = new ArrayList<>();
        initRecyclerView();

        initData();
    }

    private void initData() {
        new Thread(() -> {
            datas = AppUtils.getAppsInfo();
            runOnUiThread(() -> {
                isShowSystem(isShowSystem.isChecked());
            });
        }).start();
    }

    protected void setStatusBarColor() {
        int mStatusBarColor = ContextCompat.getColor(mContext, com.baselib.R.color.status_bar_color);
        StatusBarUtil.setColorForSwipeBack(this, mStatusBarColor, 0);
    }

    private void initRecyclerView() {
        adapter = new SingnatureAdapter();
        recyclerView.setLayoutManager(new NoBugLinearLayoutManager(mContext));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(((adapter1, view, position) -> {
            String packageName = adapter.getData().get(position).getPackageName();

            SingnatureDetailsActivity.start(this, packageName);
        }));

        isShowSystem.setOnCheckedChangeListener((CompoundButton compoundButton, boolean b) -> {
            isShowSystem(b);
        });

        search.setOnClickListener((view) -> {
            String contentStr = CommonUtils.getViewContent(content);

            if (TextUtils.isEmpty(contentStr)) {
                return;
            }

            List<AppUtils.AppInfo> appInfos = new ArrayList<>();
            for (int i = 0; i < datas.size(); i++) {
                String packName = datas.get(i).getPackageName();
                String name = datas.get(i).getName();
                //搜索包名或应用名
                if (packName.contains(contentStr) || name.contains(contentStr)) {

                    if (isShowSystem.isChecked()) {
                        appInfos.add(datas.get(i));
                    } else {
                        if (!datas.get(i).isSystem()) {
                            appInfos.add(datas.get(i));
                        }
                    }
                }
            }
            adapter.setNewData(appInfos);

        });


    }

    private void isShowSystem(boolean isShowSystem) {
        if (!isShowSystem) {
            List<AppUtils.AppInfo> appInfos = new ArrayList<>();
            for (int i = 0; i < datas.size(); i++) {
                if (!datas.get(i).isSystem()) {
                    appInfos.add(datas.get(i));
                }
            }
            adapter.setNewData(appInfos);
        } else {
            adapter.setNewData(datas);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
