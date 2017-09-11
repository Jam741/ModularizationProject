package com.yingwumeijia.android.ywmj.client.function.historyView

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.entity.bean.CaseBean
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration
import kotlinx.android.synthetic.main.history_view_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/7/4.
 */
class HistoryViewActivity : JBaseActivity(), HistoryViewContract.View {

    val presenter by lazy { HistoryViewPresenter(context, this) }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, HistoryViewActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.history_view_act)

        topTitle.text = "历史浏览"
        topLeft.setOnClickListener { close() }
        topRight.textSize = 15f
        topRight.text = "清空"
        topRight.setOnClickListener { if (presenter.historyViewAdapter.itemCount == 0) toastWith("暂无历史浏览") else showClearHistoryDialog() }

        rv_content.run {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(HorizontalDividerItemDecoration.Builder(context).margin(R.dimen.vertical_margin).build())
            adapter = presenter.historyViewAdapter
        }

    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }


    override fun showHistoryNum(mun: Int) {
        tv_historyNum.text = "最近" + mun + "条"
    }

    override fun showHistoryList(caseBeen: List<CaseBean>) {
        presenter.historyViewAdapter.refresh(caseBeen as ArrayList<CaseBean>)
    }

    override fun showEmpty(empty: Boolean) {
        empty_layout.visibility = if (empty) View.VISIBLE else View.GONE
        ll_content.visibility = if (!empty) View.VISIBLE else View.GONE
    }

    override fun showClearHistoryDialog() {
        AlertDialog.Builder(context)
                .setTitle(R.string.dialog_title)
                .setMessage("确认要清空历史浏览记录吗？\n 清空后将不再显示")
                .setNegativeButton("取消", null)
                .setPositiveButton("确认", { _, _ -> presenter.clear() })
                .show()
    }
}