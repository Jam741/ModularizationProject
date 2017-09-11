package com.yingwumeijia.baseywmj.function.cashier

import android.content.Intent
import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView
import com.yingwumeijia.baseywmj.entity.bean.BillSimpleInfo
import java.math.BigDecimal

/**
 * Created by jamisonline on 2017/8/7.
 */
interface CashierContract {

    interface View : JBaseView {

        fun didPaySuccess()

        fun didPayFailure()

        fun initBillSimple(billSimpleInfo: BillSimpleInfo)

    }

    interface Presenter : JBasePresenter {

        fun checkBillSimpleInfo()

        fun alPay()
        fun alPayPart(amount: BigDecimal)

        fun wxPay()
        fun wxPayPart(amount: BigDecimal)

        fun unionPay()
        fun unionPayPart(amount: BigDecimal)

        fun registerWXPay()

        fun unregisterWXPay()

        fun onUnionPayResult(data: Intent)

        fun toPay()

        fun toPayPart(amount: BigDecimal)
    }
}