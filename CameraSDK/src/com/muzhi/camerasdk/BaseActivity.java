package com.muzhi.camerasdk;


import com.muzhi.mtools.utils.SystemBarTintManager;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


public abstract class BaseActivity extends FragmentActivity{
	
	protected Context mContext;
	protected TextView btn_back,mActionBarTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//不可横屏幕

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				//透明状态栏
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		}
		/*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}

		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.camerasdk_action_bar);*/
		
		
	}
	
	/*@TargetApi(19) 
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}*/
	
	
	
	public void setActionBarTitle(String s) {
		if(mActionBarTitle==null){
			mActionBarTitle = (TextView)findViewById(R.id.camerasdk_actionbar_title);
		}
		mActionBarTitle.setText(s);
	}
	
	//显示返回按钮
	public void showLeftIcon(){
		if(btn_back==null){
			btn_back = (TextView)findViewById(R.id.camerasdk_btn_back);
		}
		btn_back.setVisibility(View.VISIBLE);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	
}
