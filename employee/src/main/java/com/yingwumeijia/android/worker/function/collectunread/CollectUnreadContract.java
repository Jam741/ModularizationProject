package com.yingwumeijia.android.worker.function.collectunread;



import com.yingwumeijia.baseywmj.base.JBasePresenter;
import com.yingwumeijia.baseywmj.base.JBaseView;

import java.util.List;

/**
 * Created by Jam on 2017/3/25 下午11:13.
 * Describe:
 */

public interface CollectUnreadContract {

    interface View extends JBaseView {

        void onResponse(List list);

        void loadCompleted(int page, boolean empty);

        void showEmptyLayout(boolean empty);
    }


    interface Presenter extends JBasePresenter {

        void loadData(int pageNum);
    }
}

