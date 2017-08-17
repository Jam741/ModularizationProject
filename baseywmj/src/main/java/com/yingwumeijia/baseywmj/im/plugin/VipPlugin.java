package com.yingwumeijia.baseywmj.im.plugin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;


import com.yingwumeijia.baseywmj.R;
import com.yingwumeijia.baseywmj.function.StartActivityManager;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;


/**
 * Created by Jam on 2016/12/15 下午11:31.
 * Describe:
 */

public class VipPlugin implements IPluginModule {
    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.rc_ext_plugin_vip_selector);

    }

    @Override
    public String obtainTitle(Context context) {
        return "会员权益";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        StartActivityManager.INSTANCE.startVipInfoPage(fragment.getContext());
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}
