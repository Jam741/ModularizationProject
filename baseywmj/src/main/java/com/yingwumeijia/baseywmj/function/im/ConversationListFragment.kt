package com.yingwumeijia.baseywmj.function.im

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yingwumeijia.baseywmj.R

/**
 * Created by jamisonline on 2017/5/31.
 */
class ConversationListFragment : JBaseConversationListFragment() {


    companion object {
        fun newInstance(): ConversationListFragment {
            return ConversationListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.conversation_list, container, false)
    }


}