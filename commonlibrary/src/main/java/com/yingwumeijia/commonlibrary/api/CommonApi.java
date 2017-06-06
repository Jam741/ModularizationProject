package com.yingwumeijia.commonlibrary.api;

import com.yingwumeijia.commonlibrary.entity.UserSession;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jamisonline on 2017/5/25.
 */

public interface CommonApi {

    /**
     * 刷新用户登录Token
     *
     * @param sessionId
     * @param refreshToken
     * @return
     */
    @POST("user/refreshToken")
    Observable<UserSession> refreshToken(@Query("sessionId") String sessionId,
                                         @Query("refreshToken") String refreshToken);
}
