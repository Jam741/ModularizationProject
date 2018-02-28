package com.yingwumeijia.baseywmj.function.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import com.yingwumeijia.baseywmj.R;
import com.yingwumeijia.baseywmj.entity.bean.CaseTypeEnum;

import java.util.List;


/**
 * Created by Jam on 2017/5/4 下午4:03.
 * Describe:
 */

public class CaseFilterClassfilyAdapter extends BaseExpandableListAdapter {

    private String[] grpous = {"完工作品", "设计作品"};

    private List<CaseTypeEnum> doneCasesTypes;
    private List<CaseTypeEnum> designCasesTypes;

    public CaseFilterClassfilyAdapter(List<CaseTypeEnum> doneCasesTypes, List<CaseTypeEnum> designCasesTypes) {
        this.doneCasesTypes = doneCasesTypes;
        this.designCasesTypes = designCasesTypes;
    }

    @Override
    public int getGroupCount() {
        return grpous.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition == 0) {
            return doneCasesTypes.size();
        }
        return designCasesTypes.size();
    }

    @Override
    public String getGroup(int groupPosition) {
        return grpous[groupPosition];
    }

    @Override
    public CaseTypeEnum getChild(int groupPosition, int childPosition) {
        if (groupPosition == 0) {
            return doneCasesTypes.get(childPosition);
        }
        return designCasesTypes.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.case_filter_nav_group, parent, false);
            groupHolder = new GroupHolder();
            groupHolder.tvGroup = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        groupHolder.tvGroup.setText(grpous[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        CaseTypeEnum caseTypeEnum = getChild(groupPosition, childPosition);
        ChildHolder childHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.case_filter_nav_child, parent, false);
            childHolder = new ChildHolder();
            childHolder.tvChild = (TextView) convertView.findViewById(R.id.tv_child);
            childHolder.divider = convertView.findViewById(R.id.divider);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        childHolder.divider.setVisibility(childPosition == getChildrenCount(groupPosition) - 1 ? View.VISIBLE : View.GONE);

        childHolder.tvChild.setText(caseTypeEnum.getName() + "  " + caseTypeEnum.getCount());

        if (caseTypeEnum.isSelected()) {
            childHolder.tvChild.setTextColor(parent.getContext().getResources().getColor(R.color.color_6));
        } else {
            childHolder.tvChild.setTextColor(parent.getContext().getResources().getColor(R.color.text_color_whide));
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setSelected(int grouPositionint, int position) {

        for (int i = 0; i < doneCasesTypes.size(); i++) {
            doneCasesTypes.get(i).setSelected(false);
        }
        for (int i = 0; i < designCasesTypes.size(); i++) {
            designCasesTypes.get(i).setSelected(false);
        }
        if (grouPositionint == 0) {
            doneCasesTypes.get(position).setSelected(true);
        } else {
            designCasesTypes.get(position).setSelected(true);
        }
        notifyDataSetChanged();
    }

    public CaseTypeEnum getSelectorId() {
        CaseTypeEnum caseTypeEnum = null;
        for (int i = 0; i < doneCasesTypes.size(); i++) {
            if (doneCasesTypes.get(i).isSelected()) caseTypeEnum = doneCasesTypes.get(i);
        }
        if (caseTypeEnum == null) {
            for (int i = 0; i < designCasesTypes.size(); i++) {
                if (designCasesTypes.get(i).isSelected()) caseTypeEnum = designCasesTypes.get(i);
            }
        }

        return caseTypeEnum;
    }


    class GroupHolder {
        TextView tvGroup;
    }

    class ChildHolder {
        TextView tvChild;
        View divider;
    }
}
