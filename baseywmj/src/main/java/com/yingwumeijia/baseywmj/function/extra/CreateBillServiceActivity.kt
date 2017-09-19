package com.yingwumeijia.baseywmj.function.extra


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import cn.qqtheme.framework.picker.OptionPicker
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.entity.bean.BillServiceInfo
import com.yingwumeijia.baseywmj.entity.bean.BillSimpleInfo
import com.yingwumeijia.baseywmj.entity.bean.MemberBean
import com.yingwumeijia.baseywmj.entity.bean.PayBillInfo
import com.yingwumeijia.baseywmj.function.cashier.CashierForActiveActivity
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import kotlinx.android.synthetic.main.create_bill_service_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*
import rx.Observable
import rx.schedulers.Schedulers
import java.math.BigDecimal
import java.util.*

/**
 * Created by Jam on 2017/3/21 下午2:15.
 * Describe:
 */
internal enum class BillType {
    pay_payment,
    pay_paid,
    invite_payment,
    invite_payid
}

class CreateBillServiceActivity : JBaseActivity(), CreateBillServicContract.View {

//        @Bind(R.id.topLeft)
//        TextView topLeft;
//        @Bind(R.id.topLeft_second)
//        TextView topLeftSecond;
//        @Bind(R.id.topRight)
//        TextView topRight;
//        @Bind(R.id.topDivider)
//        View topDivider;
//        @Bind(R.id.topTitle)
//        TextView topTitle;
//        @Bind(R.id.toolbar)
//        RelativeLayout toolbar;
//        @Bind(R.id.iv_portrait)
//        CircleImageView ivPortrait;
//        @Bind(R.id.tv_companyName)
//        TextView tvCompanyName;
//        @Bind(R.id.btn_choosePayPurpose)
//        TextView btnChoosePayPurpose;
//        @Bind(R.id.ed_payPrice)
//        EditText edPayPrice;
//        @Bind(R.id.ed_payRemarks)
//        EditText edPayRemarks;
//        @Bind(R.id.btn_payNow)
//        Button btnPayNow;
//        @Bind(R.id.pay_layout)
//        LinearLayout payLayout;
//        @Bind(R.id.tv_PayPurpose)
//        TextView tvPayPurpose;
//        @Bind(R.id.tv_PayPrice)
//        TextView tvPayPrice;
//        @Bind(R.id.tv_PayRemark)
//        TextView tvPayRemark;
//        @Bind(R.id.preview_layout)
//        LinearLayout previewLayout;
//        @Bind(R.id.payStatus)
//        TextView payStatus;
//        @Bind(R.id.btn_payNowForPreview)
//        Button btnPayNowForPreview;

    private var mIntent: Intent? = null
    private var mBillId: String? = null
    private var forCreate = false
    private val mBillType: BillType? = null
    private var mSessionId: String? = null
    private val mCompanyId: Int = 0
    private var payExtra: String? = null
    private var payBillInfo: PayBillInfo? = null//支付账单信息
    private var picker: OptionPicker? = null
    private var billServicesBeanList: List<BillServiceInfo.BillServicesBean>? = null
    private var billServicesStringList: ArrayList<String>? = null
    private var memberBean: MemberBean? = null

    private var mPresenter: CreateBillServicContract.Presenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_bill_service_act)

        topTitle.text = "付款邀请"
        mBillId = intent!!.getStringExtra("BILL_ID")
        if (intent!!.getSerializableExtra("MEMBER_KEY") != null)
            memberBean = intent!!.getSerializableExtra("MEMBER_KEY") as MemberBean
        mSessionId = intent!!.getStringExtra("SESSION_ID")
        forCreate = intent!!.getBooleanExtra("FOR_CREATE", false)
        payExtra = intent!!.getStringExtra("EXTRA")
        mPresenter = CreateBillServicePresenter(this, context)

        topLeft.setOnClickListener { ActivityCompat.finishAfterTransition(context) }

        ed_payPrice.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(edt: Editable) {
                val temp = edt.toString()
                val posDot = temp.indexOf(".")
                if (posDot <= 0) return
                if (temp.length - posDot - 1 > 2) {
                    edt.delete(posDot + 3, posDot + 4)
                }
            }

            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}

            override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
        })


        /**
         * 付款
         */
        btn_payNow.setOnClickListener {
            if (payBillInfo == null) {
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(ed_payPrice.getText().toString().trim())) {
                showToastMsg("请填写付款金额")
                return@setOnClickListener

            }


            val amount = BigDecimal(ed_payPrice.getText().toString())
            if (amount.compareTo(BigDecimal("0.01")) == -1) {
                showToastMsg("输入金额不小于0.01元")
                return@setOnClickListener

            }

            if (amount.compareTo(BigDecimal("10000")) == 1) {
                showToastMsg("输入金额不大于10000元")
                return@setOnClickListener

            }


            payBillInfo!!.amount = amount

            if (!TextUtils.isEmpty(ed_payRemarks.getText().toString().trim())) {
                payBillInfo!!.postscript = ed_payRemarks.getText().toString()
            }

            mPresenter!!.createPayBill(payBillInfo)
        }


        btn_choosePayPurpose.setOnClickListener {
            if (!ListUtil.isEmpty(billServicesStringList))
                showChoosePick(billServicesStringList!!)

        }
    }

    public override fun onResume() {
        super.onResume()
        if (forCreate) {
            mPresenter!!.getBillService(mSessionId)
        } else {
            mPresenter!!.checkBillSimple(mBillId)
        }
    }


    private fun showChoosePick(strings: ArrayList<String>) {
        if (picker == null) {
            picker = OptionPicker(context, strings)
            picker!!.setCancelTextColor(resources.getColor(R.color.color_6))
            picker!!.setCancelText(R.string.btn_cancel)
            picker!!.setCancelTextSize(16)
            picker!!.setSubmitTextColor(resources.getColor(R.color.color_6))
            picker!!.setSubmitText(R.string.btn_confirm)
            picker!!.setSubmitTextSize(16)
            picker!!.setTextColor(resources.getColor(R.color.color_6), resources.getColor(R.color.color_5))
            picker!!.setCycleDisable(true)
            picker!!.setHeight(ScreenUtils.screenWidth / 3)
            picker!!.setLineColor(resources.getColor(R.color.color_3))
            picker!!.selectedIndex = 0
            picker!!.setOnOptionPickListener(object : OptionPicker.OnOptionPickListener() {
                override fun onOptionPicked(position: Int, option: String) {
                    //                mHouseBean.setHouseCondition(houseChooseDto.getHouseConditions().get(position).getCode());
                    btn_choosePayPurpose.setText(option)
                    payBillInfo!!.serviceContentType = billServicesBeanList!![position].code
                }
            })
        }
        picker!!.show()
    }


    fun setPresener(presenter: CreateBillServicContract.Presenter) {
        mPresenter = presenter
    }


    fun showToastMsg(msg: String) {
        toastWith(msg)
    }


    override fun initCreateService(billServiceInfo: BillServiceInfo) {
        pay_layout.visibility = View.VISIBLE
        billServicesBeanList = billServiceInfo.billServices
        if (!ListUtil.isEmpty(billServicesBeanList)) {
            Observable.from<BillServiceInfo.BillServicesBean>(billServicesBeanList!!)
                    .map({ billServicesBean -> billServicesBean.desc })
                    .subscribeOn(Schedulers.io())
                    .subscribe { s ->
                        if (billServicesStringList == null)
                            billServicesStringList = ArrayList<String>()
                        billServicesStringList!!.add(s)
                    }
        }

        showCompanyName(memberBean!!.showName)
        showCompanyPortrait(memberBean!!.showHead)

        payBillInfo = PayBillInfo()
        payBillInfo!!.userId = memberBean!!.userId
        payBillInfo!!.sessionId = Integer.valueOf(mSessionId)!!
        payBillInfo!!.serviceContentType = billServiceInfo.billServices[0].code
        btn_choosePayPurpose.setText(billServiceInfo.billServices[0].desc)

    }

    override fun showCompanyName(companyName: String) {
        if (!TextUtils.isEmpty(companyName))
            tv_companyName.setText(companyName)
    }

    override fun showCompanyPortrait(portrait: String) {
        if (!TextUtils.isEmpty(portrait))
            JImageLolder.loadPortrait256(context, iv_portrait, portrait)
    }

    override fun toCashier(bill: String) {
//        val payMessageContent = PayMessageContent.obtain("付款邀请", bill, "1")
//        val myMessage = Message.obtain(mSessionId, Conversation.ConversationType.GROUP, payMessageContent)
//        RongIM.getInstance().sendMessage(myMessage, "邀请付款消息", "邀请付款消息", object : IRongCallback.ISendMediaMessageCallback {
//            override fun onProgress(message: Message, i: Int) {
//
//            }
//
//            override fun onCanceled(message: Message) {
//
//            }
//
//            override fun onAttached(message: Message) {
//
//            }
//
//            override fun onSuccess(message: Message) {
//                ActivityCompat.finishAfterTransition(context)
//            }
//
//            override fun onError(message: Message, errorCode: RongIMClient.ErrorCode) {
//                ActivityCompat.finishAfterTransition(context)
//            }
//        })
    }

    override fun initBillSimple(billSimpleInfo: BillSimpleInfo) {
        preview_layout.setVisibility(View.VISIBLE)
        showCompanyName(billSimpleInfo.companyName)
        showCompanyPortrait(billSimpleInfo.getLogo())


        tv_PayPurpose.setText(billSimpleInfo.getServiceContentStr())
        tv_PayPrice.setText(billSimpleInfo.getAmount().toString())
        tv_PayRemark.setText(billSimpleInfo.getPostscript())

        val whetherPayed = billSimpleInfo.isWhetherPayed()//是否已支付，true表示已支付，false表示未支

        if (billSimpleInfo.getBillDetailType() === 2)
            topTitle.setText("付款邀请")
        else
            topTitle.setText("支付款项")
        payStatus.setVisibility(View.VISIBLE)
        btn_payNowForPreview.setVisibility(View.GONE)
        if (whetherPayed) {
            payStatus.setText("款项已支付")
        } else {

            if (AppTypeManager.isAppC()) {
                btn_payNowForPreview.text = "立即支付"
                btn_payNowForPreview.visibility = View.VISIBLE
                btn_payNowForPreview.setOnClickListener { CashierForActiveActivity.start(context, mBillId!!) }
                payStatus.visibility = View.GONE

            } else {
                payStatus.setText("等待业主支付")

            }

        }


    }

    companion object {

        fun start(context: Activity, memberBean: MemberBean, sessionId: String, forCreate: Boolean) {
            val starter = Intent(context, CreateBillServiceActivity::class.java)
            starter.putExtra("MEMBER_KEY", memberBean)
            starter.putExtra("SESSION_ID", sessionId)
            starter.putExtra("FOR_CREATE", forCreate)
            context.startActivity(starter)
        }

        fun start(context: Context, billId: String, sessionId: String, extra: String) {
            val starter = Intent(context, CreateBillServiceActivity::class.java)
            starter.putExtra("BILL_ID", billId)
            starter.putExtra("SESSION_ID", sessionId)
            starter.putExtra("EXTRA", extra)
            context.startActivity(starter)
        }
    }

}
