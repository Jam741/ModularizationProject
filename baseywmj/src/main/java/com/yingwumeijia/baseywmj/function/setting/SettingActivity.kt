package com.yingwumeijia.baseywmj.function.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.function.StartActivityManager
import com.yingwumeijia.baseywmj.function.setting.about.AboutUsActivity
import com.yingwumeijia.baseywmj.function.setting.pwd.SetPasswordActivity
import kotlinx.android.synthetic.main.setting_frag.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/6/30.
 */
class SettingActivity : JBaseActivity(), SettingContract.View {

    val presenter by lazy { SettingPresenter(this, this) }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, SettingActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_frag)
        topLeft.setOnClickListener { close() }
        topTitle.text = "设置"

        btn_set_pwd.setOnClickListener { SetPasswordActivity.start(context) }
        btn_clear_cache.setOnClickListener {
            AlertDialog
                    .Builder(context)
                    .setMessage(R.string.dialog_clear_cache)
                    .setTitle(R.string.dialog_title)
                    .setNegativeButton(R.string.btn_cancel, { _, _ -> })
                    .setPositiveButton(R.string.btn_confirm, { _, _ -> presenter.clearnCache() })
                    .show()
        }
        btn_service_agreement.setOnClickListener { StartActivityManager.startAgreementActivity(context) }
        btn_aboout_us.setOnClickListener { AboutUsActivity.start(context) }
        btn_login_out.setOnClickListener { presenter.loginOut() }
        btn_grade.setOnClickListener {
            try {
                val uri = Uri.parse("market://details?id=" + context.packageName)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } catch (e: Exception) {
                toastWith("没有找到应用市场")
            }
        }

        presenter.start()
    }


    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun showLoginOutButton(show: Boolean) {
        switch_push.isEnabled = show
        if (show) btn_login_out.visibility = View.VISIBLE else btn_login_out.visibility = View.GONE
    }

    override fun showCurrentCache(cache: String) {
        tv_cache.text = cache
    }


}