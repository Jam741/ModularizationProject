package com.yingwumeijia.android.ywmj.client.function.coupon

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.android.ywmj.client.function.coupon.details.CouponDetailActivity
import com.yingwumeijia.android.ywmj.client.function.coupon.details.CouponDetailForiscountActivity
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.entity.bean.CouponListResponseBean
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber
import com.yingwumeijia.commonlibrary.utils.FromartDateUtil
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import com.yingwumeijia.commonlibrary.widget.SpannableTextView
import java.lang.Long
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jamisonline on 2017/7/4.
 */
class CouponPresenter(var context: Activity, var view: CouponContract.View, var available: Boolean) : CouponContract.Presenter {


    val couponAdapter by lazy {
        createCouponAdapter()
    }

    private fun createCouponAdapter(): CommonRecyclerAdapter<CouponListResponseBean.ResultBean> {
        return object : CommonRecyclerAdapter<CouponListResponseBean.ResultBean>(context, null, null, R.layout.item_coupon_list) {
            override fun convert(holder: RecyclerViewHolder, resultBean: CouponListResponseBean.ResultBean, position: Int) {
                val isLastOne = position == itemCount - 1

                val price = holder.getViewWith(R.id.tv_couponPrice) as SpannableTextView
                val showDate = couponDate(resultBean.beginTime, resultBean.endTime, resultBean.countDown, !resultBean.isEffective)
                if (!available) {
                    holder.setImageResource(R.id.tv_couponIcon, if (resultBean.isCouponUsed) R.mipmap.mine_ticket_worked_ico else R.mipmap.mine_ticket_no_work_ico)
                }

                if (resultBean.amount == null) {
                    createSpannableTextViewForPrice(price, "", "" + (resultBean.amount.toInt()))
                } else {
                    if (resultBean.amount.toInt() != 0) {
                        createSpannableTextViewForPrice(price, "¥ ", "" + (resultBean.amount.toInt()))
                    } else {
                        createSpannableTextViewForPrice(price, "", "免费")
                    }
                }

                holder.run {
                    setVisible(R.id.bottom, !isLastOne)
                    setVisible(R.id.top, position == 0)
                    setVisible(R.id.btn_checkPast, isLastOne && available)
                    setVisible(R.id.tv_couponIcon, !available)
                    setTextWith(R.id.tv_couponType, resultBean.typeDesc)
                    setBackgroundColor(R.id.coupon_bg, if (available) Color.parseColor("#ed316b") else Color.parseColor("#CDCDCD"))
                    setTextWith(R.id.tv_couponTitle, resultBean.name)
                    setTextWith(R.id.tv_couponDesc, resultBean.description)
                    setTextWith(R.id.tv_couponDate, showDate)
                    setTextColorRes(R.id.tv_couponDate, if (available && showDate.contains("后过期")) R.color.color_6 else R.color.color_1)
                    setOnClickListener(R.id.btn_checkPast, View.OnClickListener { CouponActivity.start(context, false) })
                    setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                        override fun itemClick(itemView: View, position: Int) {
                            if (resultBean.type === 3)
                                CouponDetailActivity.start(context, resultBean.id)
                            else
                                CouponDetailForiscountActivity.start(context, resultBean.id)
                        }
                    })
                }

            }

        }
    }

    fun createSpannableTextViewForPrice(tv: SpannableTextView, title: String, content: String) {

        // clear pieces
        tv.reset()
        // Add the first piece
        tv.addPiece(SpannableTextView.Piece.Builder(title).textColor(context.resources.getColor(R.color.bg_whide))
                .style(Typeface.BOLD).textSize(context.resources.getDimension(R.dimen.font_coupon_item_price1).toInt()).build())

        // Add the second piece
        tv.addPiece(SpannableTextView.Piece.Builder(content).textColor(context.resources.getColor(R.color.bg_whide))
                .style(Typeface.BOLD).textSize(context.resources.getDimension(R.dimen.font_coupon_item_price2) as Int).build())

        // Display the final, styled text
        tv.display()
    }


    companion object {
        fun couponDate(startTime: String, endTime: String?, countDown: Int, guoqi: Boolean): String {

            var showDate: String? = null

            if (guoqi) {
                if (endTime == null) {
                    showDate = "永久有效"
                } else {
                    showDate = FromartDateUtil.fromartDateYMd(startTime) + " - " + FromartDateUtil.fromartDateYMd(endTime)
                }
                return showDate
            }


            if (TextUtils.isEmpty(endTime)) {
                showDate = "永久有效"
            } else {
                val jiange = getJianInterval(endTime!!)
                if (jiange <= countDown) {
                    showDate = jiange.toString() + " 天后过期"
                } else {
                    showDate = "有效期至 " + FromartDateUtil.fromartDateYMd(endTime)
                }
            }
            return showDate
        }

        fun getJianInterval(inputTime: String): Int {
            var inputTime = inputTime

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            inputTime = simpleDateFormat.format(Date(Long.valueOf(inputTime)!!))

            val result: String? = null

            try {

                val sd = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

                val pos = ParsePosition(0)

                val d1 = sd.parse(inputTime, pos)

                // 用现在距离1970年的时间间隔new

                // Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔

                val time = d1.time - Date().time// 得出的时间间隔是毫秒

                Log.d("jam", "d1.getTime() " + d1.time)
                Log.d("jam", "time" + time)

                val jiangeDay = Math.floor((time / 86400000).toDouble()).toInt() + 1
                Log.d("jam", "jiangeDay" + jiangeDay)
                return jiangeDay
            } catch (e: Exception) {
                Log.d("jam", "error" + e.message)
                e.printStackTrace()
                return 0
            }

        }

    }


    override fun getCouponList(available: Boolean, pageNum: Int, pageSize: Int) {

        var ob = Api.service.getCouponList(available, pageNum, pageSize)
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : SimpleSubscriber<CouponListResponseBean>(context) {
            override fun _onNext(t: CouponListResponseBean?) {
                if (t == null) return
                view.onLoadComplete(pageNum, ListUtil.isEmpty(t.result))
                if (!ListUtil.isEmpty(t.result))
                    view.onResonpse(t.result)
            }

        })
    }


}