package com.yingwumeijia.baseywmj.function.collect;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.muzhi.mtools.utils.ScreenUtils;
import com.yingwumeijia.baseywmj.R;
import com.yingwumeijia.baseywmj.api.Api;
import com.yingwumeijia.baseywmj.base.JBaseFragment;
import com.yingwumeijia.baseywmj.function.UserManager;
import com.yingwumeijia.baseywmj.function.casedetails.CaseDetailActivity;
import com.yingwumeijia.baseywmj.function.collect.cases.CollectCaseBean;
import com.yingwumeijia.baseywmj.option.Config;
import com.yingwumeijia.baseywmj.utils.net.HttpUtil;
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber;
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.CommonRecyclerAdapter;
import com.yingwumeijia.commonlibrary.utils.adapter.recyclerview.RecyclerViewHolder;
import com.yingwumeijia.commonlibrary.widget.recycler.LoadingMoreFooter;
import com.yingwumeijia.commonlibrary.widget.recycler.XRecyclerView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by Jam on 2016/6/8 17:29.
 * Describe:
 */
public class CollectOldFragment extends JBaseFragment {


    XRecyclerView rvCollect;
    LinearLayout emptyLayout;
    private int pageNum = 1;

    private CommonRecyclerAdapter<CollectCaseBean.ResultBean> mAdapter;
    private AlertDialog.Builder dialog;

    public static CollectOldFragment newInstance() {

        Bundle args = new Bundle();

        CollectOldFragment fragment = new CollectOldFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collect, container, false);
        rvCollect = (XRecyclerView) view.findViewById(R.id.rv_collect);
        emptyLayout = (LinearLayout) view.findViewById(R.id.empty_layout);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (UserManager.INSTANCE.isLogin(getActivity())) {
            initData();
            initView();
        } else {
            showEmptyLayout(true);
        }
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    private void initView() {
        rvCollect.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvCollect.setAdapter(mAdapter);
        rvCollect.setLoadingListener(loadingListener);
        rvCollect.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).margin(R.dimen.vertical_margin).build());
        rvCollect.addFootView(new LoadingMoreFooter(getActivity(), "没有更多了..."));

    }

    private void initData() {
        createAdapter();
        getCollectList(true);
    }

    /**
     * 创建收藏列表适配器
     */
    private void createAdapter() {
        mAdapter = new CommonRecyclerAdapter<CollectCaseBean.ResultBean>(null, context, null, R.layout.item_collect_list) {
            @Override
            public void convert(RecyclerViewHolder holder, final CollectCaseBean.ResultBean caseBean, final int position) {
                holder.setTextWith(R.id.tv_name, caseBean.getCaseName());
                holder.setImageUrl480(context, R.id.iv_case, caseBean.getCaseCover());
                holder.setSize(R.id.item_layout, (ScreenUtils.getScreenWidth(getActivity()) - 10) / 2, ((ScreenUtils.getScreenWidth(getActivity()) - 10) / 2) * 680 / 720)
                        .setOnClickListener(R.id.iv_cancel_collection, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                currentCaseId = caseBean.getCaseId();
                                currentPositon = position;
                                showCancelDialog();
                            }
                        })
                        .setOnItemClickListener(new RecyclerViewHolder.OnItemCliceListener() {
                            @Override
                            public void itemClick(View itemView, int position) {
                                CaseDetailActivity.Companion.start(getActivity(),caseBean.getCaseId(),false);
                            }
                        });
            }
        };
    }

    private int currentPositon;
    private int currentCaseId;

    private void showCancelDialog() {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(getActivity())
                    .setMessage("确定取消收藏?")
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelCollect();
                        }
                    });
        }
        dialog.show();
    }


    /**
     * 取消收藏
     */
    private void cancelCollect() {
        HttpUtil.getInstance().toNolifeSubscribe(Api.Companion.getService().cancelCollect(currentCaseId, CollectType.INSTANCE.getCASE()), new SimpleSubscriber<String>(getActivity()) {
            @Override
            protected void _onNext(@org.jetbrains.annotations.Nullable String s) {
                mAdapter.remove(currentPositon);
                checkCllectAdapterNotNull();
                pageNum = (mAdapter.getItemCount()) / 10;
                toastWith("取消收藏成功");
            }
        });
    }



    private void checkCllectAdapterNotNull() {
        if (mAdapter.getItemCount() == 0) {
            pageNum = 1;
            getCollectList(false);
        }
    }

    private void checkAdapterNotNull() {
        if (mAdapter == null) {
            showEmptyLayout(true);
            return;
        }
        if (mAdapter.getItemCount() == 0) {
            showEmptyLayout(true);
        } else {
            showEmptyLayout(false);
        }
    }

    /**
     * 获取收藏数据
     *
     * @param isRef
     */
    private void getCollectList(final boolean isRef) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.Companion.getService().getCollectCaseData(pageNum, Config.INSTANCE.getSize()), new SimpleSubscriber<CollectCaseBean>(getActivity()) {
            @Override
            protected void _onNext(@org.jetbrains.annotations.Nullable CollectCaseBean collectCaseBean) {
                if (collectCaseBean.getResult() == null) {
                    checkAdapterNotNull();
                    if (isRef) {
                        rvCollect.refreshComplete();
                        showEmptyLayout(true);
                    } else {
                        rvCollect.loadMoreComplete();
//                                    rvCollect.setIsnomore(true);
                        rvCollect.noMoreLoading();
                    }
                } else {
                    if (isRef) {
                        rvCollect.refreshComplete();
                        rvCollect.setIsnomore(false);
                        if (collectCaseBean.getResult() != null && mAdapter != null)
                            mAdapter.refresh((ArrayList<CollectCaseBean.ResultBean>) collectCaseBean.getResult());
                    } else {
                        rvCollect.loadMoreComplete();
                        if (collectCaseBean.getResult() != null && mAdapter != null)
                            mAdapter.addRange((ArrayList<CollectCaseBean.ResultBean>) removeRepeta(collectCaseBean.getResult()));
                    }
                    checkAdapterNotNull();
                }
            }
        });
    }

    /**
     * 收藏数据去重
     *
     * @param data
     * @return
     */
    private List<CollectCaseBean.ResultBean> removeRepeta(List<CollectCaseBean.ResultBean> data) {
        List<CollectCaseBean.ResultBean> adapterList = mAdapter.getData();
        List<CollectCaseBean.ResultBean> newList = data;
        List<CollectCaseBean.ResultBean> deleList = new ArrayList<>();
        deleList.clear();
        for (CollectCaseBean.ResultBean newCaseBean : data) {
            for (CollectCaseBean.ResultBean oldCaseBean : adapterList) {
                if (newCaseBean.getCaseId() == oldCaseBean.getCaseId())
                    deleList.add(newCaseBean);
            }
        }
        newList.removeAll(deleList);
        return data;
    }


    XRecyclerView.LoadingListener loadingListener = new XRecyclerView.LoadingListener() {
        @Override
        public void onRefresh() {
            pageNum = 1;
            getCollectList(true);
        }

        @Override
        public void onLoadMore() {
            pageNum++;
            getCollectList(false);
        }
    };


    public void reFreshData() {
        pageNum = 1;
        getCollectList(true);
    }


    /**
     * 是否显示空
     *
     * @param isEmpty
     */
    public void showEmptyLayout(boolean isEmpty) {
        if (isEmpty) {
            emptyLayout.setVisibility(View.VISIBLE);
            rvCollect.setVisibility(View.GONE);
        } else {
            emptyLayout.setVisibility(View.GONE);
            rvCollect.setVisibility(View.VISIBLE);
        }
    }


    public interface OnCollectionCancelListener {
        void canceled(boolean isCancel);
    }
}
