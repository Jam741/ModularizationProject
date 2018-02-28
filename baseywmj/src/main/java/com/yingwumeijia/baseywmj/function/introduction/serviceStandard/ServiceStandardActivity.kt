package com.yingwumeijia.baseywmj.function.introduction.serviceStandard

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import com.yingwumeijia.commonlibrary.widget.SpannableTextView
import kotlinx.android.synthetic.main.service_standard_act.*
import kotlinx.android.synthetic.main.service_standard_employee.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/6/29.
 */
class ServiceStandardActivity : JBaseActivity(), ServiceStandardContract.View {


    private val serviceType: Int by lazy { intent.getIntExtra(Constant.KEY_SERVICE_TYPE_ID, Constant.DEFAULT_INT_VALUE) }
    private val serviceTypeStr: String by lazy { intent.getStringExtra(Constant.KEY_SERVICE_TYPE_STR) }
    private val id: Int by lazy { intent.getIntExtra(Constant.KEY_ID, Constant.DEFAULT_INT_VALUE) }
    private val sourceType: Int by lazy { intent.getIntExtra(Constant.KEY_SOURCE_TYPE, Constant.DEFAULT_INT_VALUE) }

    private val presenter by lazy { ServiceStandardPresenter(context, this) }

    companion object {

        /**
         * @param context
         * @param serviceTypeStr
         * @param serviceType
         * @param id             公司id或用户ID
         * @param sourceType     id类型（1：公司id，2：业者用户id）
         */
        fun start(context: Context, serviceTypeStr: String, serviceType: Int, id: Int, sourceType: Int) {
            val starter = Intent(context, ServiceStandardActivity::class.java)
            starter.putExtra(Constant.KEY_SERVICE_TYPE_STR, serviceTypeStr)
            starter.putExtra(Constant.KEY_SERVICE_TYPE_ID, serviceType)
            starter.putExtra(Constant.KEY_ID, id)
            starter.putExtra(Constant.KEY_SOURCE_TYPE, sourceType)
            context.startActivity(starter)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.service_standard_act)
        topTitle.text = serviceTypeStr
        topLeft.setOnClickListener { close() }
        presenter.loadProcedureData(id, sourceType, serviceType)
    }


    override fun showItems(items: List<ServiceStandardBean.ServiceBean.ItemsBean>) {
        rv_serivce.run {
            layoutManager = LinearLayoutManager(context)
            adapter = presenter.itemAdapter
        }
    }

    override fun showEmployee(employeeVoBean: ServiceStandardBean.EmployeeVoBean) {
        JImageLolder.loadPortrait256(context, iv_portrait, employeeVoBean.headImage)
        createSpannableTextViewForReplay(tv_employeeInfo, employeeVoBean.name, "  " + employeeVoBean.title)
    }


    fun createSpannableTextViewForReplay(tv: SpannableTextView, title: String, content: String) {

        // clear pieces
        tv.reset()
        // Add the first piece
        tv.addPiece(SpannableTextView.Piece.Builder(title).textColor(Color.WHITE)
                .style(Typeface.BOLD).textSize(resources.getDimension(R.dimen.font1_sp).toInt()).build())

        // Add the second piece
        tv.addPiece(SpannableTextView.Piece.Builder(content).textColor(resources.getColor(R.color.color_5))
                .style(Typeface.NORMAL).textSize(resources.getDimension(R.dimen.font6_sp).toInt()).build())

        // Display the final, styled text
        tv.display()
    }
}