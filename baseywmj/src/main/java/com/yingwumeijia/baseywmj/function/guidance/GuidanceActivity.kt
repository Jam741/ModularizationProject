package com.yingwumeijia.baseywmj.function.guidance


import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import kotlinx.android.synthetic.main.guidance_act.*
import java.util.*


/**
 * Created by Jam on 2017/3/10 下午5:58.
 * Describe:
 */

abstract open class GuidanceActivity : JBaseActivity() {

    private var isLastPage: Boolean = false
    private var isDragPage: Boolean = false
    private var canJumpPage = true
    private val views by lazy { createViews() }

    abstract fun createViews(): ArrayList<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: add setContentView(...) invocation
        /*set it to be no title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        /*set it to be full screen*/
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.guidance_act)

        initViews()
    }

    private fun initViews() {


        vp_guidance.adapter = MyPageAdapter()
        vp_guidance.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                Log.v("AAA", isLastPage.toString() + "   " + isDragPage + "   " + positionOffsetPixels)
                if (isLastPage && isDragPage && positionOffsetPixels == 0) {   //当前页是最后一页，并且是拖动状态，并且像素偏移量为0
                    if (canJumpPage) {
                        canJumpPage = false
                        JumpToNext()
                    }
                }
            }

            override fun onPageSelected(position: Int) {
                isLastPage = position == views.size - 1
                rv_point.check(rv_point.getChildAt(position).id)
            }

            override fun onPageScrollStateChanged(state: Int) {
                isDragPage = state == 1

            }
        })

    }

    private fun JumpToNext() {
        ActivityCompat.finishAfterTransition(context)
        next()
    }

    abstract operator fun next()

    internal inner class MyPageAdapter : PagerAdapter() {

        override fun getCount(): Int {
            return views.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }


        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            container.addView(views[position])
            return views[position]
        }

        override fun getItemPosition(`object`: Any?): Int {
            return views.indexOf(`object`)
        }

    }
}
