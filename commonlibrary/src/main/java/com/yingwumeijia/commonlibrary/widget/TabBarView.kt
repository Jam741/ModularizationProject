package com.rx.android.jamspeedlibrary.utils.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.rx.android.jamspeedlibrary.R
import com.rx.android.jamspeedlibrary.utils.AndroidUtilities
import com.rx.android.jamspeedlibrary.utils.LogUtil
import com.rx.android.jamspeedlibrary.utils.ScreenUtils
import com.rx.android.jamspeedlibrary.utils.TextViewUtils


/**
 * Created by Jam on 2016/4/22.
 */
class TabBarView @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet = null, defStyleAttr: Int = 0) : LinearLayout(mContext, attrs, defStyleAttr) {

    private var leftTextView: TextView? = null
    private var rightTextView: TextView? = null

    private var mLeftText: String? = null
    private var mLeftTextColor: Int = 0
    private var mLeftTextSize: Int = 0
    private var mLeftImg: Int = 0

    private var mRightText: String? = null
    private var mRightTextColor: Int = 0
    private var mRightTextSize: Int = 0
    private var mRightImg: Int = 0

    private var view: View? = null

    init {
        val typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TabBarView, defStyleAttr, 0)
        for (i in 0..typedArray.getIndexCount() - 1) {
            val attr = typedArray.getIndex(i)

            if (attr == R.styleable.TabBarView_leftTextColor) {
                mLeftTextColor = typedArray.getColor(attr, Color.BLACK)

            } else if (attr == R.styleable.TabBarView_rightTextColor) {
                mRightTextColor = typedArray.getColor(attr, Color.BLACK)

            } else if (attr == R.styleable.TabBarView_leftTextSize) {
                //                mLeftTextSize = (int) typedArray.getDimension(attr, 0);
                mLeftTextSize = typedArray.getDimensionPixelSize(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()) as Int)

            } else if (attr == R.styleable.TabBarView_rightTextSize) {
                mRightTextSize = typedArray.getDimensionPixelSize(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()) as Int)

            } else if (attr == R.styleable.TabBarView_leftTextImg) {
                mLeftImg = typedArray.getResourceId(attr, 0)

            } else if (attr == R.styleable.TabBarView_rightTextImg) {
                mRightImg = typedArray.getResourceId(attr, 0)

            } else if (attr == R.styleable.TabBarView_leftText) {
                mLeftText = typedArray.getString(attr)

            } else if (attr == R.styleable.TabBarView_rightText) {
                mRightText = typedArray.getString(attr)

            }
        }
        typedArray.recycle()
        initView()
        initContent()
    }

    @Override
    protected fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //        int withSize =
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    @Override
    protected fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    private fun initView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_tab_bar, null)
        leftTextView = view!!.findViewById(R.id.left_text) as TextView
        rightTextView = view!!.findViewById(R.id.right_text) as TextView
    }

    private fun initContent() {
        leftTextView!!.setText(mLeftText)
        LogUtil.getInstance().debug("size", "-------------" + mLeftTextSize)
        LogUtil.getInstance().debug("size", "----rig---------" + mRightTextSize)
        leftTextView!!.setTextSize(mLeftTextSize / 4)
        leftTextView!!.setTextColor(mLeftTextColor)
        if (mLeftImg != 0) {
            TextViewUtils.setDrawableToLeft(mContext, leftTextView, mLeftImg)
        }

        rightTextView!!.setText(mRightText)
        rightTextView!!.setTextSize(mRightTextSize / 4)
        rightTextView!!.setTextColor(mRightTextColor)
        if (mRightImg != 0) {
            TextViewUtils.setDrawableToRight(mContext, rightTextView, mRightImg)
        }
        val layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        view!!.setLayoutParams(layoutParams)
        addView(view)
    }


    var leftText: String
        get() = mLeftText
        set(mLeftText) {
            this.mLeftText = mLeftText
            postInvalidate()
        }

    var leftTextColor: Int
        get() = mLeftTextColor
        set(mLeftTextColor) {
            this.mLeftTextColor = mLeftTextColor
            postInvalidate()
        }

    var leftTextSize: Int
        get() = mLeftTextSize
        set(mLeftTextSize) {
            this.mLeftTextSize = mLeftTextSize
            postInvalidate()
        }

    var leftImg: Int
        get() = mLeftImg
        set(mLeftImg) {
            this.mLeftImg = mLeftImg
            postInvalidate()
        }

    var rightText: String
        get() = mRightText
        set(mRightText) {
            this.mRightText = mRightText
            postInvalidate()
        }

    var rightTextColor: Int
        get() = mRightTextColor
        set(mRightTextColor) {
            this.mRightTextColor = mRightTextColor
            postInvalidate()
        }

    var rightTextSize: Int
        get() = mRightTextSize
        set(mRightTextSize) {
            this.mRightTextSize = mRightTextSize
            postInvalidate()
        }

    var rightImg: Int
        get() = mRightImg
        set(mRightImg) {
            this.mRightImg = mRightImg
            postInvalidate()
        }
}
