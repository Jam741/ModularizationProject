package com.yingwumeijia.android.ywmj.client.function.coupon.details

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.android.ywmj.client.function.coupon.CouponPresenter
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.CouponDetail
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber
import com.yingwumeijia.commonlibrary.widget.SpannableTextView
import kotlinx.android.synthetic.main.coupon_detail_discount.*
import kotlinx.android.synthetic.main.item_coupon_list.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/7/4.
 */
class CouponDetailForiscountActivity : JBaseActivity() {

    val couponId by lazy { intent.getIntExtra(Constant.KEY_COUPON_CODE, Constant.DEFAULT_INT_VALUE) }

    companion object {
        fun start(context: Context, couponId: Int) {
            val starter = Intent(context, CouponDetailForiscountActivity::class.java)
            starter.putExtra(Constant.KEY_COUPON_CODE, couponId)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coupon_detail_discount)
        topTitle.text = "优惠券详情"
        topLeft.setOnClickListener { close() }
        loadCouponData()
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

    private fun initCouponDetail(couponDetail: CouponDetail) {

        val showDate = CouponPresenter.couponDate(couponDetail.getBeginTime(), couponDetail.getEndTime(), couponDetail.getCountDown(), !couponDetail.isEffective())


        if (couponDetail.getAmount().toInt() !== 0) {
            createSpannableTextViewForPrice(tv_couponPrice, "¥ ", "" + couponDetail.amount.toInt())
        } else {
            createSpannableTextViewForPrice(tv_couponPrice, "", "免费") }

        coupon_bg.setBackgroundColor(if (couponDetail.isEffective) Color.parseColor("#ed316b") else Color.parseColor("#CDCDCD"))
        tv_couponType.text = couponDetail.typeDesc
        tv_couponTitle.text = couponDetail.name
        tv_couponDesc.text = couponDetail.description
        tv_couponDate.text = showDate
        tv_couponDate.setTextColor(resources.getColor(if (couponDetail.isAvailable && showDate.contains("后过期")) R.color.color_6 else R.color.color_1))
        tv_rule.setText(couponDetail.usageRules)
    }


    fun createSpannableTextViewForPrice(tv: SpannableTextView, title: String, content: String) {

        // clear pieces
        tv.reset()
        // Add the first piece
        tv.addPiece(SpannableTextView.Piece.Builder(title).textColor(resources.getColor(R.color.bg_whide))
                .style(Typeface.BOLD).textSize(resources.getDimension(R.dimen.font_coupon_item_price1) as Int).build())

        // Add the second piece
        tv.addPiece(SpannableTextView.Piece.Builder(content).textColor(resources.getColor(R.color.bg_whide))
                .style(Typeface.BOLD).textSize(resources.getDimension(R.dimen.font_coupon_item_price2) as Int).build())

        // Display the final, styled text
        tv.display()
    }
}