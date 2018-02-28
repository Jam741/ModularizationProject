package com.yingwumeijia.android.ywmj.client.function.coupon

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView
import com.yingwumeijia.baseywmj.entity.bean.CouponListResponseBean

/**
 * Created by Jam on 2017/3/25 上午10:46.
 * Describe:
 */

interface CouponContract {

    interface View : JBaseView {

        fun onResonpse(list: List<CouponListResponseBean.ResultBean>)

        fun showEmptyLayout(empty: Boolean)

        fun onLoadComplete(page: Int, empty: Boolean)
    }

    interface Presenter : JBasePresenter {

        fun getCouponList(available: Boolean, pageNum: Int, pageSize: Int)

    }
}
