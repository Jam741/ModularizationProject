package com.yingwumeijia.baseywmj.im.add

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.view.ViewPager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.AddSessionMember
import com.yingwumeijia.baseywmj.entity.bean.MemberBean
import com.yingwumeijia.baseywmj.function.adapter.TabWithPagerAdapter
import kotlinx.android.synthetic.main.add_member_e_act.*
import kotlinx.android.synthetic.main.add_member_header.*
import java.util.*

/**
 * Created by jamisonline on 2017/7/24.
 */
class AddEmployeeActivity : JBaseActivity(), AddMemberContract.View {

    val sessionId by lazy { intent.getStringExtra(Constant.KEY_SESSION_ID) }

    val mTitles by lazy { arrayOf("业者", "业者所在公司") }

    val memberListFragments by lazy { arrayListOf(MemberListFragment.newInstance(MemberListFragment.SEARCH_TYPE.EMPLOYEE), MemberListFragment.newInstance(MemberListFragment.SEARCH_TYPE.COMPANY)) }

    val presenter by lazy { AddMemberPresenter(context, this, sessionId) }

    var searchType = MemberListFragment.SEARCH_TYPE.EMPLOYEE

    lateinit var currentFragment: MemberListFragment

//
//    /**
//     * 被观察者 （SearchType）
//     */
//    class SearchTypeManager : Observable() {
//        var searchType = MemberListFragment.SEARCH_TYPE.EMPLOYEE
//        fun changeSearchType(searchType: MemberListFragment.SEARCH_TYPE) {
//            if (this@SearchTypeManager.searchType != searchType) {
//                this@SearchTypeManager.searchType = searchType
//                setChanged()
//            }
//            notifyObservers()
//        }
//    }
//
//
//    /**
//     * 观察者 （SearchType）
//     */
//    class SearchTypObserver(searchTypeManager: SearchTypeManager) : Observer {
//
//        init {
//            searchTypeManager.addObserver(this)
//        }
//
//        override fun update(o: Observable?, arg: Any?) {
//
//        }
//    }

    companion object {
        fun start(activity: Activity, sessionId: String, request_code: Int) {
            val starter = Intent(activity, AddEmployeeActivity::class.java)
            starter.putExtra(Constant.KEY_SESSION_ID, sessionId)
            ActivityCompat.startActivityForResult(activity, starter, request_code, Bundle.EMPTY)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_member_e_act)
        topTitle.text = "添加业者"


        btn_cancel.setOnClickListener { close() }
        btn_clear_edit.setOnClickListener { ed_key_words.setText("") }
        btn_confirm.setOnClickListener {
            if (currentFragment.memberListAdapter.selectedMembers.size == 0) toastWith("未选中")
            else presenter.addMemberToSession(AddSessionMember(currentFragment.memberListAdapter.selectedMembers, sessionId))
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
                when (searchType) {
                    MemberListFragment.SEARCH_TYPE.EMPLOYEE -> presenter.searchEmployeeWithName(keyWordsValue().trim())
                    MemberListFragment.SEARCH_TYPE.COMPANY -> presenter.searchEmployeeWithCompany(keyWordsValue().trim())
                }
            }
            return@setOnEditorActionListener false
        }

        currentFragment = memberListFragments[0]

        vp_content.run {
            adapter = TabWithPagerAdapter(supportFragmentManager, mTitles, memberListFragments)
            offscreenPageLimit = 2
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    searchType = if (position == 0) MemberListFragment.SEARCH_TYPE.EMPLOYEE else MemberListFragment.SEARCH_TYPE.COMPANY
                    setViewWith(searchType)
                    currentFragment = memberListFragments[position]
                }

                override fun onPageSelected(position: Int) {
                }
            })
        }
        tl_eTitle.setViewPager(vp_content)

        if (!AppTypeManager.isAppC()){
            employeeLayout.visibility = View.GONE
        }

    }

    private fun verifyKeyWords(): Boolean {

        if (TextUtils.isEmpty(keyWordsValue())) {
            toastWith("至少输入两个字符")
            return false
        }

        if (keyWordsValue().trim().length < 2) {
            toastWith("至少输入两个字符")
            return false
        }

        return true
    }


    fun setViewWith(searchType: MemberListFragment.SEARCH_TYPE) {
        when (searchType) {
            MemberListFragment.SEARCH_TYPE.EMPLOYEE -> {
                ed_key_words.hint = "请输入业者全名进行搜索"
            }
            MemberListFragment.SEARCH_TYPE.COMPANY -> {
                ed_key_words.hint = "请输入业者所在公司名称进行搜索"
            }
        }
    }

    fun keyWordsValue(): String {
        return ed_key_words.text.toString()
    }

    override fun showMemberList(memberBeanList: List<MemberBean>) {
        currentFragment.showMemberList(memberBeanList)
    }

    override fun showEmptyLayout(isEmpty: Boolean) {
        currentFragment.showEmptyLayout(isEmpty)
    }

    override fun insertMember(memberBean: MemberBean) {
        currentFragment.insertMember(memberBean!!)
    }

    override fun showAddSuccess() {
        toastWith("添加成功")
        val intent = Intent()
        context.setResult(Activity.RESULT_OK, intent)
        ActivityCompat.finishAfterTransition(context)
    }

}