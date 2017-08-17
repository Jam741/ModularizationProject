package com.yingwumeijia.baseywmj.im.plugin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.yingwumeijia.baseywmj.R;
import com.yingwumeijia.baseywmj.function.UserManager;
import com.yingwumeijia.baseywmj.function.WebViewManager;
import com.yingwumeijia.baseywmj.utils.net.SeverUrlManager;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;

/**
 * Created by Jam on 2016/12/15 下午11:42.
 * Describe:
 */

public class OrderPlugin implements IPluginModule {
    @Override
    public Drawable obtainDrawable(Context context) {
        return ContextCompat.getDrawable(context, R.drawable.rc_ext_plugin_order_selector);
    }

    @Override
    public String obtainTitle(Context context) {
        return "创建订单";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {

        if (UserManager.INSTANCE.getUserData().getUserDetailType() == 10) {
            Toast.makeText(fragment.getContext(), "家居顾问不能创建订单", Toast.LENGTH_SHORT).show();
        } else {
            WebViewManager.INSTANCE.startFullScreen(fragment.getActivity(), SeverUrlManager.INSTANCE.baseWebUrl() + "#/createOrder?type=1&sessionId=" + rongExtension.getTargetId() + "&oneselfRole=" + UserManager.INSTANCE.getUserData().getUserDetailTypeDesc());
            // OneWebActivity.start(fragment.getActivity(), null, BaseUrlManager.h5BaseUrl(fragment.getContext()) + "#/createOrder?type=1&sessionId=" + rongExtension.getTargetId() + "&oneselfRole=" + UserManager.getUserInfo().getUserDetailTypeDesc(), false);
        }
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}
