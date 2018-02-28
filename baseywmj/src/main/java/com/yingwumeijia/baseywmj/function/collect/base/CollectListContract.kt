package com.yingwumeijia.baseywmj.function.collect.base

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView

/**
 * Created by jamisonline on 2017/6/30.
 */
interface CollectListContract {

    interface View : JBaseView {

        fun onResponse(data: List<*>)

        fun showEmpty(empty: Boolean)

        fun cancelSucc(position: Int)

        fun onLoadComplete(page: Int, empty: Boolean)

        fun showCancelDialog(targetId: Int, position: Int)

    }

    interface Presenter : JBasePresenter {

        fun loadData(page: Int, size: Int)

        fun cancelCollect(targetId: Int, position: Int)

    }
}