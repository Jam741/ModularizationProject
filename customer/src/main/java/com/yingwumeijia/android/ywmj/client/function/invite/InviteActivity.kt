package com.yingwumeijia.android.ywmj.client.function.invite

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.sina.weibo.sdk.share.WbShareCallback
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.sharelibrary.ShareData
import com.yingwumeijia.sharelibrary.ShareManager
import kotlinx.android.synthetic.main.invite_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/7/5.
 */
class InviteActivity : JBaseActivity(), WbShareCallback {


    private val shareTitle = "定制家装就找鹦鹉美家"
    private val shareSubTitle = "先验货，再找人，比亲朋好友介绍更靠谱！"
    private val shareUrl = "https://a.app.qq.com/o/simple.jsp?pkgname=com.yingwumeijia.android.ywmj.client"
    private val bitmapBytes: ByteArray? = null
    private val imageUrl = "http://o7zlnyltf.bkt.clouddn.com/down_load_pic.png"

    var shareData: ShareData? = null
    var shareManager: ShareManager? = null

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, InviteActivity::class.java)
            context.startActivity(starter)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.invite_act)
        topTitle.text = "推荐给朋友使用"
        iv_image.setImageBitmap(encodeAsBitmap(shareUrl))
        progressDialog.show()
        Glide.with(context.applicationContext).load(imageUrl + "?imageView2/2/w/256").asBitmap().into(object : SimpleTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {
                progressDialog.dismiss()
                shareData = ShareData(shareTitle, shareSubTitle, shareUrl, resource, imageUrl, 1)
                shareManager = ShareManager(context, shareData!!, object : WbShareCallback {
                    override fun onWbShareFail() {}

                    override fun onWbShareCancel() {}

                    override fun onWbShareSuccess() {}
                }, object : IUiListener {
                    override fun onComplete(p0: Any?) {}

                    override fun onCancel() {}

                    override fun onError(p0: UiError?) {}
                })
            }
        })

        topLeft.setOnClickListener { close() }
        wchart.setOnClickListener { if (shareManager != null) shareManager!!.shareToWXConversation() }
        friends.setOnClickListener { if (shareManager != null) shareManager!!.shareToWXFirends() }
        message.setOnClickListener { getContactInfo() }
        weibo.setOnClickListener { if (shareManager != null) shareManager!!.shareToWb() }
        qq.setOnClickListener { if (shareManager != null) shareManager!!.shareToQq() }


    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null && shareManager != null)
            shareManager!!.onNextIntent(intent!!)
    }


    internal fun encodeAsBitmap(str: String): Bitmap? {
        var bitmap: Bitmap? = null
        var result: BitMatrix? = null
        val multiFormatWriter = MultiFormatWriter()
        try {
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 200, 200)
            // 使用 ZXing Android Embedded 要写的代码
            val barcodeEncoder = BarcodeEncoder()
            bitmap = barcodeEncoder.createBitmap(result!!)
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (iae: IllegalArgumentException) { // ?
            return null
        }

        return bitmap
    }

    override fun onWbShareFail() {
    }

    override fun onWbShareCancel() {
    }

    override fun onWbShareSuccess() {
    }


    /**
     * 打开通讯了获取联系人信息
     */
    fun getContactInfo() {
        val i = Intent()
        i.action = Intent.ACTION_PICK
        i.data = ContactsContract.Contacts.CONTENT_URI
        startActivityForResult(i, Constant.REQUEST_CODE_GET_CONTACT_INFO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        Tencent.onActivityResultData(requestCode, resultCode, data, this)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                Constant.REQUEST_CODE_GET_CONTACT_INFO -> getUserContactFroData(data)
            }
        }
    }


    private fun getUserContactFroData(data: Intent?) {
        if (data == null) {
            return
        }

        val contactData = data.data ?: return

        var contactNumber: String? = null
        var contactName: String? = null

        val cursor = contentResolver.query(contactData, null, null, null, null)

        if (cursor.moveToFirst()) {
            var hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
            val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))

            if (hasPhone.equals("1", ignoreCase = true)) {
                hasPhone = "true"
            } else {
                hasPhone = "false"
            }
            if (java.lang.Boolean.parseBoolean(hasPhone)) {
                val phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null)
                while (phones.moveToNext()) {
                    contactNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    contactName = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                }
                phones.close()
            }

            if (!TextUtils.isEmpty(contactNumber))
                sendSmsWithBody(context, contactNumber!!, shareTitle + "\n" + shareSubTitle + "\n" + shareUrl)
        } else {
            Log.d("JAM", "error")

        }
    }


    /**
     * 调用系统界面，给指定的号码发送短信，并附带短信内容

     * @param context
     * *
     * @param number
     * *
     * @param body
     */
    fun sendSmsWithBody(context: Context, number: String, body: String) {
        val sendIntent = Intent(Intent.ACTION_SENDTO)
        sendIntent.data = Uri.parse("smsto:" + number)
        sendIntent.putExtra("sms_body", body)
        context.startActivity(sendIntent)
    }

}