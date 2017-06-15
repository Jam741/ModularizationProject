package com.yingwumeijia.commonlibrary.net;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yingwumeijia.commonlibrary.base.ActivityLifeCycleEvent;
import com.yingwumeijia.commonlibrary.net.cache.RetrofitCache;
import com.yingwumeijia.commonlibrary.net.exception.ApiException;
import com.yingwumeijia.commonlibrary.net.helper.RxHelper;
import com.yingwumeijia.commonlibrary.net.subscriber.ProgressSubscriber;
import com.yingwumeijia.commonlibrary.net.subscriber.SimpleSubscriber;

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


    public <T> void toSimpleSubscribe(Observable<T> ob, SimpleSubscriber<T> subscriber, final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        Observable.Transformer<Object, Object> result = RxHelper.handleResult(event, lifecycleSubject);
        ob.compose(result);
        RetrofitCache.load("nocacheKey", ob, false, false).subscribe(subscriber);
    }

    public <T> void toSimpleSubscribe(Observable<T> ob, SimpleSubscriber<T> subscriber, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        Observable.Transformer<T, T> result = RxHelper.handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
        Observable<T> observable = ob.compose(result);
        RetrofitCache.load("nocacheKey", observable, false, false).subscribe(subscriber);
    }

    public <T> void toSimpleSubscribe(Observable<T> ob, Subscriber<T> subscriber, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        Observable.Transformer<T, T> result = RxHelper.handleResult(ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
        Observable<T> observable = ob.compose(result);
        RetrofitCache.load("nocacheKey", observable, false, false).subscribe(subscriber);
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
        RetrofitCache.load("nocacheKey", observable, false, false).subscribe(subscriber);
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

    public static <T> Subscriber<T> newSubscriber(final Context context, final Action1<T> onNext) {

        final Subscriber<T> subscriber = new Subscriber<T>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

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
            public void onNext(T t) {
                if (!isUnsubscribed()) {
                    onNext.call(t);
                }
            }
        };
        return subscriber;
    }

}
