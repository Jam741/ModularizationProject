package com.yingwumeijia.android.ywmj.client.function.enter;

import com.yingwumeijia.baseywmj.base.JBasePresenter;
import com.yingwumeijia.baseywmj.base.JBaseView;
import com.yingwumeijia.baseywmj.entity.bean.ApplyJoinBean;

/**
 * Created by Jam on 2017/2/24 上午11:41.
 * Describe:
 */

public interface EditEnterContract {


    interface View extends JBaseView {

        void setSendSmsCodeText(String s);

        void lockSendSmsButton();

        void unLockSendSmsButton();

        void joinSuccess();
    }

    interface Presenter extends JBasePresenter {


        void sendSmsCode(String phoneNum);

        void commit(ApplyJoinBean applyJoinBean);

    }
}
