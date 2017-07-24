package com.yingwumeijia.sharelibrary

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast

/**
 * Created by jamisonline on 2017/7/10.
 */
class ShareDialog(var shareManager: ShareManager) {

    val dialog by lazy { createShareDialog() }

    val rootView by lazy { initRootView() }

    private fun initRootView(): View {
        val view = LayoutInflater.from(shareManager.context).inflate(R.layout.share_dialog_layout, null)
        view.findViewById(R.id.btn_shareToWechat).setOnClickListener {
            dialog.dismiss()
            shareManager.shareToWXConversation()
        }
        view.findViewById(R.id.btn_shareToFriends).setOnClickListener {
            dialog.dismiss()
            shareManager.shareToWXFirends()
        }
        view.findViewById(R.id.btn_shareToWeibo).setOnClickListener {
            dialog.dismiss()
            shareManager.shareToWb()
        }
        view.findViewById(R.id.btn_copyLink).setOnClickListener {
            val clipData = ClipData.newPlainText("text", shareManager.shareData.url)
            (shareManager.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).primaryClip = clipData
            dialog.dismiss()
            Toast.makeText(shareManager.context, "复制成功", Toast.LENGTH_SHORT).show()
        }

        return view
    }


    private fun createShareDialog(): AlertDialog {
        return AlertDialog.Builder(shareManager.context)
                .setView(rootView)
                .create()
    }

    fun show() {
        dialog.show()
    }


}