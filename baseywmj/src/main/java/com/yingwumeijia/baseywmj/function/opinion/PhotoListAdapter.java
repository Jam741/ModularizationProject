package com.yingwumeijia.baseywmj.function.opinion;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yingwumeijia.baseywmj.R;
import com.yingwumeijia.commonlibrary.utils.ScreenUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jam on 16/10/8 下午2:30.
 * Describe:
 */
public class PhotoListAdapter extends BaseAdapter {

    private static final int MAX_PHOTO = 4;

    private List<PhotoBean> photoBeanList;
    private ArrayList<String> image_list;
    private OnPhotoCountChangeListener countChangeListener;


    public PhotoListAdapter() {
        photoBeanList = new ArrayList<>();
        PhotoBean bean = new PhotoBean();
        bean.setButton(true);
        photoBeanList.add(bean);
    }

    public void setCountChangeListener(OnPhotoCountChangeListener countChangeListener) {
        this.countChangeListener = countChangeListener;
    }

    @Override
    public int getCount() {
        return photoBeanList.size();
    }

    @Override
    public PhotoBean getItem(int position) {
        return photoBeanList.get(position);
    }


    public ArrayList<String> getImageList() {
        if (image_list == null) image_list = new ArrayList<>();
        image_list.clear();
        for (PhotoBean p :
                photoBeanList) {
            if (!p.isButton())
                image_list.add(p.getImagPath());
        }
//        for (int i = 0; i < photoBeanList.size(); i++) {
//            PhotoBean b = photoBeanList.get(i);
//            if (!b.isButton())
//                image_list.add(b.getImagPath());
//        }
        return image_list;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addPhoto(PhotoBean photoBean) {

        if (photoBeanList.get(photoBeanList.size() - 1).isButton()) {
            photoBeanList.remove(photoBeanList.size() - 1);
        }

        photoBeanList.add(photoBean);

        if (getCount() < MAX_PHOTO) {
            PhotoBean bean = new PhotoBean();
            bean.setButton(true);
            photoBeanList.add(bean);
        }
        notifyDataSetChanged();
        if (countChangeListener != null) {
            countChangeListener.countChange(getRealCount());
        }
    }


    public void addPhotos(List<String> list) {
        if (photoBeanList.get(photoBeanList.size() - 1).isButton()) {
            photoBeanList.clear();
        }


        for (int i = 0; i < list.size(); i++) {
            PhotoBean photoBean = new PhotoBean(list.get(i), false);
            photoBeanList.add(photoBean);
        }

        if (getCount() < MAX_PHOTO) {
            PhotoBean bean = new PhotoBean();
            bean.setButton(true);
            photoBeanList.add(bean);
        }
        notifyDataSetChanged();
        if (countChangeListener != null) {
            countChangeListener.countChange(getRealCount());
        }
    }

    public int getRealCount() {
        int count = 0;
        for (int i = 0; i < photoBeanList.size(); i++) {
            if (!photoBeanList.get(i).isButton()) {
                count++;
            }
        }
        return count;
    }

    public void remove(int position) {
        photoBeanList.remove(position);


        if (!photoBeanList.get(photoBeanList.size() - 1).isButton()) {
            PhotoBean bean = new PhotoBean();
            bean.setButton(true);
            photoBeanList.add(bean);
        }
        notifyDataSetChanged();
        if (countChangeListener != null) {
            countChangeListener.countChange(getRealCount());
        }

    }

    public List<PhotoBean> getPhotoBeanList() {
        if (photoBeanList.get(photoBeanList.size() - 1).isButton()) {
            photoBeanList.remove(photoBeanList.size() - 1);
        }
        return photoBeanList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PhotoBean photoBean = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_opinion_photo, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        int s = ScreenUtils.INSTANCE.dp2px(20f, parent.getContext()) * 2 + (ScreenUtils.INSTANCE.dp2px(7f, parent.getContext()) * 2);
        int itemWidth = (ScreenUtils.INSTANCE.getScreenWidth() - s) / 3;

        ScreenUtils.INSTANCE.setLayoutScaleByWidth(convertView, itemWidth, 1f);

        if (photoBean.isButton()) {
            holder.btnRemove.setVisibility(View.GONE);
            holder.divider.setVisibility(View.GONE);
            Glide.with(parent.getContext()).load(R.drawable.mine_suggestions_add_pic_bt).fitCenter().into(holder.ivContent);
        } else {
            holder.btnRemove.setVisibility(View.VISIBLE);
            holder.divider.setVisibility(View.VISIBLE);
            Glide.with(parent.getContext()).load(Uri.fromFile(new File(photoBean.getImagPath()))).centerCrop().into(holder.ivContent);
        }

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });

        return convertView;
    }


    static class ViewHolder {
        ImageView ivContent;
        ImageView btnRemove;
        View divider;

        ViewHolder(View view) {
            ivContent = (ImageView) view.findViewById(R.id.iv_content);
            btnRemove = (ImageView) view.findViewById(R.id.btn_remove);
            divider = view.findViewById(R.id.divider);
        }
    }


    public interface OnPhotoCountChangeListener {

        void countChange(int count);
    }
}
