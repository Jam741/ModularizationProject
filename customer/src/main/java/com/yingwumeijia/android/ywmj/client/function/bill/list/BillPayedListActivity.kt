package com.yingwumeijia.android.ywmj.client.function.bill.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.android.ywmj.client.function.bill.list.detail.BillPayedDetailActivity
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.entity.bean.BillItemBean
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.FromartDateUtil
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import kotlinx.android.synthetic.main.bill_list_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*
import java.lang.Long
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jamisonline on 2017/7/27.
 */
class BillPayedListActivity : JBaseActivity() {

    val paymentAdapter by lazy { createAdapter() }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BillPayedListActivity::class.java)
            context.startActivity(starter)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bill_list_act)
        topTitle.text = "等待支付"

        rv_content.run {
            layoutManager = LinearLayoutManager(context)
            adapter = paymentAdapter
        }

        topLeft.setOnClickListener { close() }
    }

    fun loadData() {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getBillPayedList(), object : ProgressSubscriber<List<BillItemBean>>(context) {
            override fun _onNext(t: List<BillItemBean>?) {
                showEmptyLayout(ListUtil.isEmpty(t))
                if (!ListUtil.isEmpty(t))
                    paymentAdapter.refresh(t!! as ArrayList<BillItemBean>)
            }

        })
    }


    private fun createAdapter(): CommonRecyclerAdapter<BillItemBean> {
        return object : CommonRecyclerAdapter<BillItemBean>(context, null, null, R.layout.item_bill_payed) {
            override fun convert(holder: RecyclerViewHolder, billItemBean: BillItemBean, position: Int) {

                val shouZhi = if (billItemBean.getRevExpType() === 1) "+" else "-"

                holder.run {
                    setVisible(R.id.line, !isLastOne(position))
                    setTextWith(R.id.tv_price, shouZhi + billItemBean.amount.intValueExact())
                    setTextWith(R.id.tv_name, billItemBean.name)
                    setTextWith(R.id.tv_date, if (billItemBean.payTime == null) "" else FromartDateUtil.fromartDateYMd(billItemBean.payTime))
                    setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                        override fun itemClick(itemView: View, position: Int) {
                            TODO("创建订单页面")
                            BillPayedDetailActivity.start(context, billItemBean.billId.toString())                        }
                    })
                }

            }
        }
    }

    fun fromartDateY(times: String): String {
        val sdf = SimpleDateFormat("yyyy")
        val date = sdf.format(Date(Long.valueOf(times)!!))
        return date
    }

    fun fromartDateMd(times: String): String {
        val sdf = SimpleDateFormat("MM.dd")
        val date = sdf.format(Date(Long.valueOf(times)!!))
        return date
    }


    private fun showEmptyLayout(emoty: Boolean) {
        empty_layout.visibility = if (emoty) View.VISIBLE else View.GONE
        rv_content.visibility = if (!emoty) View.VISIBLE else View.GONE
    }

}