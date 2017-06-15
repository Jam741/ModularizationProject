package com.yingwumeijia.commonlibrary.net.subscriber;


import android.content.Context;
import android.widget.Toast;


import com.kaopiz.kprogresshud.KProgressHUD;
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
        e.printStackTrace();
        String message;
        if (NetUtils.isConnected(context)) {
            message = "网络异常";
        } else if (e instanceof ApiException) {
            message = ((ApiException) e).getError_message();
        } else if (e instanceof ConnectException) {
            message = "网络连接异常，请重试";
        } else if (e instanceof SocketTimeoutException) {
            message = "网络请求超时，请重试";
        } else {
            message = "网络异常";
        }
        e.printStackTrace();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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
