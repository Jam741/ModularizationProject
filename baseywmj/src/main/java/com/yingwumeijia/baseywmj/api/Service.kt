package com.yingwumeijia.baseywmj.api

import com.yingwumeijia.baseywmj.entity.bean.CaseBean
import com.yingwumeijia.baseywmj.entity.bean.SeverBean
import com.yingwumeijia.baseywmj.function.caselist.CaseFilterOptionBody
import retrofit2.http.*
import rx.Observable

/**
 * Created by jamisonline on 2017/6/8.
 */
interface Service {


    @GET("serviceQuery/appServer")
    fun getService(@Query("appType") appType: String,
                   @Query("version") version: String): Observable<SeverBean>

    /**
     * 首页作品列表

     * @param pageNum  页码
     * *
     * @param pageSize 每页有多少项
     * *
     * @return
     */
    @POST("case/app/list/{pageNum}/{pageSize}")
    fun getCaseList(@Path("pageNum") pageNum: Int,
                    @Path("pageSize") pageSize: Int,
                    @Body caseFilterOptionBody: CaseFilterOptionBody): Observable<List<CaseBean>>

}