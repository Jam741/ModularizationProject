package com.yingwumeijia.baseywmj.function.caselist

import android.app.Activity
import android.support.v4.app.Fragment
import com.yingwumeijia.baseywmj.entity.bean.CaseBean
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder

/**
 * Created by jamisonline on 2017/5/31.
 */
class CaseListAdapter(activity: Activity?, fragment: Fragment?, data: ArrayList<CaseBean>, layoutId: Int) : CommonRecyclerAdapter<CaseBean>(activity, fragment, data, layoutId) {
    override fun convert(holder: RecyclerViewHolder, t: CaseBean, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}