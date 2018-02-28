package com.yingwumeijia.commonlibrary.utils

import android.app.ActivityManager
import android.content.Context
import android.text.TextUtils

/**
 * Created by jamisonline on 2017/9/14.
 */
object SystemUtil {

    /**
     * 获取当前进程名
     * @param context
     * *
     * @return 进程名
     */
    fun getProcessName(context: Context): String {
        var processName: String? = null

        // ActivityManager
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        while (true) {
            for (info in am.getRunningAppProcesses()) {
                if (info.pid === android.os.Process.myPid()) {
                    processName = info.processName
                    break
                }
            }

            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName!!
            }

            // take a rest and again
            try {
                Thread.sleep(100L)
            } catch (ex: InterruptedException) {
                ex.printStackTrace()
            }

        }
    }
}