package com.yingwumeijia.baseywmj.function.collect.company

import android.content.Context
import com.yingwumeijia.baseywmj.function.collect.base.CollectListContract

/**
 * Created by jamisonline on 2017/6/30.
 */
class CompanyCollectPresenter(var context: Context,var view:CollectListContract.View): CollectListContract.Presenter {
    override fun loadData(page: Int, size: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancelCollect(targetId: Int, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}