package com.yingwumeijia.baseywmj.im;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yingwumeijia.baseywmj.R;
import com.yingwumeijia.baseywmj.entity.bean.MemberBean;
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder;

import java.util.List;
import java.util.Map;


/**
 * Created by Jam on 16/9/9 下午12:02.
 * Describe:
 */
public class TeamAdapter extends BaseExpandableListAdapter {

    public static final int TYPE_CHILD = 001;
    public static final int TYPE_CHILD_BOTTOM = 002;

    private Map<String, List<MemberBean>> mData;
    private Activity mContext;
    private String mSessionId;

    private OnChildLongClickListener onChildLongClickListener;
    private OnChildClickListener onChildClickListener;

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.onChildClickListener = onChildClickListener;
    }

    public void setOnChildLongClickListener(OnChildLongClickListener onChildLongClickLisenter) {
        this.onChildLongClickListener = onChildLongClickLisenter;
    }

    public interface AddTeamOperationListener {
        void addTeam(String teanType, int groupPosition);
    }

    AddTeamOperationListener addTeamOperationListener;

    public void setAddTeamOperationListener(AddTeamOperationListener addTeamOperationListener) {
        this.addTeamOperationListener = addTeamOperationListener;
    }

    public TeamAdapter(Map<String, List<MemberBean>> mData, Activity mContext, String sessionId) {
        this.mData = mData;
        this.mContext = mContext;
        this.mSessionId = sessionId;
    }

    public void refresh(Map<String, List<MemberBean>> data) {
        this.mData.clear();
        this.mData.putAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mData.get(getGroup(groupPosition)).size();
    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        if (childPosition == mData.get(getGroup(groupPosition)).size() - 1) {
            return TYPE_CHILD_BOTTOM;
        }
        return TYPE_CHILD;
    }


    @Override
    public int getChildTypeCount() {
        return 3;
    }


    @Override
    public String getGroup(int groupPosition) {
        return groupPosition == 0 ? "c" : "e";
    }

    @Override
    public MemberBean getChild(int groupPosition, int childPosition) {

        if (childPosition == mData.get(getGroup(groupPosition)).size())
            return null;
        return mData.get(getGroup(groupPosition)).get(childPosition);

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
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_addteam_group, parent, false);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        }
        groupViewHolder = (GroupViewHolder) convertView.getTag();
        initGroupView(groupViewHolder, groupPosition);

        return convertView;
    }

    private void initGroupView(GroupViewHolder groupViewHolder, final int groupPosition) {
        groupViewHolder.tvGroupName.setText(groupPosition == 0 ? "业主" : "业者");
    }


    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        ChildBottomViewHolder childBottomViewHolder;
        int currentItemType = getChildType(groupPosition, childPosition);
        MemberBean memberBean = mData.get(getGroup(groupPosition)).get(childPosition);
        switch (currentItemType) {
            case TYPE_CHILD:
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_addteam_child, parent, false);
                    childViewHolder = new ChildViewHolder(convertView);
                    convertView.setTag(childViewHolder);
                }
                childViewHolder = (ChildViewHolder) convertView.getTag();
                initChildView(memberBean, childViewHolder);
                break;
            case TYPE_CHILD_BOTTOM:
                if (convertView == null) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_addteam_child_bottom, parent, false);
                    childBottomViewHolder = new ChildBottomViewHolder(convertView);
                    convertView.setTag(childBottomViewHolder);
                }
                childBottomViewHolder = (ChildBottomViewHolder) convertView.getTag();
                initChildBottomView(memberBean, childBottomViewHolder);
                break;
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onChildClickListener != null)
                    onChildClickListener.onChildClick(groupPosition, childPosition);
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onChildLongClickListener != null)
                    onChildLongClickListener.onChildLongClick(groupPosition, childPosition);
                return false;
            }
        });

        return convertView;
    }

    private void initChildBottomView(MemberBean memberBean, ChildBottomViewHolder childBottomViewHolder) {
        childBottomViewHolder.employeeLayout.setVisibility(memberBean.getUserType().equals("c") ? View.GONE : View.VISIBLE);
        childBottomViewHolder.tvCName.setVisibility(memberBean.getUserType().equals("c") ? View.VISIBLE : View.GONE);
        childBottomViewHolder.ivRight.setVisibility(memberBean.getUserType().equals("c") ? View.GONE : View.VISIBLE);
        boolean isCoustom = memberBean.getUserType().equals("c");
        if (isCoustom) {
            childBottomViewHolder.tvCName.setText(memberBean.getShowName());
        } else {
            childBottomViewHolder.tvEName.setText(memberBean.getShowName());
            childBottomViewHolder.tvEJob.setText(memberBean.getUserTitle());
            childBottomViewHolder.tvECompany.setText(memberBean.getCompanyName());
        }
        JImageLolder.INSTANCE.loadPortrait256(mContext, childBottomViewHolder.ivPortrait, memberBean.getShowHead());
    }

    private void initChildView(MemberBean memberBean, ChildViewHolder childViewHolder) {
        childViewHolder.employeeLayout.setVisibility(memberBean.getUserType().equals("c") ? View.GONE : View.VISIBLE);
        childViewHolder.tvCName.setVisibility(memberBean.getUserType().equals("c") ? View.VISIBLE : View.GONE);
        childViewHolder.ivRight.setVisibility(memberBean.getUserType().equals("c") ? View.GONE : View.VISIBLE);
        boolean isCoustom = memberBean.getUserType().equals("c");
        if (isCoustom) {
            childViewHolder.tvCName.setText(memberBean.getShowName());
        } else {
            childViewHolder.tvEName.setText(memberBean.getShowName());
            childViewHolder.tvEJob.setText(memberBean.getUserTitle());
            childViewHolder.tvECompany.setText(memberBean.getCompanyName());
        }
        JImageLolder.INSTANCE.loadPortrait256(mContext, childViewHolder.ivPortrait, memberBean.getShowHead());
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    /**
     * 标题布局Holder
     */
    static class GroupViewHolder {

        TextView tvGroupName;
        TextView btnAddTeam;

        GroupViewHolder(View view) {
            tvGroupName = (TextView) view.findViewById(R.id.tv_groupName);
            btnAddTeam = (TextView) view.findViewById(R.id.btn_addTeam);
        }
    }

    /**
     * child布局holder
     */
    static class ChildViewHolder {
        LinearLayout employeeLayout;
        ImageView ivPortrait;
        ImageView ivRight;
        TextView tvEName;
        TextView tvEJob;
        TextView tvECompany;
        TextView tvCName;

        ChildViewHolder(View view) {
            employeeLayout = (LinearLayout) view.findViewById(R.id.employeeLayout);
            ivPortrait = (ImageView) view.findViewById(R.id.iv_portrait);
            ivRight = (ImageView) view.findViewById(R.id.iv_right);
            tvEName = (TextView) view.findViewById(R.id.tv_eName);
            tvEJob = (TextView) view.findViewById(R.id.tv_eJob);
            tvECompany = (TextView) view.findViewById(R.id.tv_eCompany);
            tvCName = (TextView) view.findViewById(R.id.tv_cName);
        }
    }

    /**
     * child bottom布局holder
     */
    static class ChildBottomViewHolder {
        LinearLayout employeeLayout;
        ImageView ivPortrait;
        ImageView ivRight;
        TextView tvEName;
        TextView tvEJob;
        TextView tvECompany;
        TextView tvCName;

        ChildBottomViewHolder(View view) {
            employeeLayout = (LinearLayout) view.findViewById(R.id.employeeLayout);
            ivPortrait = (ImageView) view.findViewById(R.id.iv_portrait);
            ivRight = (ImageView) view.findViewById(R.id.iv_right);
            tvEName = (TextView) view.findViewById(R.id.tv_eName);
            tvEJob = (TextView) view.findViewById(R.id.tv_eJob);
            tvECompany = (TextView) view.findViewById(R.id.tv_eCompany);
            tvCName = (TextView) view.findViewById(R.id.tv_cName);
        }
    }


    public interface OnChildLongClickListener {
        boolean onChildLongClick(int groupPosition, int childPosition);
    }

    public interface OnChildClickListener {
        void onChildClick(int groupPosition, int childPosition);
    }
}
