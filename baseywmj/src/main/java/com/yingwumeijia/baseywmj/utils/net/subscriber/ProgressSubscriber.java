package com.yingwumeijia.baseywmj.utils.net.subscriber;


import android.content.Context;


import com.kaopiz.kprogresshud.KProgressHUD;
import com.yingwumeijia.baseywmj.utils.net.HttpUtil;
import com.yingwumeijia.commonlibrary.widget.ProgressCancelListener;

import rx.Subscriber;

/**
 * Created by helin on 2016/10/10 15:49.
 */

public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    Context context;

    private KProgressHUD dialogHandler;

    public ProgressSubscriber(Context context) {
        this.context = context;
        dialogHandler = KProgressHUD.create(context);
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }


    /**
     * 显示Dialog
     */
    public void showProgressDialog() {
        if (dialogHandler != null) {
//            dialogHandler.obtainMessage(SimpleLoadDialog.SHOW_PROGRESS_DIALOG).sendToTarget();
            dialogHandler.show();
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    /**
     * 隐藏Dialog
     */
    private void dismissProgressDialog() {
        if (dialogHandler != null) {
//            dialogHandler.obtainMessage(SimpleLoadDialog.DISMISS_PROGRESS_DIALOG).sendToTarget();
            dialogHandler.dismiss();
            dialogHandler = null;
        }
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        HttpUtil.disposeHttpException(context, e);
    }


    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    protected abstract void _onNext(T t);

//    protected abstract void _onError(String message);
}
