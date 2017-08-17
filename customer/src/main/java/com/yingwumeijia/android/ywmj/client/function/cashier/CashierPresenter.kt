package com.yingwumeijia.android.ywmj.client.function.cashier

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.unionpay.UPPayAssistEx
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.android.ywmj.client.function.paysdk.alipay.AliPaySDK
import com.yingwumeijia.android.ywmj.client.function.paysdk.alipay.PayResult
import com.yingwumeijia.android.ywmj.client.function.paysdk.wxpay.WXPaySDK
import com.yingwumeijia.android.ywmj.client.wxapi.WXPayEntryActivity
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.ALPayOrderInfo
import com.yingwumeijia.baseywmj.entity.bean.BillSimpleInfo
import com.yingwumeijia.baseywmj.entity.bean.WxPayOrderInfo
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import java.math.BigDecimal

/**
 * Created by jamisonline on 2017/8/7.
 */
class CashierPresenter(val context: Activity, val view: CashierContract.View, val billId: String) : CashierContract.Presenter {


    companion object {
        val ybao_resquest = 10002
    }

    private var selectedPosition = 0

    val payTypeAdapter by lazy { createPayTypeAdapter() }

    val payTypeBeen by lazy { arrayListOf(PayTypeBean(R.mipmap.order_pay_alipay_ico, "支付宝", PayType.ALI), PayTypeBean(R.mipmap.order_pay_wechat_ico, "微信", PayType.WCHART)) }

    val wxPayBroadCostReceiver by lazy { WXPayBroadCostReceiver() }

    var payTypeChangedListener: PayTypeChangedListener? = null

    // “00” – 银联正式环境
    // “01” – 银联测试环境，该环境中不发生真实交易
    val serverMode = "01"


    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                AliPaySDK.SDK_PAY_FLAG -> {
                    val payResult = PayResult(msg.obj as Map<String, String>)
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    val resultInfo = payResult.result// 同步返回需要验证的信息
                    val resultStatus = payResult.resultStatus

                    Log.v("AA", payResult.toString())
                    Log.v("AA", resultInfo)
                    Log.v("AA", resultStatus)

                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        view.didPaySuccess()
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        view.didPayFailure()
                    }
                }
                else -> {
                }
            }
        }
    }


    private fun createPayTypeAdapter(): CommonRecyclerAdapter<PayTypeBean> {
        return object : CommonRecyclerAdapter<PayTypeBean>(context, null, payTypeBeen, R.layout.item_paytype) {

            override fun convert(holder: RecyclerViewHolder, payTypeBean: PayTypeBean, position: Int) {
                holder.run {
                    setImageResource(R.id.btnCheck, if (selectedPosition == position) R.mipmap.order_pay_choseing_ico else R.mipmap.order_pay_chose_ico)
                    setImageResource(R.id.iv_imag, payTypeBean.image)
                    setTextWith(R.id.tv_name, payTypeBean.name)
                    setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                        override fun itemClick(itemView: View, position: Int) {
                            if (payTypeChangedListener != null) payTypeChangedListener!!.onPayTypeChanged(payTypeBean)
                            selectedPosition = position
                            notifyDataSetChanged()
                        }
                    })
                }
            }
        }
    }


    override fun checkBillSimpleInfo() {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.checkBillSimpleInfo(billId), object : ProgressSubscriber<BillSimpleInfo>(context) {
            override fun _onNext(t: BillSimpleInfo?) {
                if (t == null) context.finish()
                view.initBillSimple(t!!)
            }

        })
    }

    override fun alPay() {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getALPayOrderInfo(billId), object : ProgressSubscriber<ALPayOrderInfo>(context) {
            override fun _onNext(t: ALPayOrderInfo?) {
                if (t == null) return
                AliPaySDK.Builder(context).appId(Config.PAY.ALIPAY_APPID).orderInfo(t.orderInfo).showLoading(true).handler(mHandler).build().pay()
            }
        })
    }

    override fun alPayPart(amount: BigDecimal) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getALPayOrderInfoPart(billId, amount), object : ProgressSubscriber<ALPayOrderInfo>(context) {
            override fun _onNext(t: ALPayOrderInfo?) {
                if (t == null) return
                AliPaySDK.Builder(context).appId(Config.PAY.ALIPAY_APPID).orderInfo(t.orderInfo).showLoading(true).handler(mHandler).build().pay()
            }
        })
    }

    override fun wxPay() {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getWXPayOrderInfo(billId), object : ProgressSubscriber<WxPayOrderInfo>(context) {
            override fun _onNext(t: WxPayOrderInfo?) {
                if (t == null) return
                WXPaySDK.Builder(context).appId(t.appId).nonceStr(t.nonceStr).timeStamp(t.timeStamp).sign(t.sign).parentId(t.partnerId).prepayId(t.prepayId).build().pay()
            }
        })
    }

    override fun wxPayPart(amount: BigDecimal) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getWXPayOrderInfoPart(billId, amount), object : ProgressSubscriber<WxPayOrderInfo>(context) {
            override fun _onNext(t: WxPayOrderInfo?) {
                if (t == null) return
                WXPaySDK.Builder(context).appId(t.appId).nonceStr(t.nonceStr).timeStamp(t.timeStamp).sign(t.sign).parentId(t.partnerId).prepayId(t.prepayId).build().pay()
            }
        })
    }

    override fun unionPay() {
        UPPayAssistEx.startPay(context, null, null, "899394085660622736701", serverMode)
    }

    override fun unionPayPart(amount: BigDecimal) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun toPay() {
        when (payTypeBeen[selectedPosition].payType) {
            PayType.ALI -> alPay()
            PayType.WCHART -> wxPay()
            PayType.UNION -> unionPay()
        }
    }

    override fun toPayPart(amount: BigDecimal) {
        when (payTypeBeen[selectedPosition].payType) {
            PayType.ALI -> alPayPart(amount)
            PayType.WCHART -> wxPayPart(amount)
            PayType.UNION -> unionPayPart(amount)
        }
    }

    /**
     * 银联支付回调
     */
    override fun onUnionPayResult(data: Intent) {
        var msg = ""
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        val str = data.extras.getString("pay_result")
        if (str.equals("success", ignoreCase = true)) {

            // 如果想对结果数据验签，可使用下面这段代码，但建议不验签，直接去商户后台查询交易结果
            // result_data结构见c）result_data参数说明
//            if (data.hasExtra("result_data")) {
//                val result = data.extras.getString("result_data")
//                try {
//                    val resultJson = JSONObject(result)
//                    val sign = resultJson.getString("sign")
//                    val dataOrg = resultJson.getString("data")
//                    // 此处的verify建议送去商户后台做验签
//                    // 如要放在手机端验，则代码必须支持更新证书
//                    val ret = verify(dataOrg, sign, serverMode)
//                    if (ret) {
//                        // 验签成功，显示支付结果
////                        msg = "支付成功！"
//                        view.didPaySuccess()
//                    } else {
//                        // 验签失败
////                        msg = "支付失败！"
//                        view.didPayFailure()
//                    }
//                } catch (e: JSONException) {
//                }
//
//            }
            // 结果result_data为成功时，去商户后台查询一下再展示成功
//            msg = "支付成功！"
            view.didPaySuccess()
        } else if (str.equals("fail", ignoreCase = true)) {
//            msg = "支付失败！"
            view.didPayFailure()
        } else if (str.equals("cancel", ignoreCase = true)) {
//            msg = "用户取消了支付"
            view.didPayFailure()
        }
    }


    override fun registerWXPay() {
        //注册微信支付广播
        val intentFilter = IntentFilter(WXPayEntryActivity.WX_PAY_BORAD_COST_ACTION)
        context.registerReceiver(wxPayBroadCostReceiver, intentFilter)
    }

    override fun unregisterWXPay() {
        context.unregisterReceiver(wxPayBroadCostReceiver)
    }


    private fun verify(msg: String, sign64: String, mode: String): Boolean {
        // 此处的verify，商户需送去商户后台做验签
        return true

    }


    inner class WXPayBroadCostReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val succ = intent.getBooleanExtra(Constant.PAY_SUCCESS_KEY, false)
            if (succ) {
                //显示充值成功的页面和需要的操作
                view.didPaySuccess()
            } else {
                view.didPayFailure()
            }
        }
    }

    interface PayTypeChangedListener {
        fun onPayTypeChanged(payTypeBean: PayTypeBean)
    }

}