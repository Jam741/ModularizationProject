package com.yingwumeijia.baseywmj.function.web

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.support.v4.app.ActivityCompat
import android.text.TextUtils
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import cn.qqtheme.framework.entity.Province
import cn.qqtheme.framework.picker.AddressPicker
import cn.qqtheme.framework.picker.OptionPicker
import cn.qqtheme.framework.util.ConvertUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import com.camitor.pdfviewlibrary.PDFViewManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kaopiz.kprogresshud.KProgressHUD
import com.muzhi.camerasdk.model.CameraSdkParameterInfo
import com.orhanobut.logger.Logger
import com.sina.weibo.sdk.share.WbShareCallback
import com.tencent.tauth.IUiListener
import com.tencent.tauth.UiError
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.entity.UserSession
import com.yingwumeijia.baseywmj.function.StartActivityManager
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.WebViewManager
import com.yingwumeijia.baseywmj.function.cashier.CashierForOrderActivity
import com.yingwumeijia.baseywmj.function.cashier.CashierPresenter
import com.yingwumeijia.baseywmj.function.upload.UploadPictureHelper
import com.yingwumeijia.baseywmj.function.user.login.LoginActivity
import com.yingwumeijia.baseywmj.function.web.jsbean.*
import com.yingwumeijia.baseywmj.option.Config
import com.yingwumeijia.baseywmj.utils.Base64Utils
import com.yingwumeijia.baseywmj.utils.VerifyUtils
import com.yingwumeijia.baseywmj.utils.net.AccountManager
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.base.BaseActivity
import com.yingwumeijia.commonlibrary.utils.CallUtils
import com.yingwumeijia.commonlibrary.utils.SPUtils
import com.yingwumeijia.commonlibrary.utils.ScreenUtils
import com.yingwumeijia.sharelibrary.ShareData
import com.yingwumeijia.sharelibrary.ShareDialog
import com.yingwumeijia.sharelibrary.ShareManager
import org.json.JSONException
import org.json.JSONObject
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.IOException

/**
 * Created by jamisonline on 2017/7/8.
 */
class JsIntelligencer(activity: Activity) : BaseJsBirdge(activity) {

    val dialog: KProgressHUD? = null

    val gson by lazy { Gson() }

    val single_pic_choose_requestcode = CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY

    val contract_info_requestcode = 1001

    val toast by lazy { Toast.makeText(activity, "", Toast.LENGTH_SHORT) }

    val mCameraSdkParameterInfo by lazy { CameraSdkParameterInfo() }

    val addressPicker by lazy { createAddressPick() }

    var currentJsbridgeInfo: JsBridgeInfo<Any>? = null

    @JavascriptInterface
    fun closePage() {
        ActivityCompat.finishAfterTransition(activity)
    }

    @JavascriptInterface
    fun toLogin() {
        LoginActivity.startCurrent(activity)
    }

    @JavascriptInterface
    fun showToastMessage(msg: String) {
        toast.setText(msg)
        toast.show()
    }

    @JavascriptInterface
    fun showDialog() {
        dialog?.show()
    }

    @JavascriptInterface
    fun dismisDialog() {
        dialog?.dismiss()
    }

    @JavascriptInterface
    fun shareActivity(json: String) {
        var shareModel = gson.fromJson(json, ShareModel::class.java)
        Observable.create<Boolean> {
            Glide.with(activity).load(shareModel.img).asBitmap().into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) {
                    val shareData = ShareData(shareModel.getmShareTitle(), shareModel.getmDescription(), shareModel.getmShareUrl(), resource, shareModel.img, 0)
                    ShareDialog(ShareManager(activity, shareData, object : WbShareCallback {
                        override fun onWbShareFail() {}

                        override fun onWbShareCancel() {}

                        override fun onWbShareSuccess() {}
                    }, object : IUiListener {
                        override fun onComplete(p0: Any?) {}

                        override fun onCancel() {}

                        override fun onError(p0: UiError?) {}
                    })).show()
                }
            })
        }.subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<Boolean>() {
                    override fun onNext(t: Boolean?) {
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onError(e: Throwable?) {
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onCompleted() {
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })


    }


    @JavascriptInterface
    fun getToken() {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.getH5Token(), object : ProgressSubscriber<String>(activity) {
            override fun _onNext(t: String?) {
                webView.post { webView.loadUrl("javascript:getTokenReturn('" + t!! + "')") }
            }

        })
    }

    @JavascriptInterface
    fun getAccountToken(name: String) {
        Observable.just(name)
                .map { s: String -> UserSession(AccountManager.sessionId(), AccountManager.accessToken(), s) }
                .map { t: UserSession -> gson.toJson(t) }
                .map { t: String -> Base64Utils.encryptBASE64(t.toByteArray()) }
                .subscribe({ t: String ->
                    Logger.d(t)
                    webView.post { webView.loadUrl("javascript:getAccountTokenReturn('$t')") }
                })
    }

    @JavascriptInterface
    fun refreshToken(name: String) {
        HttpUtil.getInstance().toNolifeSubscribe(Api.service.refreshToken(AccountManager.sessionId(), AccountManager.refreshToken()), object : ProgressSubscriber<UserSession>(activity) {
            override fun _onNext(t: UserSession?) {
                AccountManager.refreshAccount(t!!)
                t!!.refreshToken = null
                t!!.name = name
                webView.post {
                    webView.loadUrl("javascript:getAccountTokenReturn('" + Base64Utils.encryptBASE64(gson.toJson(t!!).toByteArray()) + "')")
                }
            }
        })
    }


    @JavascriptInterface
    fun toPay(billId: String) {
        val activityName = "CashierForActiveActivity"
        val clazz = Class.forName(activityName)
        val intent = Intent(activity, clazz)
        intent.putExtra(Constant.KEY_BILL_ID, billId)
        activity.startActivity(intent)
    }

    @JavascriptInterface
    fun openWebview(url: String) {
        WebViewManager.startFullScreen(activity, url)
    }

    /**
     * 跳转到案例

     * @param caseId
     */
    @JavascriptInterface
    fun caseSkip(caseId: String) {
        StartActivityManager.startCaseDetailActivity(activity, Integer.valueOf(caseId))
    }


    @JavascriptInterface
    fun getUserInfo() {
        if (UserManager.getUserData() == null) return
        var phone = UserManager.getUserData()!!.userPhone
        if (TextUtils.isEmpty(phone)) return
        val phoneBean = PhoneBean()
        phoneBean.phone = phone
        webView.loadUrl("javascript:getUserInfoReturn('" + Base64Utils.encryptBASE64(gson.toJson(phoneBean).toByteArray()) + "')")

    }

    @JavascriptInterface
    fun payResult(isSuccess: String) {
        dismisDialog()
        val intent = Intent()
        intent.putExtra(Constant.PAY_SUCCESS_KEY, isSuccess == "true")
        activity.setResult(Activity.RESULT_OK, intent)
        activity.finish()
    }


    @JavascriptInterface
    fun invokeNative(jsonParam: String) {
        currentJsbridgeInfo = getJsBridgeBean(jsonParam)
        var data: String? = getDataJson(jsonParam)
        when (currentJsbridgeInfo!!.code) {
            Config.CODE_DIALOG_SHOW -> dialog?.show()
            Config.CODE_DIALOG_HIDE -> dialog?.dismiss()
            Config.CODE_CLOSE -> ActivityCompat.finishAfterTransition(activity)
            Config.CODE_TOAST -> showToastMessage(data!!)
            Config.CODE_LOGIN -> LoginActivity.startCurrent(activity)
            Config.CODE_SHARE -> shareActivity(data!!)
            Config.CODE_PAY -> toPay(data!!)
            Config.CODE_OPEN -> openPage(data!!)
            Config.CODE_PAY_RESULT -> payResult(data!!)
            Config.CODE_GET_SINGLE_PHOTO -> getSinglePhoto()
            Config.CODE_OPEN_CASE_DETIAL -> StartActivityManager.startCaseDetailActivity(activity, currentJsbridgeInfo!!.data as Int)
            Config.CODE_CHOOSE_ADRESS -> showChooseAddress(data as String)
            Config.CODE_GET_CONTACT_INFO -> getContactInfo()
            Config.CODE_PICK_SINGLE_STING -> singleChoosePick(data as String)
            Config.CODE_CALL_PHONE_NUMBER -> if (data != null && VerifyUtils.verifyMobilePhoneNumber(data)) CallUtils.callPhone(data, activity)
            Config.CODE_ORDER_BILL_PAY -> {
                CashierForOrderActivity.start(activity, data!!)
//                val activityName = "CashierForOrderActivity"
//                val clazz = Class.forName(activityName)
//                val intent = Intent(activity, clazz)
//                intent.putExtra(Constant.KEY_BILL_ID, data)
//                activity.startActivity(intent)
            }
            Config.CODE_GET_BILL_ID -> {
                currentJsbridgeInfo!!.data = SPUtils.get(activity, "KEY_CURRENT_BILL_ID", "")
                invokeH5(currentJsbridgeInfo!!)
            }
            Config.CODE_COPY -> {
                val cm = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", data)
                cm.primaryClip = clipData
            }
            Config.CODE_GET_LOGIN_STATUS -> {
                currentJsbridgeInfo!!.data = UserManager.isLogin(activity)
                invokeH5(currentJsbridgeInfo!!)
            }
        }


    }

    private fun openPage(data: String) {
        var openBean = Gson().fromJson(data, JsOpenBean::class.java)
        Logger.d("befo==" + openBean.url)
        if (openBean.url.toLowerCase().endsWith(".pdf")) {
            Logger.d(openBean.url)
            PDFViewManager.openPDF(activity, openBean.title, openBean.url)
        } else {
            if (openBean.title == null) {
                WebViewManager.startFullScreen(activity, openBean.url)
            } else {
                WebViewManager.startHasTitle(activity, openBean.url, openBean.title)
            }
        }

    }


    fun singleChoosePick(data: String) {
        val jsSingleChooseBean = gson.fromJson<JsSingleChooseBean>(data, JsSingleChooseBean::class.java!!)
        Observable.create<ArrayList<JsSingleChooseBean.ArrayBean>> { t: Subscriber<in ArrayList<JsSingleChooseBean.ArrayBean>>? -> t!!.onNext(jsSingleChooseBean.array as ArrayList<JsSingleChooseBean.ArrayBean>) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { t: ArrayList<JsSingleChooseBean.ArrayBean>? -> t!!.map { it -> it.desc } }
                .map { strings: List<String>? ->
                    OptionPicker(activity, strings).apply {
                        setCancelTextColor(activity.resources.getColor(R.color.color_6))
                        setCancelText(R.string.btn_cancel)
                        setCancelTextSize(16)
                        setSubmitTextColor(activity.resources.getColor(R.color.color_6))
                        setSubmitText(R.string.btn_confirm)
                        setTextColor(activity.resources.getColor(R.color.color_6), activity.resources.getColor(R.color.color_5))
                        setSubmitTextSize(16)
                        setCycleDisable(true)
                        setHeight(ScreenUtils.screenHeight / 3)
                        setLineColor(activity.resources.getColor(R.color.color_3))
                        selectedIndex = 0
                        setOnOptionPickListener(object : OptionPicker.OnOptionPickListener() {
                            override fun onOptionPicked(position: Int, option: String) {
                                currentJsbridgeInfo!!.data = jsSingleChooseBean.array[position]
                                invokeH5(currentJsbridgeInfo!!)
                            }
                        })
                        show()
                    }
                }

    }

    fun showChooseAddress(data: String) {
        if (!TextUtils.isEmpty(data)) {
            val jsAreaCallbackBean = gson.fromJson<JsAreaCallbackBean>(data, JsAreaCallbackBean::class.java)
            addressPicker.setSelectedItem(jsAreaCallbackBean.province.name, jsAreaCallbackBean.city.name, jsAreaCallbackBean.area.name)
        }
        addressPicker.show()
    }


    fun createAddressPick(): AddressPicker {

        return AddressPicker(activity, getProvinces()).apply {
            setCancelTextColor(activity.resources.getColor(R.color.color_6))
            setCancelText(R.string.btn_cancel)
            setCancelTextSize(16)
            setSubmitTextColor(activity.resources.getColor(R.color.color_6))
            setSubmitText(R.string.btn_confirm)
            setTextColor(activity.resources.getColor(R.color.color_6), activity.resources.getColor(R.color.color_5))
            setSubmitTextSize(16)
            setCycleDisable(true)
            setSelectedItem("四川省", "成都市", "锦江区")
            setHeight(ScreenUtils.screenHeight / 3)
            setLineColor(activity.resources.getColor(R.color.color_3))
            setOnAddressPickListener { province, city, county ->
                val jsAreaCallbackBean = JsAreaCallbackBean(
                        JsAreaCallbackBean.ProvinceBean(province.areaName, Integer.valueOf(province.areaId)),
                        JsAreaCallbackBean.CityBean(city.areaName, Integer.valueOf(city.areaId)),
                        JsAreaCallbackBean.AreaBean(county.areaName, Integer.valueOf(county.areaId))
                )
                currentJsbridgeInfo!!.data = jsAreaCallbackBean
                invokeH5(currentJsbridgeInfo!!)
            }
        }

    }

    private fun getProvinces(): java.util.ArrayList<Province> {
        var addressJson: String? = null
        try {
            addressJson = ConvertUtils.toString(activity.assets.open("allarea.json"))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val addressJsonLast = addressJson!!.replace("id", "areaId").replace("name", "areaName").replace("areas", "counties")
        val provinces = gson.fromJson<java.util.ArrayList<Province>>(addressJsonLast, object : TypeToken<List<Province>>() {
        }.type)
        return provinces
    }

    private fun getContactInfo() {
        val i = Intent()
        i.action = Intent.ACTION_PICK
        i.data = ContactsContract.Contacts.CONTENT_URI
        activity.startActivityForResult(i, contract_info_requestcode)
    }


    fun getSinglePhoto() {
        mCameraSdkParameterInfo.isFilter_image = false
        mCameraSdkParameterInfo.isShow_camera = true
        mCameraSdkParameterInfo.isSingle_mode = true
        mCameraSdkParameterInfo.isCroper_image = true
        mCameraSdkParameterInfo.isCroper_image_rectangle = true
        val intent = Intent()
        intent.setClassName(activity.application, "com.muzhi.camerasdk.PhotoPickActivity")
        val b = Bundle()
        b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, mCameraSdkParameterInfo)
        intent.putExtras(b)
        ActivityCompat.startActivityForResult(activity, intent, CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY, null)
    }


    fun getJsBridgeBean(jsonParam: String): JsBridgeInfo<Any> {
        val bridgeInfo = gson.fromJson<JsBridgeInfo<Any>>(jsonParam, JsBridgeInfo::class.java!!)
        return bridgeInfo
    }

    private fun getDataJson(jsonParam: String): String? {
        Log.d("jam", jsonParam)
        var data: String? = null
        try {
            val jsonObject = JSONObject(jsonParam)
            if (jsonObject != null)
                data = jsonObject.getString("data")
            Log.d("jam", data)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return data
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            single_pic_choose_requestcode -> getBundle(data!!.extras)
            contract_info_requestcode -> getUserContactFroData(data)
            Constant.REQUEST_CODE_FOR_PAY -> {
                Log.d("jam", "==========ybao_resquest -经来了==========")
                val isPaySuccess = data!!.getBooleanExtra(Constant.PAY_SUCCESS_KEY, false)
                if (isPaySuccess) {
                    Log.d("jam", "==========isPaySuccess -经来了==========")
                    if (webView != null)
                        webView.post(Runnable {
                            webView.loadUrl("javascript:paySuccReturn()")
                        })
                }
            }
        }
    }

    private fun getUserContactFroData(data: Intent?) {
        if (data == null) return
        val contactData = data.data ?: return
        var contactNumber: String? = null
        var contactName: String? = null
        val cursor = activity.contentResolver.query(contactData, null, null, null, null)
        if (cursor.moveToFirst()) {
            var hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
            val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            if (hasPhone.equals("1", ignoreCase = true)) {
                hasPhone = "true"
            } else {
                hasPhone = "false"
            }
            if (java.lang.Boolean.parseBoolean(hasPhone)) {
                val phones = activity.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null)
                while (phones.moveToNext()) {
                    contactNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    contactName = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                }
                phones.close()
            }
            val jsContactBean = JsContactBean(contactNumber, contactName)
            val jsContactBeanJsBridgeInfo = currentJsbridgeInfo
            jsContactBeanJsBridgeInfo!!.data = jsContactBean
            invokeH5(jsContactBeanJsBridgeInfo)
        } else {
            Log.d("JAM", "error")
        }
    }

    private fun getBundle(b: Bundle?) {
        if (b != null) {
            val mCameraSdkParameterInfo = b.getSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER) as CameraSdkParameterInfo
            upLoadPic(mCameraSdkParameterInfo.image_list[0])
        }
    }

    private fun upLoadPic(filePath: String) {
        UploadPictureHelper.uploadSinglePicture(activity, filePath, object : UploadPictureHelper.OnSingleLoadListener {
            override fun success(url: String) {
                val jsBridgeInfo = currentJsbridgeInfo
                jsBridgeInfo!!.data = url!!
                invokeH5(jsBridgeInfo)
            }

        })
    }

    fun invokeH5(o: Any) {
        invokeH5(currentJsbridgeInfo!!.callback, o)
    }

    fun invokeH5(callbackName: String, o: Any) {
        webView.post {
            webView.loadUrl("javascript:" + callbackName + "('" + Base64Utils.encryptBASE64(gson.toJson(o).toByteArray()) + "')")
        }
    }

    override fun onResume() {
    }

}