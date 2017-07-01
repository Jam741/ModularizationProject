package com.yingwumeijia.baseywmj.function.search

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.function.main.MainController
import com.yingwumeijia.commonlibrary.base.ActivityLifeCycleEvent
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import rx.Observable
import rx.functions.Action1
import rx.subjects.PublishSubject

/**
 * Created by jamisonline on 2017/6/23.
 */
class SearchController(val activity: Activity, publishSubject: PublishSubject<ActivityLifeCycleEvent>, var loadHistoryAndHotKeyWordsListener: OnLoadHistoryAndHotKeyWordsListener) : MainController(activity, publishSubject) {


    val hotKeyWordsAdapter by lazy {
        createKeyWordsAdapter()
    }


    val historyKeyWordsAdapter by lazy {
        createKeyWordsAdapter()
    }

    fun start() {
        loadHistoryKeyWords()
        loadHotKeyWords()
    }

    fun loadHistoryKeyWords() {
        val ob: Observable<List<String>> = Observable
                .create(Observable.OnSubscribe {
                    SearchHistoryMenager.getHistory(context)
                })
        ob.compose(HttpUtil.applySchedulers()).subscribe(object : Action1<List<String>> {
            override fun call(t: List<String>?) {
                if (!ListUtil.isEmpty(t))
                    historyKeyWordsAdapter.addRange(t!!)
                loadHistoryAndHotKeyWordsListener.didLoadHistoryKeyWords(t)
            }
        })
    }


    fun loadHotKeyWords() {
        val ob = Api.service.getHotKeys()
        HttpUtil.getInstance().toSimpleSubscribe(ob, object : ProgressSubscriber<List<String>>(context) {
            override fun _onNext(t: List<String>?) {
                if (!ListUtil.isEmpty(t))
                    hotKeyWordsAdapter.addRange(t!!)
                loadHistoryAndHotKeyWordsListener.didLoadHotKeyWords(t)
            }
        }, publishSubject, true)
    }

    fun insertHistoryKeyWords(keyWords: String) {
        SearchHistoryMenager.insertHistory(context, keyWords)
        historyKeyWordsAdapter.insert(keyWords)
    }

    private fun createKeyWordsAdapter(): TagAdapter<String> {
        return object : TagAdapter<String>() {
            override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                val mInflater = LayoutInflater.from(context)
                val textView = mInflater.inflate(R.layout.tv_tag, parent, false) as TextView
                textView.setText(t)
                return textView
            }
        }
    }


    interface OnLoadHistoryAndHotKeyWordsListener {

        fun didLoadHistoryKeyWords(data: List<String>?)

        fun didLoadHotKeyWords(data: List<String>?)

    }

}