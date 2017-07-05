package com.yingwumeijia.android.ywmj.client.function.enter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder
import kotlinx.android.synthetic.main.enter_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/7/5.
 */
class EnterActivity : JBaseActivity() {

    internal var stepData = arrayOf(arrayOf("填写公司信息", "填写公司以及联系人基本信息"), arrayOf("商务经理沟通联系", "城市商务经理会在申请提交后两个工作日内联系您，与您沟通合作细节"), arrayOf("签约入驻", "签约入驻鹦鹉美家平台，获取优质客户"))

    val stepBeanList by lazy { stepData.indices.map { StepBean(stepData[it][0], stepData[it][1]) } }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, EnterActivity::class.java)
            context.startActivity(starter)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.enter_act)
        topTitle.text = "申请入驻"

        rv_step.run {
            layoutManager = LinearLayoutManager(context)
            adapter = creataStepAdapter(stepBeanList)
        }

        topLeft.setOnClickListener { close() }
        btn_edit.setOnClickListener {  EditEnterActivity.start(context)}
    }


    private fun creataStepAdapter(stepBeanList: List<StepBean>): CommonRecyclerAdapter<StepBean> {
        return object : CommonRecyclerAdapter<StepBean>(context, null, stepBeanList as ArrayList<StepBean>, R.layout.item_enter_step) {
            override fun convert(holder: RecyclerViewHolder, stepBean: StepBean, position: Int) {
                holder.run {
                    setTextWith(R.id.tv_step, (position + 1).toString() + "")
                    setTextWith(R.id.tv_1, stepBean.step1)
                    setTextWith(R.id.tv_2, stepBean.step2)
                }
            }
        }
    }

    inner class StepBean(var step1: String, var step2: String)
}