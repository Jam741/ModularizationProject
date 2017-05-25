package com.yingwumeijia.commonlibrary.utils

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * 设备ID 工具类

 * @author Jam
 * *         create at 2016/5/19 17:42
 */
object DeviceID {

    @SuppressLint("DefaultLocale")
    fun getDeviceID(context: Context): String {

        val m_szLongID = getIMEI(context)
        +btmacAddress
        +getAndroidId(context)
        +getLocalMacAddress(context)
        +pseudoUniqueID


        //comput md5
        var m: MessageDigest? = null
        try {
            m = MessageDigest.getInstance("MD5")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        m!!.update(m_szLongID.toByteArray(), 0, m_szLongID.length)

        //get md5 bytes
        val p_md5Data = m.digest()
        //create a hex string
        var m_szUniqueID = String()
        for (i in p_md5Data.indices) {
            val b = 0xFF and p_md5Data[i]
            // if it is a single digit, make sure it have 0 in front (proper padding)
            if (b <= 0xF) m_szUniqueID += "0"
            // add number to string
            m_szUniqueID += Integer.toHexString(b)
        }
        return m_szUniqueID
    }

    /**
     * 获取蓝牙MacAdress

     * @return
     */
    // Nothing to do
    val btmacAddress: String
        get() {
            try {
                var m_BuletoothAdapter: BluetoothAdapter? = null
                m_BuletoothAdapter = BluetoothAdapter.getDefaultAdapter()
                val m_szBTMAC = m_BuletoothAdapter!!.address
                return m_szBTMAC
            } catch (e: Exception) {
            }

            return ""
        }

    /**
     * 获取Android ID

     * @param context
     * *
     * @return
     */
    fun getAndroidId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    /**
     * 获取android设备唯一标识码

     * @return
     */
    //we make this look like a valid IMEI
    //13 digits
    // Nothing to do
    val pseudoUniqueID: String
        get() {
            try {
                val m_szDevIDShort = "35" +
                        Build.BOARD.length % 10 +
                        Build.BRAND.length % 10 +
                        Build.CPU_ABI.length % 10 +
                        Build.DEVICE.length % 10 +
                        Build.DISPLAY.length % 10 +
                        Build.HOST.length % 10 +
                        Build.ID.length % 10 +
                        Build.MANUFACTURER.length % 10 +
                        Build.MODEL.length % 10 +
                        Build.PRODUCT.length % 10 +
                        Build.TAGS.length % 10 +
                        Build.TYPE.length % 10 +
                        Build.USER.length % 10
                return m_szDevIDShort
            } catch (e: Exception) {
            }

            return ""
        }

    /**
     * @param context
     * *
     * @return
     */
    fun getIMEI(context: Context): String? {
        try {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return telephonyManager.deviceId
        } catch (e: Exception) {
            //Nothing to do
        }

        return null
    }

    /**
     * 根据Wifi信息获取本地Mac

     * @param context
     * *
     * @return
     */
    fun getLocalMacAddress(context: Context): String {
        try {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val info = wifiManager.connectionInfo
            return info.macAddress
        } catch (e: Exception) {
            // Nothing to do
        }

        return ""
    }
}
