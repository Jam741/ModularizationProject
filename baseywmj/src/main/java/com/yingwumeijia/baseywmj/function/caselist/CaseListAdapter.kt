package com.yingwumeijia.baseywmj.function.caselist

import android.app.Activity
import android.support.v4.app.Fragment
import android.view.View
import android.widget.RelativeLayout
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.entity.bean.CaseBean
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import java.text.DecimalFormat

/**
 * Created by jamisonline on 2017/5/31.
 */
class CaseListAdapter : CommonRecyclerAdapter<CaseBean> {

    constructor(activity: Activity?, data: ArrayList<CaseBean>?) : super(activity, null, data, R.layout.item_case_list)
    constructor(fragment: Fragment, data: ArrayList<CaseBean>?) : super(null, fragment, data, R.layout.item_case_list)

    override fun convert(holder: RecyclerViewHolder, caseBean: CaseBean, position: Int) {
        val caseName = caseBean.getCaseName()

        val tvName = holder.getViewWith(R.id.tv_name)
        val tv_describe = holder.getViewWith(R.id.tv_describe)

        val params = tvName.getLayoutParams() as RelativeLayout.LayoutParams
        params.setMargins(0, ScreenUtils.dp2px(7f, activity!!), 0, ScreenUtils.dp2px(3f, activity!!))
        tvName.setLayoutParams(params)
        tvName.setPadding(ScreenUtils.dp2px(20f, activity!!), 0, 0, 0)
        tv_describe.setPadding(ScreenUtils.dp2px(20f, activity!!), 0, 0, 0)

        holder.run {
            setTextWith(R.id.tv_collect_count, fromtNumWan(caseBean.getCollectionCount()))
            setTextWith(R.id.tv_view_count, "浏览 " + fromtNumWan(caseBean.getViewCount()))
            setTextWith(R.id.tv_name, caseName)
            setTextWith(R.id.tv_describe, caseBean.getStyle() + " / "
                    + caseBean.getHouseType() + " / "
                    + caseBean.getHouseArea() + "m²")
//                .setImageURL(R.id.iv_icon, caseBean.getCaseCover() + Constant.IMAGE_PREVIEW_720, activity)
            setVisible(R.id.has720Layout, caseBean.isHas720())
            setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                override fun itemClick(itemView: View, position: Int) {
//                        StartActivityManager.startCaseDetailActivity(mContext, caseBean.getCaseId())
                }
            })
        }
    }


    private fun fromtNumWan(sourceNum: Long): String {
        if (sourceNum < 10000) {
            return sourceNum.toString()
        } else {
            val df = DecimalFormat("######0.00")
            val d = java.lang.Double.valueOf(sourceNum.toDouble())!! / 10000
            return df.format(d) + "万"
        }
    }

}