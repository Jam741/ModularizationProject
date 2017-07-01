package com.yingwumeijia.baseywmj.function.previewPic;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder;
import com.yingwumeijia.commonlibrary.widget.photoview.PhotoView;
import com.yingwumeijia.commonlibrary.widget.photoview.PhotoViewAttacher;

import java.util.ArrayList;


/**
 * Created by Jam on 16/8/30 下午6:17.
 * Describe:
 */
public class ViewPagerPreviewAdapter extends PagerAdapter {

    private ArrayList<String> pics;
    private Context context;
    private OnPicClickLisenter onPicClickLisenter;


    /**
     * 图片被点击
     */
    public interface OnPicClickLisenter {
        void picClicked(int position);
    }

    public ViewPagerPreviewAdapter(ArrayList<String> pics, Context context, OnPicClickLisenter onPicClickLisenter) {
        this.pics = pics;
        this.context = context;
        this.onPicClickLisenter = onPicClickLisenter;
    }


    @Override
    public int getCount() {
        if (pics == null) return 0;
        return pics.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        JImageLolder.INSTANCE.loadOriginal(context, photoView, pics.get(position));
        photoView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                if (onPicClickLisenter != null) onPicClickLisenter.picClicked(position);
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });
        container.addView(photoView);
        return photoView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
