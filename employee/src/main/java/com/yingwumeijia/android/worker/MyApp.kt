package com.yingwumeijia.android.worker

import android.content.Context
import android.content.Intent
import android.graphics.Color
import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import com.netease.nim.uikit.NimUIKit
import com.netease.nim.uikit.custom.DefaultUserInfoProvider
import com.netease.nim.uikit.session.viewholder.MsgViewHolderThumbBase
import com.netease.nimlib.sdk.NIMClient
import com.netease.nimlib.sdk.Observer
import com.netease.nimlib.sdk.SDKOptions
import com.netease.nimlib.sdk.StatusBarNotificationConfig
import com.netease.nimlib.sdk.auth.LoginInfo
import com.netease.nimlib.sdk.mixpush.NIMPushClient
import com.netease.nimlib.sdk.msg.MessageBuilder
import com.netease.nimlib.sdk.msg.MessageNotifierCustomization
import com.netease.nimlib.sdk.msg.MsgService
import com.netease.nimlib.sdk.msg.MsgServiceObserve
import com.netease.nimlib.sdk.msg.constant.MsgStatusEnum
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum
import com.netease.nimlib.sdk.msg.model.CustomMessageConfig
import com.netease.nimlib.sdk.msg.model.IMMessage
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.pisces.android.sharesdk.ShareSDK
import com.taobao.sophix.PatchStatus
import com.taobao.sophix.SophixManager
import com.yingwumeijia.android.worker.function.splash.SplashActivity
import com.yingwumeijia.baseywmj.AppType
import com.yingwumeijia.baseywmj.base.JBaseApp
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.message.MessageActivity
import com.yingwumeijia.baseywmj.function.message.MessageBean
import com.yingwumeijia.baseywmj.function.message.MessageManager
import com.yingwumeijia.baseywmj.nimim.NIMIMCache
import com.yingwumeijia.baseywmj.nimim.UserPreferences
import com.yingwumeijia.baseywmj.nimim.conversation.customer.EmployeeTeamCustomization
import com.yingwumeijia.baseywmj.nimim.msg.*
import com.yingwumeijia.baseywmj.nimim.provider.NimDemoLocationProvider
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.commonlibrary.utils.SystemUtil

/**
 * Created by jamisonline on 2017/5/31.
 */
class
MyApp : JBaseApp() {

    override fun appType(): AppType {
        return AppType.APP_E
    }

    override fun onCreate() {
        super.onCreate()
        initHotfix()
        ShareSDK.initSDK(this, "3416527308", "wxe317a57cc8b93035")
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

        NIMIMCache.setContext(this)
        // 注册小米推送，参数：小米推送证书名称（需要在云信管理后台配置）、appID 、appKey，该逻辑放在 NIMClient init 之前
        NIMPushClient.registerMiPush(this, "EmployeeMIPHSU", Config.MIPUSH_E.APP_ID, Config.MIPUSH_E.APP_KEY)
        // 注册华为推送，参数：华为推送证书名称（需要在云信管理后台配置）
        NIMPushClient.registerHWPush(this, "EmployeeHWPUSH")
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

        NimUIKit.setCommonTeamSessionCustomization(EmployeeTeamCustomization())

        NimUIKit.registerMsgItemViewHolder(PayMessageAttachment::class.java, MsgViewHolderPay::class.java)

        NIMClient.getService(MsgService::class.java).registerCustomAttachmentParser(CustomAttachParser())

        NIMClient.getService(MsgServiceObserve::class.java).observeCustomNotification({ p0 ->
            Logger.d(p0.sessionType)
            Logger.d(p0.content)


            val ob = JSON.parseObject(p0.content)
            val noticeType = ob.getInteger("noticeType")
            if (noticeType == CustomAttachmentType.NOTICE_SYSTEM_MESSAGE) {
                //收到站内信
                val content = ob.getJSONObject("content").toJSONString()
                didSystemMessageReceived(content)

            } else if (noticeType == CustomAttachmentType.NOTICE_NOTICE_MESSAGE) {
                //收到小灰条消息
                val content = ob.getJSONObject("content").toJSONString()
                didNoticeMessageReceived(content)
            }


        }, true)


        NIMClient.getService(MsgServiceObserve::class.java).observeReceiveMessage(object : Observer<List<IMMessage>> {
            override fun onEvent(p0: List<IMMessage>?) {
                val mIntent = Intent(Constant.MSG_RECEIVE_ACTION_E)
                sendBroadcast(mIntent)
            }
        }, true)
    }


    private fun didNoticeMessageReceived(content: String) {
        val noticeMsgBean = Gson().fromJson(content, NoticeMsgBean::class.java)
        // 向群里插入一条Tip消息，使得该群能立即出现在最近联系人列表（会话列表）中，满足部分开发者需求。
        val content = HashMap<String, Any>(1)
        content.put("content", noticeMsgBean.message)
        // 创建tip消息，teamId需要开发者已经存在的team的teamId
        val msg = MessageBuilder.createTipMessage(noticeMsgBean.groupId, SessionTypeEnum.Team)
        msg.remoteExtension = content
        // 自定义消息配置选项
        val config = CustomMessageConfig()
        // 消息不计入未读
        config.enableUnreadCount = false
        msg.setConfig(config)
        // 消息发送状态设置为success
        msg.setStatus(MsgStatusEnum.success)
        // 保存消息到本地数据库，但不发送到服务器
        NIMClient.getService(MsgService::class.java).saveMessageToLocal(msg, true)
    }


    fun didSystemMessageReceived(content: String) {
        val messageBean = assembleMessageBean(content)
        MessageManager.insert(this, messageBean)

        MessageActivity.setUnReader(applicationContext,true)

        val intent = Intent(Constant.SYSTEM_MSG_RECEIVE_ACTION_E)
        sendBroadcast(intent)
        //发送广播
        val mIntent = Intent(MessageActivity.ACTION_MESSAGE_E)
        mIntent.putExtra(MessageActivity.KEY_MESSAGE, messageBean)
        sendBroadcast(mIntent)
    }

    private fun assembleMessageBean(message: String): MessageBean {

        val bean = Gson().fromJson<MessageBean>(message, MessageBean::class.java!!)
        bean.messageSendId = Constant.SYSTEM_TARGET_ID
        bean.messageUserId = "" + UserManager.getUserData()!!.id
        return bean
    }

    private fun getLoginInfo(): LoginInfo? {
        val loginInfo = UserManager.getNIMLoginInfo(this)
        if (loginInfo != null) {
            NIMIMCache.setAccount(loginInfo.account)
            return loginInfo
        } else {
            return null
        }
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
            options.appKey = Config.NIM_IM.release_app_key
        } else {
            options.appKey = Config.NIM_IM.release_app_key
        }
        initStatusBarNotificationConfig(options)

        // 配置保存图片，文件，log等数据的目录
        options.sdkStorageRootPath = Config.localStoreFolderPath(this)

        // 配置数据库加密秘钥
        options.databaseEncryptKey = "BeautifulHomeEmployee"

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
        config.notificationSmallIconId = R.mipmap.push_logo_e
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

    private fun initHotfix() {
        var appVersion: String
        try {
            appVersion = this.packageManager.getPackageInfo(this.packageName, 0).versionName
        } catch (e: Exception) {
            appVersion = "1.0.0"
        }

        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(Config.HOTFIX_E.ALI_APP_ID, Config.HOTFIX_E.ALI_HOTFIX_AppSecret, Config.HOTFIX_E.ALI_HOTFIX_RSASECRET)
                .setAesKey(Config.HOTFIX_E.ALI_HOTFIX_AES_KEY)
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
}