package com.yingwumeijia.baseywmj.function.casedetails.material;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rx.android.jamspeedlibrary.utils.view.MyGridView;
import com.yingwumeijia.baseywmj.R;
import com.yingwumeijia.baseywmj.function.WebViewManager;
import com.yingwumeijia.baseywmj.utils.MoneyFormatUtils;
import com.yingwumeijia.commonlibrary.utils.ListUtil;
import com.yingwumeijia.commonlibrary.utils.adapter.CommonAdapter;
import com.yingwumeijia.commonlibrary.utils.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jam on 2017/3/1 下午2:03.
 * Describe:
 */

public class MaterialBrandAdapter extends BaseExpandableListAdapter {

    private List<CaseInfomationBean.CostBrandsBean> data;
    private ExpandableListView expandableListView;

    private Map<Integer, Boolean> groupVisibleMap = new HashMap<>();

    public MaterialBrandAdapter(List<CaseInfomationBean.CostBrandsBean> data, ExpandableListView expandableListView) {
        this.data = data;
        this.expandableListView = expandableListView;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (data.get(groupPosition).getBrands() == null) {
            return 0;
        }
        return 1;
    }

    @Override
    public CaseInfomationBean.CostsBean getGroup(int groupPosition) {
        return data.get(groupPosition).getCostInfo();
    }

    @Override
    public List<CaseInfomationBean.CostBrandsBean.BrandsBean> getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getBrands();
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
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {

        GroupViewHolder groupViewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_infomation_material_brand, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvMaterailBrand = (TextView) convertView.findViewById(R.id.tv_materailBrand);
            groupViewHolder.btn_expand = (ImageView) convertView.findViewById(R.id.btn_expand);
            groupViewHolder.gv_pics = (MyGridView) convertView.findViewById(R.id.gv_pics);
            groupViewHolder.ll_pics = (LinearLayout) convertView.findViewById(R.id.ll_pics);
            convertView.setTag(groupViewHolder);
        }


        groupViewHolder = (GroupViewHolder) convertView.getTag();
        groupViewHolder.btn_expand.setVisibility(isEmpty(data.get(groupPosition).getBrands()) ? View.INVISIBLE : View.VISIBLE);
        groupViewHolder.tvMaterailBrand.setText(getGroup(groupPosition).getName() + "  " + MoneyFormatUtils.fromatWan(data.get(groupPosition).getCostInfo().getCost()) + "万");
        groupViewHolder.btn_expand.setImageResource(isExpanded ? R.mipmap.case_data_close_ic : R.mipmap.case_data_more_ic);
        final GroupViewHolder finalGroupViewHolder = groupViewHolder;
//        groupViewHolder.gv_pics.setVisibility(View.GONE);
//        groupViewHolder.ll_pics.setVisibility(ListUtil.isEmpty(data.get(groupPosition).getBrands()) ? View.GONE : View.VISIBLE);
        groupViewHolder.gv_pics.setVisibility(isEmpty(data.get(groupPosition).getBrands()) ? View.GONE : View.VISIBLE);
        groupViewHolder.ll_pics.setVisibility(View.GONE);
        if (groupVisibleMap.get(groupPosition) != null) {
            boolean groupVisible = groupVisibleMap.get(groupPosition);
            groupViewHolder.gv_pics.setVisibility(groupVisible ? View.VISIBLE : View.GONE);
//            groupViewHolder.gv_pics.setVisibility(groupVisible ? View.VISIBLE : View.GONE);
        }


        if (!isEmpty(data.get(groupPosition).getBrands())) {

            ArrayList<CaseInfomationBean.CostBrandsBean.BrandsBean> brands = new ArrayList<>();

            if (data.get(groupPosition).getBrands().size() > 3) {
                brands.addAll(data.get(groupPosition).getBrands().subList(0, 3));
            } else {
                brands.addAll(data.get(groupPosition).getBrands());
            }
            groupViewHolder.gv_pics.setAdapter(createBrandThumbAdapter(convertView.getContext(), brands));
            //    initTumber(convertView.getContext(), groupViewHolder.ll_pics, brands);

        }
        groupViewHolder.btn_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finalGroupViewHolder.btn_expand.setImageResource(isExpanded ? R.mipmap.case_data_close_ic : R.mipmap.case_data_more_ic);

                if (isExpanded) {
                    expandableListView.collapseGroup(groupPosition);
                } else {
                    expandableListView.expandGroup(groupPosition);
                }
                groupVisibleMap.put(groupPosition, isExpanded);
                notifyDataSetChanged();

            }
        });
        return convertView;
    }

    private void initTumber(Context context, LinearLayout linearLayout, List<CaseInfomationBean.CostBrandsBean.BrandsBean> brands) {
        LinearLayout ll;
        if (linearLayout.getChildCount() == 0)
            for (int i = 0; i < brands.size(); i++) {
                ll = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_infomation_brands_thumb, null);
                ImageView imageView = (ImageView) ll.findViewById(R.id.iv_thumb);
                TextView textView = (TextView) ll.findViewById(R.id.tv_name);
                Glide.with(context).load(brands.get(i).getLogo()).into(imageView);
                textView.setText(brands.get(i).getBrandClasses().get(0).getSubTypeName());
                linearLayout.addView(ll);
            }

    }


    private CommonAdapter<CaseInfomationBean.CostBrandsBean.BrandsBean> createBrandThumbAdapter(final Context context, ArrayList<CaseInfomationBean.CostBrandsBean.BrandsBean> brands) {
        return new CommonAdapter<CaseInfomationBean.CostBrandsBean.BrandsBean>(context, brands, R.layout.item_infomation_brands_thumb) {
            @Override
            public void conver(ViewHolder helper, CaseInfomationBean.CostBrandsBean.BrandsBean item, int position) {
                helper
                        .setImageURL(R.id.iv_thumb, item.getLogo(), context)
                        .setText(R.id.tv_name, item.getBrandClasses().get(0).getSubTypeName());
            }
        };
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_infomation_material_brand_child, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.gv_brand = (MyGridView) convertView.findViewById(R.id.gv_brand);
            convertView.setTag(childViewHolder);
        }
        childViewHolder = (ChildViewHolder) convertView.getTag();
        childViewHolder.gv_brand.setAdapter(createBrandPicAdapter(parent.getContext(), getChild(groupPosition, childPosition)));

        return convertView;
    }


    private CommonAdapter<CaseInfomationBean.CostBrandsBean.BrandsBean> createBrandPicAdapter(final Context context, List<CaseInfomationBean.CostBrandsBean.BrandsBean> brandsBeen) {
        return new CommonAdapter<CaseInfomationBean.CostBrandsBean.BrandsBean>(context, (ArrayList<CaseInfomationBean.CostBrandsBean.BrandsBean>) brandsBeen, R.layout.item_infomation_material_brand_pic) {
            @Override
            public void conver(ViewHolder helper, final CaseInfomationBean.CostBrandsBean.BrandsBean item, int position) {

                helper
                        .setText(R.id.tv_brandName, item.getBrandClasses().get(0).getSubTypeName())
                        .setImageURL(R.id.iv_brandIcon, item.getLogo() + "&imageView2/2/w/256", context)
                        .setOnClickListener(R.id.layout_item, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                WebViewManager.INSTANCE.startHasTitle(context,item.getWebsite(), null);
                            }
                        });

            }
        };
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private <T> boolean isEmpty(List<T> t) {
        if (t == null)
            return true;
        if (t.size() == 0)
            return true;
        return false;
    }

    class GroupViewHolder {
        TextView tvMaterailBrand;
        ImageView btn_expand;
        MyGridView gv_pics;
        LinearLayout ll_pics;
    }


    class ChildViewHolder {
        MyGridView gv_brand;
    }
}
