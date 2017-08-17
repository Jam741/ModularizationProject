package com.yingwumeijia.android.worker.function.minecase

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView

/**
 * Created by Jam on 2017/3/3 下午4:55.
 * Describe:
 */

interface MineCaseContract {

    interface View : JBaseView {

        fun onResponse(list: List<*>)


        fun loadComplete(page: Int, isEmpty: Boolean)


        fun showEmptyLayout(empty: Boolean)
    }

    interface Presenter : JBasePresenter {

        fun loadMineCase(pageNum: Int)


    }
}
