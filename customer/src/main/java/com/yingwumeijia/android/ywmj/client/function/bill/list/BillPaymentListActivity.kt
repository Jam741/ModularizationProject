package com.yingwumeijia.android.ywmj.client.function.bill.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.entity.bean.BillIPaymentBean
import com.yingwumeijia.baseywmj.function.extra.CreateBillServiceActivity
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import kotlinx.android.synthetic.main.bill_list_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*
import java.lang.Long
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by jamisonline on 2017/7/27.
 */
class BillPaymentListActivity : JBaseActivity() {

    val paymentAdapter by lazy { createAdapter() }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, BillPaymentListActivity::class.java)
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

        loadData()
    }

    fun loadData() {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getBillPaymentList(), object : ProgressSubscriber<List<BillIPaymentBean>>(context) {
            override fun _onNext(t: List<BillIPaymentBean>?) {
                showEmptyLayout(ListUtil.isEmpty(t))
                if (!ListUtil.isEmpty(t))
                    paymentAdapter.refresh(t!! as ArrayList<BillIPaymentBean>)
            }

        })
    }


    private fun createAdapter(): CommonRecyclerAdapter<BillIPaymentBean> {
        return object : CommonRecyclerAdapter<BillIPaymentBean>(context, null, null, R.layout.item_bill_payment) {
            override fun convert(holder: RecyclerViewHolder, billItemBean: BillIPaymentBean, position: Int) {

                JImageLolder.loadPortrait256(context, holder.getViewWith(R.id.iv_companLogo) as ImageView, billItemBean.logo)

                holder.run {
                    setVisible(R.id.line, position != 0)
                    setTextWith(R.id.tv_year, fromartDateY(billItemBean.createTime))
                    setTextWith(R.id.tv_date, fromartDateMd(billItemBean.createTime))
                    setTextWith(R.id.tv_price, billItemBean.amount.toString())
                    setTextWith(R.id.tv_type, billItemBean.billContentTypeStr)
                    setOnItemClickListener(object : RecyclerViewHolder.OnItemCliceListener {
                        override fun itemClick(itemView: View, position: Int) {
//                              CreateBillServiceActivity.start(context, billItemBean.billId.toString(), null, null)
                            val starter = Intent(context, CreateBillServiceActivity::class.java)
                            starter.putExtra("BILL_ID", billItemBean.billId.toString())
                            starter.putExtra("SESSION_ID", "")
                            starter.putExtra("EXTRA", "")
                            context.startActivity(starter)
                        }
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