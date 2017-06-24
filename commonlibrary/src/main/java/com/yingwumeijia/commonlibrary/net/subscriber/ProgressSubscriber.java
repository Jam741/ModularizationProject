package com.yingwumeijia.commonlibrary.net.subscriber;


import android.content.Context;
import android.widget.Toast;


import com.kaopiz.kprogresshud.KProgressHUD;
import com.yingwumeijia.commonlibrary.net.HttpUtil;
import com.yingwumeijia.commonlibrary.net.NetUtils;
import com.yingwumeijia.commonlibrary.net.exception.ApiException;
import com.yingwumeijia.commonlibrary.widget.ProgressCancelListener;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

/**
 * Created by helin on 2016/10/10 15:49.
 */

public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    Context context;

    private KProgressHUD dialogHandler;

    public ProgressSubscriber(Context context) {
        this.context = context;
        dialogHandler = KProgressHUD.create(context, KProgressHUD.Style.ANNULAR_DETERMINATE);
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
            ;
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
