package com.yingwumeijia.baseywmj.utils.net;

import android.content.Context;
import android.widget.Toast;

import com.yingwumeijia.baseywmj.AppTypeManager;
import com.yingwumeijia.baseywmj.function.user.login.LoginActivity;
import com.yingwumeijia.baseywmj.utils.net.exception.NotLoginThrowable;
import com.yingwumeijia.baseywmj.utils.net.subscriber.SimpleSubscriber;
import com.yingwumeijia.commonlibrary.base.ActivityLifeCycleEvent;
import com.yingwumeijia.baseywmj.utils.net.cache.RetrofitCache;
import com.yingwumeijia.baseywmj.utils.net.exception.ApiException;
import com.yingwumeijia.baseywmj.utils.net.helper.RxHelper;
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber;


import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by jamisonline on 2017/6/2.
 */

public class HttpUtil {


    private HttpUtil() {

    }


    /**
     * 在访问HttpMethods时创建单例
     */
    private static class SingletonHolder {
        private static final HttpUtil INSTANCE = new HttpUtil();
    }


    public static HttpUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }


    /**
     * 添加线程管理并订阅
     *
     * @param ob
     * @param subscriber
     * @param cacheKey         缓存kay
     * @param event            Activity 生命周期
     * @param lifecycleSubject
     * @param isSave           是否缓存
     * @param forceRefresh     是否强制刷新
     */
    public void toSubscribe(Observable ob, final ProgressSubscriber subscriber, String cacheKey, final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject, boolean isSave, boolean forceRefresh) {
        //数据预处理
        Observable.Transformer<Object, Object> result = RxHelper.handleResult(event, lifecycleSubject);
        Observable observable = ob.compose(result)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //显示Dialog和一些其他操作
                        subscriber.showProgressDialog();
                    }
                });
        RetrofitCache.load(cacheKey, observable, isSave, forceRefresh).subscribe(subscriber);
    }


    public <T> void toSimpleSubscribe(Observable<T> ob, SimpleSubscriber<T> subscriber, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        Observable.Transformer<T, T> result = RxHelper.handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
        Observable<T> observable = ob.compose(result);
        observable.subscribe(subscriber);
    }

    public <T> void toSimpleSubscribe(Observable<T> ob, SimpleSubscriber<T> subscriber) {
        Observable<T> observable = ob.compose(HttpUtil.<T>applySchedulers());
        observable.subscribe(subscriber);
    }

    public <T> void toSimpleSubscribe(Observable<T> ob, final ProgressSubscriber<T> subscriber, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject, final boolean showProgressDialog) {
        Observable.Transformer<T, T> result = RxHelper.handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
        Observable<T> observable = ob.compose(result).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                if (showProgressDialog)
                    subscriber.showProgressDialog();
            }
        });
    }


    public <T> void toNolifeSubscribe(Observable<T> ob, Subscriber<T> subscriber) {
        ob.compose(HttpUtil.<T>applySchedulers()).subscribe(subscriber);
    }

    public <T> void toNolifeSubscribe(Observable<T> ob, SimpleSubscriber<T> subscriber) {
        ob.compose(HttpUtil.<T>applySchedulers()).subscribe(subscriber);
    }


    public <T> void toNolifeSubscribe(Observable<T> ob, final ProgressSubscriber<T> subscriber) {
        ob.compose(HttpUtil.<T>applySchedulers()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                subscriber.showProgressDialog();
            }
        }).subscribe(subscriber);
    }

    public <T> void toNolifeSubscribe(Observable<T> ob, final ProgressSubscriber<T> subscriber, final boolean showProgressDialog) {
        ob.compose(HttpUtil.<T>applySchedulers()).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                if (showProgressDialog)
                    subscriber.showProgressDialog();
            }
        }).subscribe(subscriber);
    }

    /**
     * 网络请求统一的线程调度
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io());
            }
        };
    }

    public <T> Subscriber<T> newNoProgressSubscriber(final Context context, final Action1<T> onNext) {

        final Subscriber<T> subscriber = new Subscriber<T>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(T t) {
                if (!isUnsubscribed()) {
                    onNext.call(t);
                }
            }
        };
        return subscriber;
    }


    public static void disposeHttpException(Context context, Throwable e) {

        String message;
        if (!NetUtils.isConnected(context)) {
            message = "网络异常";
        } else if (e instanceof ApiException) {
            message = ((ApiException) e).getError_message();
        } else if (e instanceof ConnectException) {
            message = "网络连接异常，请重试";
        } else if (e instanceof SocketTimeoutException) {
            message = "网络请求超时，请重试";
        } else if (e instanceof NotLoginThrowable) {
            message = "请登录";
            LoginActivity.Companion.startCurrent(context);
        } else {
            message = "网络异常";
        }
        e.printStackTrace();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
