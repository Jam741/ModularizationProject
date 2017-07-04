package com.yingwumeijia.baseywmj.function.collect.employee

import android.view.View
import android.widget.ImageView
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.function.collect.base.CollectListContract
import com.yingwumeijia.baseywmj.function.collect.base.CollectListFragment
import com.yingwumeijia.baseywmj.function.introduction.employee.EmployeeActivity
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder

/**
 * Created by jamisonline on 2017/7/1.
 */
class EmployeeCollectFragment : CollectListFragment<CollectEmployeeBean.ResultBean>(), CollectListContract.View {
    override fun createListAdapter(): CommonRecyclerAdapter<CollectEmployeeBean.ResultBean> {

        return object : CommonRecyclerAdapter<CollectEmployeeBean.ResultBean>(null, context, null, R.layout.item_session_member) {
            override fun convert(holder: RecyclerViewHolder, t: CollectEmployeeBean.ResultBean, position: Int) {
                JImageLolder.loadPortrait256(context!!, holder.getViewWith(R.id.iv_portrait) as ImageView, t.headImage)
                holder.run {
                    setVisible(R.id.employeeLayout, true)
                    setVisible(R.id.topPadding, position == 0)
                    setVisible(R.id.btn_checked, false)
                    setTextWith(R.id.tv_eName, t.name)
                    setTextWith(R.id.tv_eJob, t.title)
                    setTextWith(R.id.tv_eCompany, t.companyName)
                    setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                        override fun itemClick(itemView: View, position: Int) {
                            EmployeeActivity.start(mContext!!, t.userId, 0)
                        }

                    })
                    setOnItemLongClickListener(object : RecyclerViewHolder.OnItemLongCliceListener {
                        override fun itemLongClick(itemView: View, position: Int): Boolean {
                            showCancelDialog(t.userId, position)
                            return false
                        }

                    })

                }
            }

        }
    }

    override fun createCollectPresenter(): CollectListContract.Presenter {
        return EmployeeCollectPresenter(context.context, this)
    }

    override fun emptyText(): String {
        return "空空如也，快去收藏业者吧"
    }

}