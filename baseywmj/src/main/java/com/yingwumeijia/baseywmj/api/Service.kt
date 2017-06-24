package com.yingwumeijia.baseywmj.api

import com.yingwumeijia.baseywmj.entity.bean.*
import com.yingwumeijia.baseywmj.function.caselist.CaseFilterOptionBody
import com.yingwumeijia.baseywmj.function.user.LoginBean
import retrofit2.http.*
import rx.Observable

/**
 * Created by jamisonline on 2017/6/8.
 */
interface Service {


    /**
     * 获取BASE URL
     */
    @GET("serviceQuery/appServer")
    fun getService(@Query("appType") appType: String,
                   @Query("version") version: String): Observable<SeverBean>


    /**
     * 登陆

     * @return
     */
    @POST("user/login")
    fun login(@Body loginBean: LoginBean): Observable<UserBean>


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


    /**
     * 获取用户详细信息接口

     * @return
     */
    @GET("customer/getCustomerDetail")
    fun getCustomerDetail_C(): Observable<CustomerDetailBean>


    @GET("employee/getEmployeeDetail")
    fun getCustomerDetail_E(): Observable<CustomerDetailBean>


    /**
     * 作品筛选枚举集合

     * @return
     */
    @GET("case/app/types")
    fun getCaseTypeSet(): Observable<CaseTypeSetBean>


    /**
     * 获取融云token

     * @return
     */
    @GET("im/getToken")
    abstract fun getIMToken(): Observable<TokenBean>


    /**
     * 注册

     * @return
     */
    @POST("user/register")
    fun register(@Body loginBean: LoginBean): Observable<RegisterResultBean>


    /**
     * 确认开通

     * @return
     */
    @POST("user/confirm")
    fun confirm(@Body loginBean: LoginBean): Observable<UserBean>


    /**
     * 发送验证码

     * @param phone
     * *
     * @param source 1-注册，2-找回
     * *
     * @return
     */
    @POST("user/sendSmsCode")
    fun sendSmsCode(@Query("phone") phone: String,
                    @Query("source") source: Int): Observable<String>


    /**
     * 找回密码

     * @return
     */
    @PUT("user/getBackPassword")
    fun findPassword(@Body loginBean: LoginBean): Observable<UserBean>


    /**
     * 密码设置

     * @param oldPassword
     * *
     * @param newPassword
     * *
     * @return
     */
    @PUT("user/setPassword")
    fun setPassword(@Query("oldPassword") oldPassword: String,
                    @Query("newPassword") newPassword: String): Observable<String>

    /**
     * 获取搜索热词列表

     * @return
     */
    @GET("case/app/hotkeys")
    fun getHotKeys(): Observable<List<String>>

}