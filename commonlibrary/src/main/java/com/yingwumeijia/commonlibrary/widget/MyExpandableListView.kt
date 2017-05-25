package com.rx.android.jamspeedlibrary.utils.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ExpandableListView

/**
 * Created by Jam on 16/10/20 下午4:27.
 * Describe:
 */

class MyExpandableListView(context: Context, attrs: AttributeSet)// TODO Auto-generated constructor stub
    : ExpandableListView(context, attrs) {

    @Override
    protected fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // TODO Auto-generated method stub
        val expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2,

                MeasureSpec.AT_MOST)

        super.onMeasure(widthMeasureSpec, expandSpec)
    }
}
