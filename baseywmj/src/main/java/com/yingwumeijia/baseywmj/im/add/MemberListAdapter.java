package com.yingwumeijia.baseywmj.im.add;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.yingwumeijia.baseywmj.R;
import com.yingwumeijia.baseywmj.entity.bean.MemberBean;
import com.yingwumeijia.commonlibrary.utils.ListUtil;
import com.yingwumeijia.commonlibrary.utils.TextViewUtils;
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter;
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder;
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jam on 2016/12/9 下午6:28.
 * Describe:
 */

public class MemberListAdapter extends CommonRecyclerAdapter<MemberBean> {

    private Fragment context;

    public MemberListAdapter(Fragment context) {
        this(null, context, null, R.layout.item_session_member);
    }

    private Map<Integer, Boolean> map = new HashMap<>();

    public MemberListAdapter(@Nullable Activity activity, @Nullable Fragment fragment, @Nullable ArrayList<MemberBean> data, int layoutId) {
        super(activity, fragment, data, layoutId);
    }


    @Override
    public void convert(RecyclerViewHolder holder, final MemberBean memberBean, final int position) {

        boolean isCustomer = memberBean.getUserType().equals("c");
        holder
                .setVisible(R.id.employeeLayout, !isCustomer)
                .setVisible(R.id.tv_cName, isCustomer)
                .setChecked(R.id.btn_checked, memberBean.isSelected())
                .setVisible(R.id.iv_right, false);
        JImageLolder.INSTANCE.loadPortrait256(context, (ImageView) holder.getViewWith(R.id.iv_portrait), memberBean.getShowHead());

        if (isCustomer) {
            holder.setTextWith(R.id.tv_cName, memberBean.getShowName());
        } else {
            holder.setTextWith(R.id.tv_eName, memberBean.getShowName());
            holder.setTextWith(R.id.tv_eJob, memberBean.getUserTitle());
            holder.setTextWith(R.id.tv_eCompany, memberBean.getCompanyName());
        }

        CheckBox checkBox = (CheckBox) holder.getViewWith(R.id.btn_checked);
        checkBox.setEnabled(!memberBean.isJoinSession());//已加入会话的成员不能点击
        TextViewUtils.setDrawableToLeft(context.getContext(), checkBox, memberBean.isJoinSession() ? R.mipmap.im_added_member_ico : R.drawable.connect_team_selector);


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                memberBean.setSelected(isChecked);
            }
        });


    }

    @Override
    public void clearnData() {
        super.clearnData();
        map.clear();
    }


    public List<String> getSelectedMembers() {
        List<String> selsetedMembers = new ArrayList<>();
        if (!ListUtil.INSTANCE.isEmpty(getData()))
            for (MemberBean m : getData()) {
                if (m.isSelected()) selsetedMembers.add(m.getImUid());
            }
        return selsetedMembers;
    }
}
