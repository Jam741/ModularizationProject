package com.yingwumeijia.commonlibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yingwumeijia.commonlibrary.R;
import com.yingwumeijia.commonlibrary.utils.TextViewUtils;


/**
 * Created by Jam on 2016/4/22.
 */
public class TabBarView extends LinearLayout {

    private TextView leftTextView;
    private TextView rightTextView;

    private String mLeftText;
    private int mLeftTextColor;
    private int mLeftTextSize;
    private int mLeftImg;

    private String mRightText;
    private int mRightTextColor;
    private int mRightTextSize;
    private int mRightImg;

    private Context mContext;

    private View view;

    public TabBarView(Context context) {
        this(context, null);
    }

    public TabBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabBarView, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);

            if (attr == R.styleable.TabBarView_leftTextColor) {
                mLeftTextColor = typedArray.getColor(attr, Color.BLACK);

            } else if (attr == R.styleable.TabBarView_rightTextColor) {
                mRightTextColor = typedArray.getColor(attr, Color.BLACK);

            } else if (attr == R.styleable.TabBarView_leftTextSize) {
//                mLeftTextSize = (int) typedArray.getDimension(attr, 0);
                 mLeftTextSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.TabBarView_rightTextSize) {
                mRightTextSize = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.TabBarView_leftTextImg) {
                mLeftImg = typedArray.getResourceId(attr, 0);

            } else if (attr == R.styleable.TabBarView_rightTextImg) {
                mRightImg = typedArray.getResourceId(attr, 0);

            } else if (attr == R.styleable.TabBarView_leftText) {
                mLeftText = typedArray.getString(attr);

            } else if (attr == R.styleable.TabBarView_rightText) {
                mRightText = typedArray.getString(attr);

            }
        }
        typedArray.recycle();
        initView();
        initContent();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int withSize =
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void initView() {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_tab_bar, null);
        leftTextView = (TextView) view.findViewById(R.id.left_text);
        rightTextView = (TextView) view.findViewById(R.id.right_text);
    }

    private void initContent() {
        leftTextView.setText(mLeftText);
        leftTextView.setTextSize(mLeftTextSize/4);
        leftTextView.setTextColor(mLeftTextColor);
        if (mLeftImg != 0) {
            TextViewUtils.setDrawableToLeft(mContext, leftTextView, mLeftImg);
        }

        rightTextView.setText(mRightText);
        rightTextView.setTextSize(mRightTextSize/4);
        rightTextView.setTextColor(mRightTextColor);
        if (mRightImg != 0) {
            TextViewUtils.setDrawableToRight(mContext, rightTextView, mRightImg);
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(layoutParams);
        addView(view);
    }


    public String getLeftText() {
        return mLeftText;
    }

    public void setLeftText(String mLeftText) {
        this.mLeftText = mLeftText;
        postInvalidate();
    }

    public int getLeftTextColor() {
        return mLeftTextColor;
    }

    public void setLeftTextColor(int mLeftTextColor) {
        this.mLeftTextColor = mLeftTextColor;
        postInvalidate();
    }

    public int getLeftTextSize() {
        return mLeftTextSize;
    }

    public void setLeftTextSize(int mLeftTextSize) {
        this.mLeftTextSize = mLeftTextSize;
        postInvalidate();
    }

    public int getLeftImg() {
        return mLeftImg;
    }

    public void setLeftImg(int mLeftImg) {
        this.mLeftImg = mLeftImg;
        postInvalidate();
    }

    public String getRightText() {
        return mRightText;
    }

    public void setRightText(String mRightText) {
        this.mRightText = mRightText;
        postInvalidate();
    }

    public int getRightTextColor() {
        return mRightTextColor;
    }

    public void setRightTextColor(int mRightTextColor) {
        this.mRightTextColor = mRightTextColor;
        postInvalidate();
    }

    public int getRightTextSize() {
        return mRightTextSize;
    }

    public void setRightTextSize(int mRightTextSize) {
        this.mRightTextSize = mRightTextSize;
        postInvalidate();
    }

    public int getRightImg() {
        return mRightImg;
    }

    public void setRightImg(int mRightImg) {
        this.mRightImg = mRightImg;
        postInvalidate();
    }
}
