package com.yingwumeijia.baseywmj.im.add

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.bean.MemberBean
import com.yingwumeijia.commonlibrary.utils.ListUtil
import kotlinx.android.synthetic.main.search_member_frag.*

/**
 * Created by jamisonline on 2017/7/24.
 */
class MemberListFragment : JBaseFragment() {

    enum class SEARCH_TYPE {
        CUSTOM,
        EMPLOYEE,
        COMPANY
    }

    val searchType by lazy { arguments.getSerializable(Constant.KEY_SEARCH_TYPE) }

    val memberListAdapter by lazy { MemberListAdapter(this) }


    companion object {
        fun newInstance(searchType: SEARCH_TYPE): MemberListFragment {
            val args = Bundle()
            args.putSerializable(Constant.KEY_SEARCH_TYPE, searchType)
            val fragment = MemberListFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.search_member_frag, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (searchType == SEARCH_TYPE.EMPLOYEE) {
            tv_companyHint.visibility = View.VISIBLE
        } else {
            tv_companyHint.visibility = View.GONE
        }

        rv_content.run {
            layoutManager = LinearLayoutManager(context)
            adapter = memberListAdapter
        }
    }

    fun showEmptyLayout(isEmpty: Boolean) {
        if (!ListUtil.isEmpty(memberListAdapter.data))
            memberListAdapter.clearnData()
        empty_layout.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    fun showMemberList(memberBeanList: List<MemberBean>) {
        tv_companyHint.visibility = View.GONE
        memberListAdapter.refresh(memberBeanList as ArrayList<MemberBean>)
    }

    fun insertMember(memberBean: MemberBean) {
        tv_companyHint.visibility = View.GONE
        memberListAdapter.clearnData()
        memberListAdapter.insert(memberBean)
    }
}