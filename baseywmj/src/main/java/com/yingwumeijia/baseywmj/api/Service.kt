package com.yingwumeijia.baseywmj.api

import com.yingwumeijia.baseywmj.entity.UserSession
import com.yingwumeijia.baseywmj.entity.bean.*
import com.yingwumeijia.baseywmj.function.casedetails.material.CaseInfomationBean
import com.yingwumeijia.baseywmj.function.casedetails.model.CaseDetailBannerBean
import com.yingwumeijia.baseywmj.function.casedetails.model.CreateSessionBean
import com.yingwumeijia.baseywmj.function.casedetails.realscene.RealSceneBean
import com.yingwumeijia.baseywmj.function.casedetails.team.ProductionTeamBean
import com.yingwumeijia.baseywmj.function.caselist.CaseFilterOptionBody
import com.yingwumeijia.baseywmj.function.collect.cases.CollectCaseBean
import com.yingwumeijia.baseywmj.function.collect.company.CollectCompanyBean
import com.yingwumeijia.baseywmj.function.collect.employee.CollectEmployeeBean
import com.yingwumeijia.baseywmj.function.introduction.company.CompanyIntriductionBean
import com.yingwumeijia.baseywmj.function.introduction.company.resume.CompanyResumeBean
import com.yingwumeijia.baseywmj.function.introduction.employee.EmployeeIntroductionBean
import com.yingwumeijia.baseywmj.function.introduction.serviceStandard.ServiceStandardBean
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
     * 退出登录

     * @return
     */
    @POST("user/logout")
    fun setLogout(): Observable<String>

    /**
     * 刷新用户登录Token

     * @param sessionId
     * *
     * @param refreshToken
     * *
     * @return
     */
    @POST("user/refreshToken")
    fun refreshToken(@Query("sessionId") sessionId: String,
                     @Query("refreshToken") refreshToken: String): Observable<UserSession>


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


    /**
     * 收藏

     * @param id
     * *
     * @param collectionType
     * *
     * @return
     */
    @POST("collection")
    fun collect(@Query("id") id: Int,
                @Query("collectionType") collectionType: String): Observable<String>

    /**
     * 删除收藏
     *
     * @param id
     * *
     * @param collectionType
     * *
     * @return
     */
    @POST("collection/cancellation")
    fun cancelCollect(@Query("id") id: Int,
                      @Query("collectionType") collectionType: String): Observable<String>


    /**
     * 用户收藏作品列表

     * @param pageNum
     * *
     * @param pageSize
     * *
     * @return
     */
    @GET("collection/case")
    fun getCollectCaseData(@Query("pageNum") pageNum: Int,
                           @Query("pageSize") pageSize: Int): Observable<CollectCaseBean>

    /**
     * 用户收藏业者列表

     * @param pageNum
     * *
     * @param pageSize
     * *
     * @return
     */
    @GET("collection/employee")
    fun getCollectEmployeeData(@Query("pageNum") pageNum: Int,
                               @Query("pageSize") pageSize: Int): Observable<CollectEmployeeBean>

    /**
     * 用户收藏公司列表

     * @param pageNum
     * *
     * @param pageSize
     * *
     * @return
     */
    @GET("collection/company")
    fun getCollectCompanyData(@Query("pageNum") pageNum: Int,
                              @Query("pageSize") pageSize: Int): Observable<CollectCompanyBean>


    /**
     * 获取作品详情横幅信息

     * @param caseId
     * *
     * @return
     */
    @GET("case/app/detail/banner/{caseId}")
    fun getCaseDetailBannerBean_C(@Path("caseId") caseId: Int): Observable<CaseDetailBannerBean>

    /**
     * 获取作品详情横幅信息
     *
     * @param caseId
     * *
     * @return
     */
    @GET("case/employee/detail/banner/{caseId}")
    fun getCaseDetailBannerBean_E(@Path("caseId") caseId: Int): Observable<CaseDetailBannerBean>


    /**
     * 创建会话
     *
     * @return
     */
    @POST("im/session")
    fun createSession(@Query("caseId") caseId: Int): Observable<CreateSessionBean>


    /**
     * 获取现场实景页面模块数据

     * @param caseId
     * *
     * @return
     */
    @GET("case/app/detail/scene/{caseId}")
    fun getRealSceneBean_C(@Path("caseId") caseId: Int): Observable<RealSceneBean>


    /**
     * 获取现场实景页面模块数据

     * @param caseId
     * *
     * @return
     */
    @GET("case/employee/detail/scene/{caseId}")
    fun getRealSceneBean_E(@Path("caseId") caseId: Int): Observable<RealSceneBean>


    /**
     * 获取原创团队模块数据
     *
     * @param caseId
     * *
     * @return
     */
    @GET("case/app/detail/team/{caseId}")
    fun getProductionTeamData_C(@Path("caseId") caseId: Int): Observable<ProductionTeamBean>

    /**
     * 获取原创团队模块数据
     *
     * @param caseId
     * *
     * @return
     */
    @GET("case/employee/detail/team/{caseId}")
    fun getProductionTeamData_E(@Path("caseId") caseId: Int): Observable<ProductionTeamBean>


    /**
     * 获取作品详细资料模块数据

     * @param casId
     * *
     * @return
     */
    @GET("case/app/materials/{caseId}")
    fun getCaseDetailInfomation_C(@Path("caseId") casId: Int): Observable<CaseInfomationBean>

    /**
     * 获取作品详细资料模块数据

     * @param casId
     * *
     * @return
     */
    @GET("case/employee/materials/{caseId}")
    fun getCaseDetailInfomation_E(@Path("caseId") casId: Int): Observable<CaseInfomationBean>


    /**
     * 获取公司简介数据

     * @param companyId
     * *
     * @return
     */
    @GET("case/app/resume/company/{companyId}/{caseId}")
    fun getCompanyIntrductionData_C(@Path("companyId") companyId: Int,
                                    @Path("caseId") caseId: Int): Observable<CompanyIntriductionBean>

    /**
     * 获取公司简介数据

     * @param companyId
     * *
     * @return
     */
    @GET("case/employee/resume/company/{companyId}/{caseId}")
    fun getCompanyIntrductionData_E(@Path("companyId") companyId: Int,
                                    @Path("caseId") caseId: Int): Observable<CompanyIntriductionBean>


    /**
     * 获取公司其他作品作品

     * @param companyId
     * *
     * @param pageNum
     * *
     * @param pageSize
     * *
     * @return
     */
    @GET("case/app/detail/company/works/{companyId}/{pageNum}/{pageSize}")
    fun getCompanyOtherCaseData_C(@Path("companyId") companyId: Int,
                                  @Path("pageNum") pageNum: Int,
                                  @Path("pageSize") pageSize: Int): Observable<List<CaseBean>>

    /**
     * 获取公司其他作品作品

     * @param companyId
     * *
     * @param pageNum
     * *
     * @param pageSize
     * *
     * @return
     */
    @GET("case/employee/detail/company/works/{companyId}/{pageNum}/{pageSize}")
    fun getCompanyOtherCaseData_E(@Path("companyId") companyId: Int,
                                  @Path("pageNum") pageNum: Int,
                                  @Path("pageSize") pageSize: Int): Observable<List<CaseBean>>


    /**
     * 获取公司展示数据

     * @param companyId
     * *
     * @return
     */
    @GET("case/app/present/company/{companyId}")
    fun getCompanyResumeData_C(@Path("companyId") companyId: Int): Observable<CompanyResumeBean>

    /**
     * 获取公司展示数据

     * @param companyId
     * *
     * @return
     */
    @GET("case/employee/present/company/{companyId}")
    fun getCompanyResumeData_E(@Path("companyId") companyId: Int): Observable<CompanyResumeBean>


    /**
     * 获取业者简介数据

     * @param userId
     * *
     * @param caseId
     * *
     * @return
     */
    @GET("case/app/resume/employee/{userId}/{caseId}")
    fun getEmployeeIntroductionData_C(@Path("userId") userId: Int,
                                      @Path("caseId") caseId: Int): Observable<EmployeeIntroductionBean>

    /**
     * 获取业者简介数据

     * @param userId
     * *
     * @param caseId
     * *
     * @return
     */
    @GET("case/employee/resume/employee/{userId}/{caseId}")
    fun getEmployeeIntroductionData_E(@Path("userId") userId: Int,
                                      @Path("caseId") caseId: Int): Observable<EmployeeIntroductionBean>


    /**
     * 获取业者其他作品作品

     * @param userId
     * *
     * @param pageNum
     * *
     * @param pageSize
     * *
     * @return
     */
    @GET("case/app/detail/employee/works/{userId}/{pageNum}/{pageSize}")
    fun getEmployeeOtherCaseData_C(@Path("userId") userId: Int,
                                   @Path("pageNum") pageNum: Int,
                                   @Path("pageSize") pageSize: Int): Observable<List<CaseBean>>


    /**
     * 获取业者其他作品作品

     * @param userId
     * *
     * @param pageNum
     * *
     * @param pageSize
     * *
     * @return
     */
    @GET("case/employee/detail/employee/works/{userId}/{pageNum}/{pageSize}")
    fun getEmployeeOtherCaseData_E(@Path("userId") userId: Int,
                                   @Path("pageNum") pageNum: Int,
                                   @Path("pageSize") pageSize: Int): Observable<List<CaseBean>>


    /**
     * 查询服务标准详细信息

     * @param id          公司id或用户ID
     * *
     * @param sourceType  id类型（1：公司id，2：业者用户id）
     * *
     * @param serviceType 服务标准类型（1：硬装设计，2：软装设计，3：硬装施工，4：软装施工）
     * *
     * @return
     */
    @GET("case/app/service")
    fun getServiceStandardData_C(@Query("id") id: Int,
                                 @Query("sourceType") sourceType: Int,
                                 @Query("serviceType") serviceType: Int): Observable<ServiceStandardBean>


    /**
     * 查询服务标准详细信息

     * @param id          公司id或用户ID
     * *
     * @param sourceType  id类型（1：公司id，2：业者用户id）
     * *
     * @param serviceType 服务标准类型（1：硬装设计，2：软装设计，3：硬装施工，4：软装施工）
     * *
     * @return
     */
    @GET("case/employee/service")
    fun getServiceStandardData_E(@Query("id") id: Int,
                                 @Query("sourceType") sourceType: Int,
                                 @Query("serviceType") serviceType: Int): Observable<ServiceStandardBean>


}