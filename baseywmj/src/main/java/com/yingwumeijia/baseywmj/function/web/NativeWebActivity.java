package com.yingwumeijia.baseywmj.function.web;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps2d.model.Text;
import com.orhanobut.logger.Logger;
import com.yingwumeijia.baseywmj.R;
import com.yingwumeijia.baseywmj.base.JBaseActivity;

import org.jetbrains.annotations.Nullable;

/**
 * Created by jamisonline on 2017/9/1.
 */

public class NativeWebActivity extends JBaseActivity {

    private WebView webView;

    private WebSettings webSettings;

    FrameLayout webviewContainerView;

    private String targetUrl;
    private String title;
    private boolean hasTitle;

    private NativeJsIntelligencer jsBradge;


    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private final static int FILE_CHOOSER_RESULT_CODE = 10000;


    public static void start(Context context, String url, String title, Boolean hasTitle) {
        Intent starter = new Intent(context, NativeWebActivity.class);
        starter.putExtra("KEY_URL", url);
        starter.putExtra("KEY_TITLE", title);
        starter.putExtra("KEY_HAS_TITLE", hasTitle);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.super_web_act);

        targetUrl = getIntent().getStringExtra("KEY_URL");
        if (!TextUtils.isEmpty(getIntent().getStringExtra("KEY_TITLE")))
            title = getIntent().getStringExtra("KEY_TITLE");
        hasTitle = getIntent().getBooleanExtra("", false);

        RelativeLayout toolbar = (RelativeLayout) this.findViewById(R.id.toolbar);
        toolbar.setVisibility(hasTitle ? View.VISIBLE : View.GONE);
        if (hasTitle) {
            TextView topTitle = (TextView) this.findViewById(R.id.topTitle);
            if (!TextUtils.isEmpty(title))
                topTitle.setText(title);
        }

        jsBradge = new NativeJsIntelligencer(this);
        webView = new WebView(this);
        webviewContainerView = (FrameLayout) this.findViewById(R.id.webviewContainerView);
        webviewContainerView.addView(webView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize((1024 * 1024 * 8));
        webSettings.setDatabaseEnabled(true);
        String appCachePath = context.getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.addJavascriptInterface(jsBradge, "jsIntelligencer");
        jsBradge.webView = webView;

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            // For Android  >= 3.0
            public void openFileChooser(ValueCallback valueCallback, String acceptType) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            //For Android  >= 4.1
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                uploadMessage = valueCallback;
                openImageChooserActivity();
            }

            // For Android >= 5.0
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                uploadMessageAboveL = filePathCallback;
                openImageChooserActivity();
                return true;
            }
        });

        Logger.d(targetUrl);
        webView.loadUrl(targetUrl);
    }

    private void openImageChooserActivity() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {
            if (null == uploadMessage && null == uploadMessageAboveL) return;
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (uploadMessageAboveL != null) {
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (uploadMessage != null) {
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }
}
