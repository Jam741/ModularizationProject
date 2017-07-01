package com.yingwumeijia.baseywmj.function.previewPic

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import kotlinx.android.synthetic.main.viewpager_preview_frag.*

/**
 * Created by jamisonline on 2017/6/30.
 */
class PreViewActivity : JBaseActivity(), ViewPager.OnPageChangeListener, ViewPagerPreviewAdapter.OnPicClickLisenter {

    private val pics by lazy { intent.getStringArrayListExtra(Constant.KEY_LIST_PIC) }
    private val index by lazy { intent.getIntExtra(Constant.KEY_INDEX, 0) }
    private val picAdapter by lazy { ViewPagerPreviewAdapter(pics, context, this) }

    private var titles: ArrayList<String>? = null

    private var contents: ArrayList<String>? = null

    private var mCallback: OnPicSelectedListener? = null

    private var isNeedHide = true

    companion object {

        fun start(context: Context, previewModel: PreviewModel, index: Int) {

            val starter = Intent(context, PreViewActivity::class.java)
            starter.putExtra(Constant.KEY_LIST_PIC, previewModel.pics)
            starter.putExtra(Constant.KEY_LIST_TITLE, previewModel.titles)
            starter.putExtra(Constant.KEY_LIST_CONTENT, previewModel.contents)
            starter.putExtra(Constant.KEY_INDEX, index)
            context.startActivity(starter)
        }

    }

    // Container Activity must implement this interface
    interface OnPicSelectedListener {
        fun onPicSelected(position: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewpager_preview_frag)

        topLeft.setOnClickListener { close() }
        titles = intent.getStringArrayListExtra(Constant.KEY_LIST_TITLE)
        contents = intent.getStringArrayListExtra(Constant.KEY_LIST_CONTENT)
        vp_content.run {
            adapter = picAdapter
            addOnPageChangeListener(this@PreViewActivity)
            currentItem = index
        }
        onPageSelected(index)
    }


    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        if (titles != null) tv_title.text = titles!![position]
        if (contents != null) tv_content.text = contents!![position]
        tv_page.text = (position + 1).toString() + "/" + pics.size
    }

    override fun picClicked(position: Int) {
        if (mCallback != null) mCallback!!.onPicSelected(position)
        hideOtherView(isNeedHide)
    }


    private fun hideOtherView(needHide: Boolean) {
        if (needHide) {
            topLeft.visibility = View.GONE
            tv_layout.visibility = View.GONE
        } else {
            topLeft.visibility = View.VISIBLE
            tv_layout.visibility = View.VISIBLE
        }
        this.isNeedHide = !needHide
    }
}