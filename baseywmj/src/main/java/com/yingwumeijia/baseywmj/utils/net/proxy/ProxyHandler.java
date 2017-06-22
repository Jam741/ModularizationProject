package com.yingwumeijia.baseywmj.utils.net.proxy;

import android.util.Log;

import com.yingwumeijia.baseywmj.utils.net.RetrofitUtil;
import com.yingwumeijia.commonlibrary.entity.UserSession;
import com.yingwumeijia.commonlibrary.net.AccountManager;
import com.yingwumeijia.commonlibrary.net.IGlobalManager;
import com.yingwumeijia.commonlibrary.net.exception.NotLoginThrowable;
import com.yingwumeijia.commonlibrary.net.exception.TokenInvalidException;
import com.yingwumeijia.commonlibrary.net.exception.TokenIsRefreshEdException;
import com.yingwumeijia.commonlibrary.net.exception.TokenIsTimeMatterException;
import com.yingwumeijia.commonlibrary.net.exception.TokenNotExistException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Jam on 2017/2/17 下午5:41.
 * Describe:
 */

public class ProxyHandler implements InvocationHandler {

    private final static String TAG = "Token_Proxy";

    private final static int REFRESH_TOKEN_VALID_TIME = 30;
    private static long tokenChangedTime = 0;
    private Throwable mRefreshTokenError = null;
    private boolean isTokenNeedRefresh;

    private Object mProxyObject;
    private IGlobalManager mGlobalManager;

    public ProxyHandler(Object mProxyObject, IGlobalManager mGlobalManager) {
        this.mProxyObject = mProxyObject;
        this.mGlobalManager = mGlobalManager;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        return Observable.just(null).flatMap(new Func1<Object, Observable<?>>() {
            @Override
            public Observable<?> call(Object o) {
                try {
                    if (isTokenNeedRefresh) {
                        //可以更新参数  updateMethodToken(method, args);
                    }
                    //首次请求
                    return (Observable<?>) method.invoke(mProxyObject, args);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            }
        })
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                            @Override
                            public Observable<?> call(Throwable throwable) {
                                if (throwable instanceof TokenInvalidException) {
                                    return refreshTokenWhenInvalid();
                                } else if (throwable instanceof TokenIsRefreshEdException) {
                                    return Observable.just(true);
                                } else if (throwable instanceof TokenNotExistException) {
                                    //Token 不存在，执行退出登录的操作。（为了防止多个请求，都出现 Token 不存在的问题，
                                    // 这里需要取消当前所有的网络请求）
                                    mGlobalManager.exitLogin();

                                    NotLoginThrowable notLoginThrowable = new NotLoginThrowable("not-login");
                                    return Observable.error(notLoginThrowable);
                                } else if (throwable instanceof TokenIsTimeMatterException) {
                                    Observable.just(false);
                                }
                                return Observable.error(throwable);
                            }
                        });
                    }
                });
    }

    /**
     * Refresh the token when the current token is invalid.
     *
     * @return
     */
    private Observable<?> refreshTokenWhenInvalid() {
        synchronized (ProxyHandler.class) {
            if (new Date().getTime() - tokenChangedTime < REFRESH_TOKEN_VALID_TIME) {
                isTokenNeedRefresh = true;
                return Observable.just(true);
            } else {
                //call the refresh token api
                RetrofitUtil.Companion.getInstacne().
                        getCommonService()
                        .refreshToken(AccountManager.INSTANCE.sessionId(), AccountManager.INSTANCE.refreshToken())
                        .subscribe(new Subscriber<UserSession>() {

                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                mRefreshTokenError = e;
                            }

                            @Override
                            public void onNext(UserSession userSession) {
                                if (userSession != null) {
                                    isTokenNeedRefresh = true;
                                    tokenChangedTime = new Date().getTime();
                                    AccountManager.INSTANCE.refreshAccount(userSession);
                                    Log.d(TAG, "Refresh token success, time = " + tokenChangedTime);
                                }
                            }
                        });

                if (mRefreshTokenError != null) {
                    return Observable.just(false);
                } else {
                    return Observable.just(true);
                }

            }
        }
    }
}
