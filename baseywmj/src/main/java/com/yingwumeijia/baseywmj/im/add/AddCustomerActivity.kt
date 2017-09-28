package com.yingwumeijia.baseywmj.im.add

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.AddSessionMember
import com.yingwumeijia.baseywmj.entity.bean.MemberBean
import com.yingwumeijia.baseywmj.im.IMManager
import com.yingwumeijia.baseywmj.utils.VerifyUtils
import kotlinx.android.synthetic.main.add_member_header.*

/**
 * Created by jamisonline on 2017/7/24.
 */
class AddCustomerActivity : JBaseActivity(), AddMemberContract.View {

    val sessionId by lazy { intent.getStringExtra(Constant.KEY_SESSION_ID) }

    val memberListFragment by lazy { MemberListFragment.newInstance(MemberListFragment.SEARCH_TYPE.CUSTOM) }

    val presenter by lazy { AddMemberPresenter(context, this, IMManager.currentSessionId(context)) }

    companion object {
        fun start(activity: Activity, sessionId: String, request_code: Int) {
            val starter = Intent(activity, AddCustomerActivity::class.java)
            starter.putExtra(Constant.KEY_SESSION_ID, sessionId)
            ActivityCompat.startActivityForResult(activity, starter, request_code, Bundle.EMPTY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_member_c_act)
        topTitle.text = "添加业主"
        ed_key_words.hint = "请输入业主手机号"
        btn_cancel.setOnClickListener { close() }
        btn_confirm.setOnClickListener {
            if (memberListFragment.memberListAdapter.selectedMembers.size == 0) toastWith("未选中")
            else presenter.addMemberToSession(AddSessionMember(memberListFragment.memberListAdapter.selectedMembers, IMManager.currentSessionId(context)))
        }
        ed_key_words.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_clear_edit.visibility = if (TextUtils.isEmpty(keyWordsValue().trim())) View.GONE else View.VISIBLE;
            }
        })
        ed_key_words.setOnEditorActionListener { v, actionId, event ->
            val inputMethodManager = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (inputMethodManager.isActive) hideSoftInput(v)
            if (verifyKeyWords()) {
                presenter.searchCustomer(keyWordsValue())
            }
            return@setOnEditorActionListener false
        }
        btn_clear_edit.setOnClickListener { ed_key_words.setText("") }
        if (supportFragmentManager.findFragmentById(R.id.contentFragment) == null)
            supportFragmentManager.beginTransaction().add(R.id.contentFragment, memberListFragment).commit()

    }


    private fun verifyKeyWords(): Boolean {

        if (!VerifyUtils.verifyMobilePhoneNumber(keyWordsValue())) {
            toastWith("请输入正确的电话号码")
            return false
        }

        return true
    }


    fun keyWordsValue(): String {
        return ed_key_words.text.toString()
    }

    override fun showMemberList(memberBeanList: List<MemberBean>) {
        memberListFragment.showMemberList(memberBeanList)
    }

    override fun showEmptyLayout(isEmpty: Boolean) {
        memberListFragment.showEmptyLayout(isEmpty)
    }

    override fun insertMember(memberBean: MemberBean) {
        memberListFragment.insertMember(memberBean!!)
    }

    override fun showAddSuccess() {
        toastWith("添加成功")
        val intent = Intent()
        context.setResult(Activity.RESULT_OK, intent)
        ActivityCompat.finishAfterTransition(context)
    }


}