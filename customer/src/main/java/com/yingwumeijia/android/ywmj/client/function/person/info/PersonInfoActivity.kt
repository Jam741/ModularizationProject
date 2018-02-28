package com.yingwumeijia.android.ywmj.client.function.person.info

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.TextUtils
import com.muzhi.camerasdk.model.CameraSdkParameterInfo
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.android.ywmj.client.function.person.edit.EditPersonInfoActivity
import com.yingwumeijia.baseywmj.api.Api
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.constant.Constant
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.upload.UploadPictureHelper
import com.yingwumeijia.baseywmj.utils.net.HttpUtil
import com.yingwumeijia.baseywmj.utils.net.subscriber.ProgressSubscriber
import com.yingwumeijia.commonlibrary.utils.ListUtil
import com.yingwumeijia.commonlibrary.utils.glide.JImageLolder
import kotlinx.android.synthetic.main.person_info_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/7/4.
 */
class PersonInfoActivity : JBaseActivity() {

    val request_code_nickkname = 1
    val request_code_portrait = CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY

    val portrait by lazy { intent.getStringExtra(Constant.KEY_PORTRAIT) }
    val nikeName by lazy { intent.getStringExtra(Constant.KEY_NIKENAME) }
    val mob by lazy { intent.getStringExtra(Constant.KEY_MOB) }

    val mCameraSdkParameterInfo by lazy { CameraSdkParameterInfo() }

    val defaultNikeName: String = "设置昵称让大家认识你"

    companion object {
        fun start(activity: Activity, portrait: String, nikeName: String, mob: String) {
            val intent = Intent(activity, PersonInfoActivity::class.java)
            intent.putExtra(Constant.KEY_PORTRAIT, portrait)
            intent.putExtra(Constant.KEY_NIKENAME, nikeName)
            intent.putExtra(Constant.KEY_MOB, mob)
            ActivityCompat.startActivity(activity, intent, Bundle.EMPTY)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.person_info_act)

        topTitle.text = "个人信息"
        topLeft.setOnClickListener { close() }
        btn_nkname.setOnClickListener { EditPersonInfoActivity.startForResult(context, request_code_nickkname, nikeName) }
        btn_portrait.setOnClickListener { editPortrait() }

        if (TextUtils.isEmpty(portrait)) JImageLolder.loadPortrait256(context, iv_portrait, R.mipmap.mine_user_ywmj_circle)
        else JImageLolder.loadPortrait256(context, iv_portrait, portrait)
        if (TextUtils.isEmpty(nikeName)) tv_nkname.text = defaultNikeName else tv_nkname.text = nikeName
        tv_mob.text = mob

    }

    /**
     * edit portrait
     */
    private fun editPortrait() {
        mCameraSdkParameterInfo.isFilter_image = false
        mCameraSdkParameterInfo.isShow_camera = true
        mCameraSdkParameterInfo.isSingle_mode = true
        mCameraSdkParameterInfo.isCroper_image = true
        val intent = Intent()
        intent.setClassName(application, "com.muzhi.camerasdk.PhotoPickActivity")
        val b = Bundle()
        b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, mCameraSdkParameterInfo)
        intent.putExtras(b)
        ActivityCompat.startActivityForResult(context, intent, CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null) return
        when (requestCode) {
            request_code_nickkname -> updateNikeName(data)
            request_code_portrait -> getBundleForPortrait(data.extras)
        }
    }

    private fun updateNikeName(data: Intent) {
        var newNikeName = data.getStringExtra(Constant.KEY_INPUT_RESULT)
        if (!TextUtils.isEmpty(newNikeName))
            tv_nkname.text = newNikeName
    }

    private fun getBundleForPortrait(b: Bundle?) {
        if (b != null) {
            val mCameraSdkParameterInfo = b.getSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER) as CameraSdkParameterInfo
            val list = mCameraSdkParameterInfo.image_list
            if (ListUtil.isEmpty(list)) return Unit
            upLoadPortrait(list[0])
        }
    }

    private fun upLoadPortrait(s: String) {
        UploadPictureHelper.uploadSinglePicture(context, s, object : UploadPictureHelper.OnSingleLoadListener {
            override fun success(url: String) {
                uploadHeadImage(url)
            }
        })
    }


    fun uploadHeadImage(url: String) {
        val ob = Api.service.uploadHeadImage_C(url)
        HttpUtil.getInstance().toNolifeSubscribe(ob, object : ProgressSubscriber<Boolean>(context) {
            override fun _onNext(t: Boolean?) {
                UserManager.refreshProtrait(url)
                JImageLolder.loadPortrait256(context, iv_portrait, url)
                toastWith("头像上传成功")
            }

        })
    }


}