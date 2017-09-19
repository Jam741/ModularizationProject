package com.yingwumeijia.android.ywmj.client

//import com.tencent.smtt.sdk.QbSdk
import android.content.Context
import android.graphics.Color
import android.os.Environment
import android.text.TextUtils
import com.netease.nim.uikit.NimUIKit
import com.netease.nim.uikit.contact.core.util.ContactHelper
import com.netease.nim.uikit.custom.DefaultUserInfoProvider
import com.netease.nim.uikit.session.viewholder.MsgViewHolderThumbBase
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.SDKOptions
import com.netease.nimlib.sdk.StatusBarNotificationConfig
import com.netease.nimlib.sdk.auth.LoginInfo
import com.netease.nimlib.sdk.mixpush.NIMPushClient
import com.netease.nimlib.sdk.msg.MessageNotifierCustomization
import com.netease.nimlib.sdk.msg.model.IMMessage
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.taobao.sophix.PatchStatus
import com.taobao.sophix.SophixManager
import com.yingwumeijia.android.ywmj.client.function.splash.SplashActivity
import com.yingwumeijia.baseywmj.AppType
import com.yingwumeijia.baseywmj.base.JBaseApp
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.nimim.NIMIMCache
import com.yingwumeijia.baseywmj.nimim.UserPreferences
import com.yingwumeijia.baseywmj.nimim.provider.NimDemoLocationProvider
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.baseywmj.utils.net.AccountManager
import com.yingwumeijia.commonlibrary.utils.SystemUtil
import com.yingwumeijia.sharelibrary.ShareSDK


/**
 * Created by jamisonline on 2017/5/31.
 */
class MyApp : JBaseApp() {

    override fun appType(): AppType {
        return AppType.APP_C
    }

    override fun onCreate() {
        super.onCreate()


        //阿里热更新SDK初始化
        initHotfix()

        //Jam分享SDK初始化
        ShareSDK.init(applicationContext, "2293291411", "wxa57345f69f5a674d")

        //配置日志打印级别
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })


        NIMIMCache.setContext(this)
        // 注册小米推送，参数：小米推送证书名称（需要在云信管理后台配置）、appID 、appKey，该逻辑放在 NIMClient init 之前
        NIMPushClient.registerMiPush(this, "CustomerMIPHSU", Config.MIPUSH_C.APP_ID, Config.MIPUSH_C.APP_KEY)
        // 注册华为推送，参数：华为推送证书名称（需要在云信管理后台配置）
        NIMPushClient.registerHWPush(this, "CustomerHWPUSH")
        //网易云信 SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(this, getLoginInfo(), options())


        //如果 APP 包含远程 service，该 APP 的 Application 的 onCreate 会多次调用。因此，如果需要在 onCreate 中调用除 init 接口外的其他接口，应先判断当前所属进程
        if (inMainProcess(this)) {
            initUIKit()

        }


    }


    private fun initUIKit() {
        // 初始化，使用 uikit 默认的用户信息提供者
        NimUIKit.init(this)

        // 设置地理位置提供者。如果需要发送地理位置消息，该参数必须提供。如果不需要，可以忽略。
        NimUIKit.setLocationProvider(NimDemoLocationProvider())

        // 会话窗口的定制初始化。
//        SessionHelper.init()

        // 通讯录列表定制初始化
//        ContactHelper.init()

        // 添加自定义推送文案以及选项，请开发者在各端（Android、IOS、PC、Web）消息发送时保持一致，以免出现通知不一致的情况
        // NimUIKit.CustomPushContentProvider(new DemoPushContentProvider());

//        NimUIKit.setOnlineStateContentProvider(DemoOnlineStateContentProvider())
    }


    private fun getLoginInfo(): LoginInfo? {
        val loginInfo = UserManager.getNIMLoginInfo(this)
        if (loginInfo != null) {
            NIMIMCache.setAccount(loginInfo.account.toLowerCase())
            return loginInfo
        } else {
            return null
        }
    }

    private fun initHotfix() {
        var appVersion: String
        try {
            appVersion = this.packageManager.getPackageInfo(this.packageName, 0).versionName
        } catch (e: Exception) {
            appVersion = "1.0.0"
        }

        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(Config.HOTFIX_C.ALI_APP_ID, Config.HOTFIX_C.ALI_HOTFIX_AppSecret, Config.HOTFIX_C.ALI_HOTFIX_RSASECRET)
                .setAesKey(Config.HOTFIX_C.ALI_HOTFIX_AES_KEY)
                .setEnableDebug(true)
                .setPatchLoadStatusStub { mode, code, info, handlePatchVersion ->
                    // 补丁加载回调通知
                    if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                        // 表明补丁加载成功
                    } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                        // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                        // 建议: 用户可以监听进入后台事件, 然后应用自杀
                    } else if (code == PatchStatus.CODE_LOAD_FAIL) {
                        // 内部引擎异常, 推荐此时清空本地补丁, 防止失败补丁重复加载
                        SophixManager.getInstance().cleanPatches();
                    } else {
                        // 其它错误信息, 查看PatchStatus类说明
                    }
                }.initialize()

        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch()
    }


    fun inMainProcess(context: Context): Boolean {
        val packageName = context.packageName
        val processName = SystemUtil.getProcessName(context)
        return packageName == processName
    }


    // 如果返回值为 null，则全部使用默认参数。
    fun options(): SDKOptions {
        val options = SDKOptions()

        if (BuildConfig.DEBUG) {
            options.appKey = Config.NIM_IM.dubug_app_key
        } else {
            options.appKey = Config.NIM_IM.release_app_key
        }
        initStatusBarNotificationConfig(options)

        // 配置保存图片，文件，log等数据的目录
        options.sdkStorageRootPath = Config.localStoreFolderPath(this)

        // 配置数据库加密秘钥
        options.databaseEncryptKey = "BeautifulHome"

        // 配置是否需要预下载附件缩略图
        options.preloadAttach = true

        // 配置附件缩略图的尺寸大小，
        options.thumbnailSize = MsgViewHolderThumbBase.getImageMaxEdge()

        // 用户信息提供者
        options.userInfoProvider = DefaultUserInfoProvider(this)

        // 定制通知栏提醒文案（可选，如果不定制将采用SDK默认文案）
        options.messageNotifierCustomization = messageNotifierCustomization

        // 在线多端同步未读数
        options.sessionReadAck = true

        // 云信私有化配置项
//        configServerAddress(options)

        return options
    }


    private fun configServerAddress(options: SDKOptions) {
//        val appKey = PrivatizationConfig.getAppKey()
//        if (!TextUtils.isEmpty(appKey)) {
//            options.appKey = appKey
//        }
//
//        val serverConfig = PrivatizationConfig.getServerAddresses()
//        if (serverConfig != null) {
//            options.serverConfig = serverConfig
//        }
    }

    private fun initStatusBarNotificationConfig(options: SDKOptions) {
        // load 用户的 StatusBarNotificationConfig 设置项
        var userConfig = UserPreferences.getStatusConfig()
        if (userConfig == null) {
            userConfig = loadStatusBarNotificationConfig()
        }
        // 持久化生效
        UserPreferences.setStatusConfig(userConfig)
        // SDK statusBarNotificationConfig 生效
        options.statusBarNotificationConfig = userConfig
    }


    // 这里开发者可以自定义该应用初始的 StatusBarNotificationConfig
    private fun loadStatusBarNotificationConfig(): StatusBarNotificationConfig {
        val config = StatusBarNotificationConfig()
        // 点击通知需要跳转到的界面
        config.notificationEntrance = SplashActivity::class.java
        config.notificationSmallIconId = R.mipmap.push_logo
        config.notificationColor = resources.getColor(R.color.color_6)
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg"

        // 呼吸灯配置
        config.ledARGB = Color.GREEN
        config.ledOnMs = 1000
        config.ledOffMs = 1500

        // save cache，留做切换账号备用
        NIMIMCache.setNotificationConfig(config)
        return config
    }


    private val messageNotifierCustomization = object : MessageNotifierCustomization {
        override fun makeNotifyContent(nick: String, message: IMMessage): String? {
            return null // 采用SDK默认文案
        }

        override fun makeTicker(nick: String, message: IMMessage): String? {
            return null // 采用SDK默认文案
        }
    }
}