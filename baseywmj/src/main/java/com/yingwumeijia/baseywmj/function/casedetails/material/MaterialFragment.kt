package com.yingwumeijia.baseywmj.function.casedetails.team

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseFragment
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.function.casedetails.material.*
import com.yingwumeijia.baseywmj.utils.MoneyFormatUtils
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import kotlinx.android.synthetic.main.infomation_brand.*
import kotlinx.android.synthetic.main.infomation_checks.*
import kotlinx.android.synthetic.main.infomation_cost.*
import kotlinx.android.synthetic.main.infomation_design_display.*
import kotlinx.android.synthetic.main.infomation_design_house.*
import java.math.BigDecimal

/**
 * Created by jamisonline on 2017/6/26.
 */
class MaterialFragment : JBaseFragment(), MaterialContract.View {

    val caseId by lazy { arguments.getInt(Constant.KEY_CASE_DETAIL_ID, Constant.DEFAULT_INT_VALUE) }

    val presenter by lazy { MaterialPresenter(this, caseId, this) }

    override fun initCost(costs: List<CaseInfomationBean.CostsBean>, totallCost: BigDecimal, caseCover: String, totallArea: BigDecimal) {
        JImageLolder.load480(context, iv_costBg, caseCover)
        tv_totalCost.text = "¥ " + MoneyFormatUtils.fromatWan(totallCost) + "万"
        tv_totalArea.text = "施工面积 " + totallArea.toInt() + " m²"
    }

    override fun initCostBrands(costBrands: List<CaseInfomationBean.CostBrandsBean>) {
        layoutMaterialBrand.visibility = View.VISIBLE
        ex_materialBrand.run {
            setOnGroupClickListener { _, _, _, _ -> return@setOnGroupClickListener true }
            setAdapter(MaterialBrandAdapter(costBrands, ex_materialBrand))
        }

    }

    override fun initDesignHouseImage(houseImages: List<CaseInfomationBean.DesignMaterialsBean.HouseImagesBean>) {
        layout_DesignHouseImage.visibility = View.VISIBLE
        rv_houseImage.run {
            layoutManager = LinearLayoutManager(context)
            adapter = presenter.houseDesignAdapter
        }
    }

    override fun initDesignDisplayImage(displayImages: List<CaseInfomationBean.DesignMaterialsBean.DisplayImagesBean>) {
        layout_designDisplayImage.visibility = View.VISIBLE
        rv_display.run {
            layoutManager = LinearLayoutManager(context)
            adapter = presenter.designDisplayAdapter
        }
    }

    override fun initChecks(checksBeanList: List<BigChecksBean>) {
        layout_checks.visibility = View.VISIBLE
        rv_checks.run {
            layoutManager = LinearLayoutManager(context)
            adapter = presenter.checksAdapter
        }
    }


    companion object {

        fun newInstance(caseId: Int): MaterialFragment {
            val args = Bundle()
            args.putInt(Constant.KEY_CASE_DETAIL_ID, caseId)
            val fragment = MaterialFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.material_frag, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
    }


}