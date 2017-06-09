package com.yingwumeijia.commonlibrary.net.subscriber;


import android.content.Context;


import com.kaopiz.kprogresshud.KProgressHUD;
import com.yingwumeijia.commonlibrary.net.exception.ApiException;
import com.yingwumeijia.commonlibrary.widget.ProgressCancelListener;

import rx.Subscriber;

/**
 * Created by helin on 2016/10/10 15:49.
 */

public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {


    private KProgressHUD dialogHandler;

    public ProgressSubscriber(Context context) {
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
        if (false) { //这里自行替换判断网络的代码
            _onError("网络不可用");
        } else if (e instanceof ApiException) {
            _onError(e.getMessage());
        } else {
            _onError("请求失败，请稍后再试...");
        }
    }


    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);
}
