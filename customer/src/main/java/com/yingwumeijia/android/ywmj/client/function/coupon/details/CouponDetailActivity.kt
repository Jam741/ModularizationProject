package com.yingwumeijia.android.ywmj.client.function.coupon.details

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import com.camitor.scanlibrary.ScanSDKManager
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.CouponDetail
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber
import com.yingwumeijia.commonlibrary.utils.FromartDateUtil
import com.yingwumeijia.commonlibrary.widget.SpannableTextView
import kotlinx.android.synthetic.main.coupon_detail_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/7/4.
 */
class CouponDetailActivity : JBaseActivity() {

    val couponId by lazy { intent.getIntExtra(Constant.KEY_COUPON_CODE, Constant.DEFAULT_INT_VALUE) }

    lateinit var couponCode: String

    val CONPON_COED_STR = "promotion.coupon.code.yingwumeijia.com"


    companion object {
        fun start(context: Context, couponCode: Int) {
            val starter = Intent(context, CouponDetailActivity::class.java)
            starter.putExtra(Constant.KEY_COUPON_CODE, couponCode)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coupon_detail_act)

        loadCouponData()
        topTitle.text = "活动券详情"
        topLeft.setOnClickListener { close() }
        btn_commit.setOnClickListener { ScanSDKManager.simpleScan(context) }
    }

    private fun loadCouponData() {
        val ob = Api.service.getCouponDetail(couponId)
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : SimpleSubscriber<CouponDetail>(context) {
            override fun _onNext(t: CouponDetail?) {
                if (t == null) return Unit
                initCouponDetail(t!!)
            }

        })
    }

    private fun initCouponDetail(t: CouponDetail) {
        couponCode = t.couponCode
        btn_commit.isEnabled = t.isEffective
        if (t.isCharge) createSpannableTextViewForPrice(tv_price, "¥  ", "" + t.amount) else createSpannableTextViewForPrice(tv_price, "  ", "免费")
        tv_subtitle.text = t.description
        var startTime = FromartDateUtil.fromartDateYMdHm(t.beginTime)
        var endTime = FromartDateUtil.fromartDateYMdHm(t.endTime)
        tv_date.text = startTime + " - " + endTime
        createSpannableTextViewForCode(tv_code, "券码：", couponCode)

        iv_code.setImageBitmap(encodeAsBitmap(CONPON_COED_STR + t.couponCode))

    }


    fun createSpannableTextViewForCode(tv: SpannableTextView, title: String, content: String) {

        // clear pieces
        tv.reset()
        // Add the first piece
        tv.addPiece(SpannableTextView.Piece.Builder(title).textColor(resources.getColor(R.color.color_1))
                .style(Typeface.NORMAL).textSize(resources.getDimension(R.dimen.font1_sp).toInt()).build())

        // Add the second piece
        tv.addPiece(SpannableTextView.Piece.Builder(content).textColor(resources.getColor(R.color.color_1))
                .style(Typeface.BOLD).textSize(resources.getDimension(R.dimen.font1_sp).toInt()).build())

        // Display the final, styled text
        tv.display()
    }

    fun createSpannableTextViewForPrice(tv: SpannableTextView, title: String, content: String) {

        // clear pieces
        tv.reset()
        // Add the first piece
        tv.addPiece(SpannableTextView.Piece.Builder(title).textColor(resources.getColor(R.color.color_1))
                .style(Typeface.BOLD).textSize(resources.getDimension(R.dimen.font_coupon_detail_price1).toInt()).build())

        // Add the second piece
        tv.addPiece(SpannableTextView.Piece.Builder(content).textColor(resources.getColor(R.color.color_4))
                .style(Typeface.BOLD).textSize(resources.getDimension(R.dimen.font_coupon_detail_price1).toInt()).build())

        // Display the final, styled text
        tv.display()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result == null) {
            super.onActivityResult(requestCode, resultCode, data)
        } else {
            if (result.contents == null) {
                toastWith("取消")
            } else {
                try {
                    val gson = Gson()
                    val info = gson.fromJson(result.contents, SiginInfo::class.java)
                    if (info != null) {
                        if (!TextUtils.isEmpty(info.sign) && info.activityId != 0)
                            siginToSever(info)
                        else {
                            toastWith("识别失败")
                        }
                    } else {
                        toastWith("识别失败")
                    }
                } catch (e: Exception) {
                    toastWith("识别失败")
                }

            }
        }
    }


    private fun siginToSever(info: SiginInfo) {
        val ob = Api.service.couponSign(info.activityId, info.sign, couponCode)
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<String>(context) {
            override fun _onNext(t: String?) {
                btn_commit.run {
                    isEnabled = false
                    text = "已签到"
                }
                toastWith("签到成功")
            }

        })
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