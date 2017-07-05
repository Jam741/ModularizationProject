package com.yingwumeijia.android.ywmj.client.function.invite

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import kotlinx.android.synthetic.main.invite_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/7/5.
 */
class InviteActivity : JBaseActivity() {

    private val shareTitle = "定制家装就找鹦鹉美家"
    private val shareSubTitle = "先验货，再找人，比亲朋好友介绍更靠谱！"
    private val shareUrl = "https://a.app.qq.com/o/simple.jsp?pkgname=com.yingwumeijia.android.ywmj.client"
    private val bitmapBytes: ByteArray? = null
    private val imageUrl = "http://o7zlnyltf.bkt.clouddn.com/down_load_pic.png"


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

        topLeft.setOnClickListener { close() }
        wchart.setOnClickListener { }
        friends.setOnClickListener { }
        message.setOnClickListener { }
        weibo.setOnClickListener { }
        qq.setOnClickListener { }
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
        } catch (e: WriterException) {
            e.printStackTrace()
        } catch (iae: IllegalArgumentException) { // ?
            return null
        }

        return bitmap
    }
}