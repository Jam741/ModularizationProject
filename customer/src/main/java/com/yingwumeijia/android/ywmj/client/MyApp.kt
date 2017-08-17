package com.yingwumeijia.android.ywmj.client

//import com.tencent.smtt.sdk.QbSdk
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.taobao.sophix.PatchStatus
import com.taobao.sophix.SophixManager
import com.yingwumeijia.baseywmj.AppType
import com.yingwumeijia.baseywmj.base.JBaseApp
import com.yingwumeijia.baseywmj.im.IMEventManager
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.baseywmj.utils.net.SeverUrlManager
import com.yingwumeijia.commonlibrary.base.BaseApplication
import com.yingwumeijia.sharelibrary.ShareSDK
import io.rong.imkit.RongIM
import io.rong.push.RongPushClient


/**
 * Created by jamisonline on 2017/5/31.
 */
class MyApp : JBaseApp() {

    override fun appType(): AppType {
        return AppType.APP_C
    }

    override fun onCreate() {
        super.onCreate()

        initHotfix()
        ShareSDK.init(applicationContext)
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

        //3argexb6r68fe
//
//        RongPushClient.registerHWPush(this)
//        RongPushClient.registerMiPush(this, Config.MIPUSH_C.APP_ID, Config.MIPUSH_C.APP_KEY)

//        RongIM.init(this, "3argexb6r68fe")
//        IMEventManager(this)
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


}