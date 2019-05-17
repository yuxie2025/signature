package com.yuxie.singnature.adapter;

import android.widget.ImageView;

import com.blankj.utilcode.util.AppUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuxie.singnature.R;

public class SingnatureAdapter extends BaseQuickAdapter<AppUtils.AppInfo, BaseViewHolder> {
    public SingnatureAdapter() {
        super(R.layout.item_singnature_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, AppUtils.AppInfo item) {

        helper.setText(R.id.tv_name, item.getName() + "(" + item.getPackageName() + ")");

        ImageView ivLogo = helper.getView(R.id.iv_logo);

        ivLogo.setImageDrawable(item.getIcon());

    }
}
