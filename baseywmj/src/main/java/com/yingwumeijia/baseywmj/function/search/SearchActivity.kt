package com.yingwumeijia.baseywmj.function.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.TextView
import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.entity.bean.CaseTypeEnum
import com.yingwumeijia.baseywmj.function.caselist.CaseListFragment
import com.yingwumeijia.baseywmj.function.main.MainController
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.case_list_option_title.*
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.search_assist_layout.*
import kotlinx.android.synthetic.main.search_edit_layout.*
import kotlinx.android.synthetic.main.search_layout.*

/**
 * Created by jamisonline on 2017/6/20.
 */

class SearchActivity : JBaseActivity(), AdapterView.OnItemClickListener, SearchController.OnLoadHistoryAndHotKeyWordsListener, TagFlowLayout.OnTagClickListener {


    companion object {
        fun start(activity: Activity) {
            val starter = Intent(activity, SearchActivity::class.java)
            activity.startActivity(starter)
        }

    }


    var drawableIndex: Int = 0


    val controller: SearchController by lazy {
        SearchController(context, lifecycleSubject, this@SearchActivity)
    }

    val caseListFragment by lazy {
        CaseListFragment.newInstance(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_layout)

        controller.loadHotKeyWords()
        controller.loadHistoryKeyWords()

        controller.didCaseFilterSet()

        if (supportFragmentManager.findFragmentById(R.id.content) == null)
            supportFragmentManager.beginTransaction().add(R.id.content, caseListFragment).commit()

        drawer_root.run {
            setStatusBarBackground(R.color.colorPrimaryDark)
            setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }

        lv_nav.setOnItemClickListener(this)

        exlv_nav.run {
            setOnGroupClickListener { parent, v, groupPosition, id -> false }
            setOnChildClickListener { parent, v, groupPosition, childPosition, id -> exNavChildItemClick(groupPosition, childPosition) }
        }

        val lp = right_drawer.getLayoutParams()
        lp.width = ScreenUtils.screenWidth * 8 / 12
        right_drawer.layoutParams = lp


        //搜索相关控件
        btn_cancel.setOnClickListener { close() }
        flow_hot.setOnTagClickListener(this)
        flow_history.setOnTagClickListener(this)

        ed_key_words.addTextChangedListener(searchTextWatch)
        ed_key_words.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            //当actionId == XX_SEND 或者 XX_DONE时都触发
            //或者event.getKeyCode == ENTER 且 event.getAction == ACTION_DOWN时也触发
            //注意，这是一定要判断event != null。因为在某些输入法上会返回null。
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE || event != null && KeyEvent.KEYCODE_ENTER == event.keyCode && KeyEvent.ACTION_DOWN == event.action) {
                //处理事件
                val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (imm.isActive) {
                    imm.hideSoftInputFromWindow(v.applicationWindowToken, 0)
                }
                var mKeyWords = ed_key_words.text.toString()
                if (TextUtils.isEmpty(mKeyWords)) {

                } else {
                    controller.insertHistoryKeyWords(mKeyWords)
                    //                        navlayout.setVisibility(View.VISIBLE);
                    if (search_assist_layout.getVisibility() == View.VISIBLE) {
                        search_assist_layout.setVisibility(View.GONE)
                        controller.loadHistoryKeyWords()
                    }
                    Logger.d("search")
                    search_assist_layout.visibility = View.GONE
                    caseListFragment.search(mKeyWords!!)
                }
            }
            false
        })

        btn_clear_edit.setOnClickListener { ed_key_words.setText("") }

        flow_history.adapter = controller.historyKeyWordsAdapter
        flow_hot.adapter = controller.hotKeyWordsAdapter

    }


    internal var searchTextWatch: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            if (search_assist_layout.visibility == View.GONE) {
                search_assist_layout.visibility = View.VISIBLE
            }
            if (ed_key_words.getText().toString().length > 0)
                btn_clear_edit.visibility = View.VISIBLE
            else
                btn_clear_edit.visibility = View.GONE
        }

        override fun afterTextChanged(editable: Editable) {

        }
    }

    fun exNavChildItemClick(groupPosition: Int, childPosition: Int): Boolean {
        controller.classfilyAdapter!!.setSelected(groupPosition, childPosition)
        closeDrawableLayout(controller.classfilyAdapter!!.selectorId)
        return true
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var caseTypeEnum: CaseTypeEnum? = null
        when (drawableIndex) {
            1 -> {
                if (controller.styleAdapter != null) {
                    controller.styleAdapter!!.setSelected(position)
                    caseTypeEnum = controller.styleAdapter!!.getItem(position)
                }
            }
            2 -> {
                if (controller.hoseTypeAdapter != null) {
                    controller.hoseTypeAdapter!!.setSelected(position)
                    caseTypeEnum = controller.hoseTypeAdapter!!.getItem(position)
                }
            }
            3 -> {
                if (controller.hoseAreaAdapter != null) {
                    controller.hoseAreaAdapter!!.setSelected(position)
                    caseTypeEnum = controller.hoseAreaAdapter!!.getItem(position)
                }
            }
            4 -> {
                if (controller.cityAdapter != null) {
                    controller.cityAdapter!!.setSelected(position)
                    caseTypeEnum = controller.cityAdapter!!.getItem(position)
                }
            }
        }

        if (caseTypeEnum != null)
            closeDrawableLayout(caseTypeEnum!!)
    }

    /**
     * 关闭抽屉
     */
    private fun closeDrawableLayout(caseTypeEnum: CaseTypeEnum) {
        drawer_root.closeDrawers()
        caseListFragment.onClose(caseTypeEnum)
    }

    /**
     * 显示筛选抽屉
     */
    fun showDrawableLayout(navPosition: Int) {
        drawableIndex = navPosition
        when (navPosition) {
            0 -> {
                tv_sliding_title.text = "分类"
                lv_nav.visibility = View.GONE
                exlv_nav.run {
                    visibility = View.VISIBLE
                    setAdapter(controller.classfilyAdapter)
                }
                for (i in 0..controller.classfilyAdapter!!.getGroupCount() - 1) {
                    exlv_nav.expandGroup(i)
                }
            }
            1 -> {
                tv_sliding_title.text = "风格"
                lv_nav.run {
                    visibility = View.VISIBLE
                    adapter = controller.styleAdapter
                }
                exlv_nav.visibility = View.GONE
            }
            2 -> {
                tv_sliding_title.text = "房型"
                lv_nav.run {
                    visibility = View.VISIBLE
                    adapter = controller.hoseTypeAdapter
                }
                exlv_nav.visibility = View.GONE
            }
            3 -> {
                tv_sliding_title.text = "面积"
                lv_nav.run {
                    visibility = View.VISIBLE
                    adapter = controller.hoseAreaAdapter
                }
                exlv_nav.visibility = View.GONE
            }
            4 -> {
                tv_sliding_title.text = "城市"
                lv_nav.run {
                    visibility = View.VISIBLE
                    adapter = controller.cityAdapter
                }
                exlv_nav.visibility = View.GONE
            }
        }

        drawer_root.openDrawer(Gravity.RIGHT)
    }


    override fun didLoadHistoryKeyWords(data: List<String>?) {
        if (ListUtil.isEmpty(data)) {
            history_layout.visibility = View.GONE
        } else {
            search_assist_layout.visibility = View.VISIBLE
            history_layout.visibility = View.VISIBLE
        }

    }

    override fun didLoadHotKeyWords(data: List<String>?) {
        if (ListUtil.isEmpty(data)) {
            hot_layout.visibility = View.GONE
        } else {
            search_assist_layout.visibility = View.VISIBLE
            hot_layout.visibility = View.VISIBLE
        }
    }

    override fun onTagClick(view: View?, position: Int, parent: FlowLayout?): Boolean {
        when (parent!!.id) {
            R.id.flow_hot ->{
                search_assist_layout.visibility = View.GONE
                caseListFragment.search((controller.hotKeyWordsAdapter.getItem(position) as String))
            }
            R.id.flow_history -> {
                search_assist_layout.visibility = View.GONE
                caseListFragment.search((controller.historyKeyWordsAdapter.getItem(position) as String))
            }
        }
        return true
    }

}
