package com.yingwumeijia.baseywmj.function.opinion

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.Editable
import android.text.TextWatcher
import com.muzhi.camerasdk.model.CameraSdkParameterInfo
import com.yingwumeijia.baseywmj.R
import com.yingwumeijia.baseywmj.base.JBaseActivity
import kotlinx.android.synthetic.main.opinion_frag.*
import kotlinx.android.synthetic.main.toolbr_layout.*

/**
 * Created by jamisonline on 2017/7/5.
 */
class OpinionActivity : JBaseActivity(), OpinionContract.View {


    val request_code_portrait = CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY

    val mCameraSdkParameterInfo by lazy { CameraSdkParameterInfo() }

    val persenter by lazy { OpinionPresenter(context, this) }

    val photoAdapter by lazy { PhotoListAdapter() }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, OpinionActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.opinion_frag)

        topTitle.text = "我的建议"
        topLeft.setOnClickListener { close() }
        gv_photo.run {
            adapter = this@OpinionActivity.photoAdapter
            setOnItemClickListener { parent, view, position, id ->
                if (photoAdapter.getItem(position).isButton()) {
                    choosePhoto()
                }
            }
        }
        btn_commit.setOnClickListener { persenter.commit(photoAdapter.imageList, inputValue()) }
        tv_opinionContent.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_commit.isEnabled = inputValue().length != 0
                font_num.text = inputValue().length.toString() + "/200"
            }
        })
    }


    fun inputValue(): String {
        return tv_opinionContent.text.toString()
    }

    override fun showSuccess() {
        toastWith("提交成功，我们将尽快解决")
        close()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null && resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                request_code_portrait -> {
                    val b = data.extras
                    getBundle(b)
                }
            }
        }
    }


    private fun getBundle(b: Bundle?) {
        if (b != null) {
            val mCameraSdkParameterInfo = b.getSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER) as CameraSdkParameterInfo
            val list = mCameraSdkParameterInfo.image_list
            photoAdapter.addPhotos(list)
        }
    }


    private fun choosePhoto() {
        mCameraSdkParameterInfo.isFilter_image = false
        mCameraSdkParameterInfo.isShow_camera = true
        mCameraSdkParameterInfo.isSingle_mode = false
        mCameraSdkParameterInfo.max_image = 4
        mCameraSdkParameterInfo.image_list = photoAdapter.imageList
        val intent = Intent()
        intent.setClassName(context.application, "com.muzhi.camerasdk.PhotoPickActivity")
        val b = Bundle()
        b.putSerializable(CameraSdkParameterInfo.EXTRA_PARAMETER, mCameraSdkParameterInfo)
        intent.putExtras(b)
        ActivityCompat.startActivityForResult(context, intent, CameraSdkParameterInfo.TAKE_PICTURE_FROM_GALLERY, null)
    }

}