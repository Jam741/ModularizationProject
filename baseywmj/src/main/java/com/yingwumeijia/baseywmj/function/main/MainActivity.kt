package com.yingwumeijia.baseywmj.function.main

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.Toast
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.Observer
import com.netease.nimlib.sdk.RequestCallbackWrapper
import com.netease.nimlib.sdk.msg.MsgService
import com.netease.nimlib.sdk.msg.MsgServiceObserve
import com.netease.nimlib.sdk.msg.model.IMMessage
import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.entity.TabEntity
import com.yingwumeijia.baseywmj.entity.bean.CaseTypeEnum
import com.yingwumeijia.baseywmj.function.active.ActiveFragment
import com.yingwumeijia.baseywmj.function.caselist.CaseListFragment
import com.yingwumeijia.baseywmj.function.im.ConversationListFragment
import com.yingwumeijia.baseywmj.function.personal.PersonalFragment
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.commonlibrary.utils.SPUtils
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import kotlinx.android.synthetic.main.case_list_option_title.*
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.main_act.*
import kotlinx.android.synthetic.main.main_page.*
import rx.Subscriber
import java.util.*
import kotlin.collections.ArrayList
import com.netease.nimlib.sdk.msg.model.RecentContact
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.message.MessageActivity
import com.yingwumeijia.baseywmj.function.message.MessageBean
import com.yingwumeijia.commonlibrary.utils.ListUtil


abstract class MainActivity : JBaseActivity(), OnTabSelectListener, ViewPager.OnPageChangeListener, AdapterView.OnItemClickListener {


    companion object {
        fun start(context: Activity) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }

    /**
     * MainViewPage
     */
    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        tl_main.currentTab = position
    }

    /**
     * TabLayout
     */
    override fun onTabSelect(position: Int) {
        if (position == 1) {
            tl_main.hideMsg(1)
        }
        viewpager.setCurrentItem(position, false)
        currentFragment = mFragments[position]
    }

    override fun onTabReselect(position: Int) {
    }

    val controller: MainController by lazy {
        MainController(this, lifecycleSubject)
    }

    var drawableIndex: Int = 0

    val mTitles by lazy { getTitles() }

    val mIconUnselectIds by lazy { getIconUnselectIds() }

    val mIconSelectIds by lazy { getIconSelectIds() }

    val mTabEntities by lazy { initTabEntities() }

    val mFragments by lazy { getFragments() }

    lateinit var currentFragment: Fragment

    abstract fun getFragments(): ArrayList<JBaseFragment>

    abstract fun getTitles(): Array<String>

    abstract fun getIconUnselectIds(): IntArray

    abstract fun getIconSelectIds(): IntArray

    var mPageAdapter = MainPageAdapter(mFragments, supportFragmentManager)


    val msgReceivedBroadCast = MsgReceivedBroadCast()

//    val conversationTypes = arrayOf(Conversation.ConversationType.GROUP)

    override fun onResume() {
        super.onResume()
        hideSoftInput(tl_main)

        if (UserManager.isLogin(context)) {
            setUnReadCount()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_act)
        Logger.d("Main", "MainActivity onCreated")

        registerMsgReceivedBroadCast()

        controller.didCaseFilterSet()

        viewpager.run {
            offscreenPageLimit = 4
            adapter = mPageAdapter
            addOnPageChangeListener(this@MainActivity)
        }

        currentFragment = mFragments[0]

        tl_main.run {
            setTabData(mTabEntities)
            setOnTabSelectListener(this@MainActivity)
        }

        drawer_root.run {
            setStatusBarBackground(R.color.colorPrimaryDark)
            setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }

        lv_nav.onItemClickListener = this@MainActivity

        exlv_nav.run {
            setOnGroupClickListener { parent, v, groupPosition, id -> false }
            setOnChildClickListener { parent, v, groupPosition, childPosition, id -> exNavChildItemClick(groupPosition, childPosition) }
        }


        val lp = right_drawer.layoutParams
        lp.width = ScreenUtils.screenWidth * 8 / 12
        right_drawer.layoutParams = lp


        getLastActivity()


    }

    override fun onDestroy() {
        unRegisterMsgReceivedBroadCast()
        super.onDestroy()
    }

    inner class MsgReceivedBroadCast : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("JAM", "MsgReceivedBroadCast")
            toastWith("MsgReceivedBroadCast")
            setUnReadCount()
        }

    }

    fun registerMsgReceivedBroadCast() {
        val myIntentFilter = IntentFilter()
        myIntentFilter.addAction(if (AppTypeManager.isAppC()) Constant.MSG_RECEIVE_ACTION_C else Constant.MSG_RECEIVE_ACTION_E)
        registerReceiver(msgReceivedBroadCast, myIntentFilter)

    }

    fun unRegisterMsgReceivedBroadCast() {
        unregisterReceiver(msgReceivedBroadCast)
    }


    fun setUnReadCount() {


        val recents = NIMClient.getService(MsgService::class.java).queryRecentContactsBlock()


        var unreadNum = 0
        if (!ListUtil.isEmpty(recents))
            unreadNum = recents!!.sumBy { it.unreadCount }
        if (AppTypeManager.isAppC()) {
            if (tl_main != null) {
                if (unreadNum == 0) {
                    tl_main.hideMsg(3)
                } else {
                    tl_main.showMsg(3, unreadNum)
                    tl_main.setMsgMargin(3, -5f, 5f)
                }

            }
        } else {
            if (tl_main != null) {
                if (unreadNum == 0) {
                    tl_main.hideMsg(2)
                } else {
                    tl_main.showMsg(2, unreadNum)
                    tl_main.setMsgMargin(2, -5f, 5f)
                }

            }
        }


    }


    private fun getLastActivity() {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.activeLast(), object : Subscriber<Int>() {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable?) {
            }

            override fun onNext(t: Int?) {

                val lastActivityId = SPUtils.get(context, "KEY_LAST_ACTIVITY", 0) as Int
                SPUtils.put(context, "KEY_LAST_ACTIVITY", t)
                if (lastActivityId == t) {
                    tl_main.hideMsg(1)
                } else {
                    tl_main.showMsg(1, 0)
                    tl_main.setMsgMargin(1, -5f, 5f)
                }

            }
        })

    }


    private fun initTabEntities(): ArrayList<CustomTabEntity> {
        val data = ArrayList<CustomTabEntity>()
        for (i in mTitles.indices) {
            data.add(i, TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
        }
        return data
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
        (mPageAdapter.getItem(0) as CaseListFragment).onClose(caseTypeEnum)
    }

    /**
     * 显示筛选抽屉
     */
    fun showDrawableLayout(navPosition: Int) {
        drawableIndex = navPosition
        when (navPosition) {
            0 -> {
                tv_sliding_title.text = "分类"
                lv_nav.visibility = GONE
                exlv_nav.run {
                    visibility = VISIBLE
                    setAdapter(controller.classfilyAdapter)
                }
                for (i in 0..controller.classfilyAdapter!!.getGroupCount() - 1) {
                    exlv_nav.expandGroup(i)
                }
            }
            1 -> {
                tv_sliding_title.text = "风格"
                lv_nav.run {
                    visibility = VISIBLE
                    adapter = controller.styleAdapter
                }
                exlv_nav.visibility = GONE
            }
            2 -> {
                tv_sliding_title.text = "房型"
                lv_nav.run {
                    visibility = VISIBLE
                    adapter = controller.hoseTypeAdapter
                }
                exlv_nav.visibility = GONE
            }
            3 -> {
                tv_sliding_title.text = "面积"
                lv_nav.run {
                    visibility = VISIBLE
                    adapter = controller.hoseAreaAdapter
                }
                exlv_nav.visibility = GONE
            }
            4 -> {
                tv_sliding_title.text = "城市"
                lv_nav.run {
                    visibility = VISIBLE
                    adapter = controller.cityAdapter
                }
                exlv_nav.visibility = GONE
            }
        }

        drawer_root.openDrawer(Gravity.RIGHT)
    }

    /**
     * 双击退出函数
     */
    private var isExit: Boolean = false

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click()
        }
        return false
    }

    private fun exitBy2Click() {
        val tExit: Timer
        if (!isExit) {
            isExit = true // 准备退出
            toastWith("再按一次退出程序")
            tExit = Timer()
            tExit.schedule(object : TimerTask() {
                override fun run() {
                    isExit = false // 取消退出
                }
            }, 2000) // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (currentFragment != null) currentFragment.onActivityResult(requestCode, resultCode, data)
    }

}
