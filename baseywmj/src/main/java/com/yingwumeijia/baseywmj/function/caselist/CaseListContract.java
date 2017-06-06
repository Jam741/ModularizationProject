package com.yingwumeijia.baseywmj.function.caselist;

import com.yingwumeijia.baseywmj.base.JBasePresenter;
import com.yingwumeijia.baseywmj.base.JBaseView;

import java.util.List;

/**
 * Created by jamisonline on 2017/6/4.
 */

public interface CaseListContract {


    interface View extends JBaseView {

        void onResponseList(List list);

        void showNavLayoutBar(boolean show);

        void showGoTopBotton(boolean show);

    }


    interface Presenter extends JBasePresenter {

        void loadCaseList(int page);

    }


}
