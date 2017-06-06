package com.yingwumeijia.commonlibrary.net;

import com.yingwumeijia.commonlibrary.base.ActivityLifeCycleEvent;
import com.yingwumeijia.commonlibrary.net.cache.RetrofitCache;
import com.yingwumeijia.commonlibrary.net.helper.RxHelper;
import com.yingwumeijia.commonlibrary.net.subscriber.ProgressSubscriber;

import rx.Observable;
import rx.functions.Action0;
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
     * @param ob
     * @param subscriber
     * @param cacheKey 缓存kay
     * @param event Activity 生命周期
     * @param lifecycleSubject
     * @param isSave 是否缓存
     * @param forceRefresh 是否强制刷新
     */
    public void toSubscribe(Observable ob, final ProgressSubscriber subscriber, String cacheKey, final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject, boolean isSave, boolean forceRefresh) {
        //数据预处理
        Observable.Transformer<ApiModel<Object>, Object> result = RxHelper.handleResult(event, lifecycleSubject);
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



}
