package com.yingwumeijia.baseywmj.function.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yingwumeijia.baseywmj.R;
import com.yingwumeijia.baseywmj.entity.bean.CaseTypeEnum;

import java.util.List;

/*
 * Created by Jam on 2016/8/5 11:14.
 * Describe:
 */
public class NewCaseTypeAdapter extends BaseAdapter {

    private final List<CaseTypeEnum> mCaseTypeEnumList;
    private final Context mContext;

    public NewCaseTypeAdapter(List<CaseTypeEnum> mCaseTypeEnumList, Context mContext) {
        this.mCaseTypeEnumList = mCaseTypeEnumList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mCaseTypeEnumList.size();
    }

    @Override
    public CaseTypeEnum getItem(int position) {
        return mCaseTypeEnumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        CaseTypeEnum caseTypeEnum = mCaseTypeEnumList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_case_type, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();


        String name = caseTypeEnum.getName();

        if (caseTypeEnum.getCount() != -1) {
            name = name + "  " + caseTypeEnum.getCount();
        }

        viewHolder.tvType.setText(name);
        if (caseTypeEnum.isSelected()) {
            viewHolder.tvType.setTextColor(mContext.getResources().getColor(R.color.color_6));
        } else {
            viewHolder.tvType.setTextColor(mContext.getResources().getColor(R.color.text_color_whide));
        }
        return convertView;
    }

    public void setSelected(int position) {
        for (int i = 0; i < mCaseTypeEnumList.size(); i++) {
            mCaseTypeEnumList.get(i).setSelected(false);
        }
        mCaseTypeEnumList.get(position).setSelected(true);
        notifyDataSetChanged();
    }

    public CaseTypeEnum getSelectorId() {
        CaseTypeEnum caseTypeEnum = null;
        for (int i = 0; i < mCaseTypeEnumList.size(); i++) {
            if (mCaseTypeEnumList.get(i).isSelected()) caseTypeEnum = mCaseTypeEnumList.get(i);
        }

        return caseTypeEnum;
    }

    static class ViewHolder {
        TextView tvType;

        public ViewHolder(View convertView) {
            tvType = (TextView) convertView.findViewById(R.id.tv_type);
        }
    }
}
