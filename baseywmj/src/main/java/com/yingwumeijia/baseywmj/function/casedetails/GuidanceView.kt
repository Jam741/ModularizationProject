package com.yingwumeijia.baseywmj.function.casedetails

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import com.yingwumeijia.baseywmj.R

/**
 * Created by jamisonline on 2017/8/1.
 */
class GuidanceView(context: Context, imageResId: Int) : LinearLayout(context) {

    val view by lazy { LayoutInflater.from(context).inflate(R.layout.case_details_view, this, false) }

    val image: ImageView by lazy { (view.findViewById(R.id.image) as ImageView) }

    init {
        image.setImageResource(imageResId)
        addView(view)
    }
}