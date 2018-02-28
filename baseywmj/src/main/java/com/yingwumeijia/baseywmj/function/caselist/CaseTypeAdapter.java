package com.yingwumeijia.baseywmj.function.caselist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yingwumeijia.baseywmj.R;
import com.yingwumeijia.baseywmj.entity.bean.CaseTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jam on 2016/8/5 11:14.
 * Describe:
 */
public class CaseTypeAdapter extends RecyclerView.Adapter<CaseTypeAdapter.ViewHolder> {

    private final List<CaseTypeEnum> mCaseTypeEnumList = new ArrayList<>();
    private final Context mContext;
    private OnMyItemClickLisenter onMyItemClickLisenter;

    public void setSelectedPosition(int selectedPosition) {
        for (int i = 0; i < mCaseTypeEnumList.size(); i++) {
            CaseTypeEnum type = mCaseTypeEnumList.get(i);
            if (i == selectedPosition) {
                type.setSelected(true);
            } else {
                type.setSelected(false);
            }
        }
        notifyDataSetChanged();
    }


    public void setOnMyItemClickLisenter(OnMyItemClickLisenter onMyItemClickLisenter) {
        this.onMyItemClickLisenter = onMyItemClickLisenter;
    }

    interface OnMyItemClickLisenter {
        void itemClick(CaseTypeEnum caseTypeEnum, int positon);
    }

    public CaseTypeAdapter(Context context) {
        this.mContext = context;
    }

    public CaseTypeAdapter(Context context, List<CaseTypeEnum> caseTypeEnumList) {
        this.mContext = context;
        this.mCaseTypeEnumList.addAll(caseTypeEnumList);
    }

    public void refreshData(List<CaseTypeEnum> mCaseTypeEnumList) {
        this.mCaseTypeEnumList.clear();
        this.mCaseTypeEnumList.addAll(mCaseTypeEnumList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.item_case_type, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CaseTypeEnum caseTypeEnum = mCaseTypeEnumList.get(position);
        if (caseTypeEnum.isSelected()) {
            holder.tv_type.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        } else {
            holder.tv_type.setTextColor(mContext.getResources().getColor(R.color.text_color_whide));
        }


        String name = caseTypeEnum.getName();


        if (caseTypeEnum.getCount() != -1) {
            name = name + "（" + caseTypeEnum.getCount() + "）";
        }

        holder.tv_type.setText(name+"ss");

        holder.tv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMyItemClickLisenter != null) {
                    onMyItemClickLisenter.itemClick(mCaseTypeEnumList.get(position), position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mCaseTypeEnumList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_type;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
        }
    }
}
