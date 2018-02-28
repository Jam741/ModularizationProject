package com.yingwumeijia.android.ywmj.client.function.historyView

import com.yingwumeijia.baseywmj.base.JBasePresenter
import com.yingwumeijia.baseywmj.base.JBaseView
import com.yingwumeijia.baseywmj.entity.bean.CaseBean


/**
 * Created by Jam on 2017/5/8 下午6:15.
 * Describe:
 */

interface HistoryViewContract {


    interface View : JBaseView {

        fun showHistoryNum(mun: Int)

        fun showHistoryList(caseBeen: List<CaseBean>)

        fun showEmpty(empty: Boolean)

        fun showClearHistoryDialog()
    }


    interface Presenter : JBasePresenter {

        fun start()

        fun clear()
    }


}
