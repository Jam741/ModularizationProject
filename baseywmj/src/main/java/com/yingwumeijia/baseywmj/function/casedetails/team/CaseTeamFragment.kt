package com.yingwumeijia.baseywmj.function.casedetails.team

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.yingwumeijia.baseywmj.AppTypeManager
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.function.StartActivityManager
import kotlinx.android.synthetic.main.case_team_frag.*

/**
 * Created by jamisonline on 2017/6/26.
 */
class CaseTeamFragment : JBaseFragment(), CaseTeamContract.View {


    val caseId by lazy { arguments.getInt(Constant.KEY_CASE_DETAIL_ID, Constant.DEFAULT_INT_VALUE) }

    val presenter by lazy { CaseTeamPresenter(this, caseId, this) }

    override fun supportMJProject(support: Boolean) {
        Logger.d(support)
        image_mjProject.setImageResource(if (support) {
            if (AppTypeManager.isAppC()) R.mipmap.mj_protect_pic_more else R.mipmap.mj_protect_pic
        } else {
            R.mipmap.mj_no_protect_pic
        })
        image_mjProject.setOnClickListener { if (support && AppTypeManager.isAppC()) StartActivityManager.startMjProjectInfoPage(activity) }
    }

    override fun showTeamList(teamData: ProductionTeamBean) {
        rv_Team.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = presenter.teamAdapter
        }
    }

    override fun showCeremonyPic(ceremonyBeanList: List<ProductionTeamBean.SurroundingMaterials.CeremonyBean>) {
        Logger.d(Gson().toJson(ceremonyBeanList))
        rv_ceremony.run {
            adapter = presenter.ceremonyAdapter
        }
        presenter.ceremonyAdapter.refresh(ceremonyBeanList as ArrayList<ProductionTeamBean.SurroundingMaterials.CeremonyBean>)

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