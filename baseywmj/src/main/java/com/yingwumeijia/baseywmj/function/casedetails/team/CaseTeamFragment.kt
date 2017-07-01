package com.yingwumeijia.baseywmj.function.casedetails.team

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.constant.Constant
import kotlinx.android.synthetic.main.case_team_frag.*

/**
 * Created by jamisonline on 2017/6/26.
 */
class CaseTeamFragment : JBaseFragment(),CaseTeamContract.View {


    val caseId by lazy { arguments.getInt(Constant.KEY_CASE_DETAIL_ID, Constant.DEFAULT_INT_VALUE) }

    val presenter by lazy { CaseTeamPresenter(this,caseId,this) }

    override fun showTeamList(teamData: ProductionTeamBean) {
        rv_Team.run {
            layoutManager = LinearLayoutManager(context)
            adapter = presenter.teamAdapter
        }
    }

    override fun showCeremonyPic(ceremonyBeanList: List<ProductionTeamBean.SurroundingMaterials.CeremonyBean>) {
        rv_ceremony.run {
            layoutManager = LinearLayoutManager(context)
            adapter = presenter.ceremonyAdapter
        }
    }

    companion object {

        fun newInstance(caseId: Int): CaseTeamFragment {
            val args = Bundle()
            args.putInt(Constant.KEY_CASE_DETAIL_ID, caseId)
            val fragment = CaseTeamFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.case_team_frag, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
    }


}