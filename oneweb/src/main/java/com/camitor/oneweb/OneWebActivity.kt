package com.camitor.oneweb

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.yingwumeijia.commonlibrary.base.BaseActivity
import kotlinx.android.synthetic.main.super_web_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*


/**
 * Created by jamisonline on 2017/7/3.
 */
class OneWebActivity : BaseActivity() {

    val urlStr by lazy { intent.getStringExtra(Constant.KEY_URL) }
    val hasTitle by lazy { intent.getBooleanExtra(Constant.KEY_HAS_TITLE, false) }
    var jsBradge: BaseJsBirdge? = null
    var titleStr: String? = null


    val webView: WebView by lazy { WebView(context) }
    val webSettings by lazy { webView.settings }


    companion object {
        fun start(context: Context, url: String, title: String?, hasTitlte: Boolean) {
            val starter = Intent(context, OneWebActivity::class.java)
            starter.putExtra(Constant.KEY_URL, url)
            starter.putExtra(Constant.KEY_TITLE, title)
            starter.putExtra(Constant.KEY_HAS_TITLE, hasTitlte)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.super_web_act)
        window.setFormat(PixelFormat.TRANSLUCENT)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)


        if (!TextUtils.isEmpty(intent.getStringExtra(Constant.KEY_TITLE)))
            titleStr = intent.getStringExtra(Constant.KEY_TITLE)

        progressDialog.show()
        topLeft.setOnClickListener { close() }
        if (hasTitle) toolbar.visibility = View.VISIBLE else toolbar.visibility = View.GONE
        if (!TextUtils.isEmpty(titleStr)) topTitle.text = titleStr
        webviewContainerView.addView(webView, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT))

        webSettings.domStorageEnabled = true
        webSettings.setAppCacheMaxSize((1024 * 1024 * 8).toLong())
        webSettings.databaseEnabled = true
        val appCachePath = context.applicationContext.cacheDir.absolutePath
        webSettings.setAppCachePath(appCachePath)
        webSettings.allowContentAccess = true
        webSettings.setAppCacheEnabled(true)
        webSettings.javaScriptEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE

        webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(p0: WebView?, p1: String?): Boolean {
                p0!!.loadUrl(p1)
                return true
            }

            override fun onReceivedSslError(p0: WebView?, p1: SslErrorHandler?, p2: SslError?) {
                p1!!.proceed()
            }

            override fun onReceivedError(p0: WebView?, p1: Int, p2: String?, p3: String?) {
                super.onReceivedError(p0, p1, p2, p3)
                progressDialog.dismiss()
            }

        })

        webView.setWebChromeClient(object : WebChromeClient() {

            override fun onProgressChanged(p0: WebView?, p1: Int) {
                super.onProgressChanged(p0, p1)
                if (p1 > 95) progressDialog.dismiss()
            }


            override fun onReceivedTitle(p0: WebView?, p1: String?) {
                super.onReceivedTitle(p0, p1)
                if (hasTitle && TextUtils.isEmpty(titleStr) && !TextUtils.isEmpty(p1))
                    topTitle.text = p1
            }
        })

        if (jsBradge != null) {
            webView.addJavascriptInterface(jsBradge, "jsIntelligencer")
            jsBradge!!.webView = webView
        }

        if (null != savedInstanceState) {
            webView.restoreState(savedInstanceState)
        } else {
            webView.loadUrl(urlStr)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        webView.saveState(outState)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (jsBradge != null)
            jsBradge!!.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()
        if (jsBradge != null)
            jsBradge!!.onResume()
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onDestroy() {
        if (webView != null) {
            webView.stopLoading()
            webviewContainerView.removeView(webView)
            webView.destroy()
        }
        super.onDestroy()
    }
}