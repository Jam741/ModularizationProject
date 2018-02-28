package com.camitor.oneweb

import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import com.orhanobut.logger.Logger
import com.tencent.smtt.export.external.interfaces.SslError
import com.tencent.smtt.export.external.interfaces.SslErrorHandler
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.yingwumeijia.commonlibrary.base.BaseFragment
import kotlinx.android.synthetic.main.super_web_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/7/10.
 */
class OneWebFragment : BaseFragment() {

    val urlStr by lazy { arguments.getString(Constant.KEY_URL) }
    val webView: WebView by lazy { WebView(activity) }
    val webSettings by lazy { webView.settings }

    var jsBradge: BaseJsBirdge? = null

    companion object {
        fun newInstance(url: String): OneWebFragment {
            val args = Bundle()
            val fragment = OneWebFragment()
            args.putString(Constant.KEY_URL, url)
            fragment.arguments = args
            return fragment
        }
    }

    fun setJsBridge(birdge: BaseJsBirdge) {
        this.jsBradge = birdge
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.super_web_act, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.window.setFormat(PixelFormat.TRANSLUCENT)
        activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        toolbar.visibility = View.GONE

        progressDialog.show()

        webviewContainerView.addView(webView, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT))

        webSettings.domStorageEnabled = true
        webSettings.setAppCacheMaxSize((1024 * 1024 * 8).toLong())
        webSettings.databaseEnabled = true
        val appCachePath = activity.applicationContext.cacheDir.absolutePath
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
        })

        if (jsBradge != null) {
            Logger.d(jsBradge)
            webView.addJavascriptInterface(jsBradge, "jsIntelligencer")
            jsBradge!!.webView = webView
        }


        if (null != savedInstanceState) {
            webView.restoreState(savedInstanceState)
        } else {
            webView.loadUrl(urlStr)
        }
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

    override fun onDestroyView() {
        if (webView != null) {
            webView.stopLoading()
            webviewContainerView.removeView(webView)
            webView.destroy()
        }
        super.onDestroyView()

    }
}