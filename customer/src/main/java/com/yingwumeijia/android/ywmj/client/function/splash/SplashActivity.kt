package com.yingwumeijia.android.ywmj.client.function.splash

import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import com.orhanobut.logger.Logger
import com.yingwumeijia.android.ywmj.client.R
import com.yingwumeijia.android.ywmj.client.function.home.CustomerMainActivity
import com.yingwumeijia.baseywmj.api.Service
import com.yingwumeijia.baseywmj.base.JBaseActivity
import com.yingwumeijia.baseywmj.entity.bean.SeverBean
import com.yingwumeijia.baseywmj.function.UserManager
import com.yingwumeijia.baseywmj.function.main.MainActivity
import com.yingwumeijia.baseywmj.option.PATHUrlConfig
import com.yingwumeijia.baseywmj.utils.net.AccountManager
import com.yingwumeijia.baseywmj.utils.net.SeverUrlManager
import com.yingwumeijia.baseywmj.utils.net.converter.GsonConverterFactory
import com.yingwumeijia.baseywmj.utils.net.interceptor.ProgressResponseBody
import com.yingwumeijia.commonlibrary.base.BaseApplication
import com.yingwumeijia.commonlibrary.utils.AppUtils
import io.rong.imkit.RongIM
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.*
import java.math.BigInteger
import java.nio.channels.FileChannel
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

class SplashActivity : JBaseActivity() {

    var severBean: SeverBean? = null

    val Api by lazy {
        Logger.d(PATHUrlConfig.severUrl())
        Retrofit.Builder()
                .baseUrl(PATHUrlConfig.severUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(defaultClient())
                .build()
                .create(Service::class.java)
    }

    val downloaderProgressDialog: ProgressDialog by lazy {
        ProgressDialog(context).apply {
            setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            setCanceledOnTouchOutside(false)
            max = 100
            create()
        }
    }

    private fun defaultClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d("Sever:", message) })
        builder.connectTimeout(10000, TimeUnit.MILLISECONDS)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }


    fun loadBaseUrl() {
        Api
                .getService("c", AppUtils.getVersionName(this))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(object : Subscriber<SeverBean>() {
                    override fun onNext(t: SeverBean?) {
                        didSuccess(t!!)
                    }

                    override fun onError(e: Throwable?) {

                    }

                    override fun onCompleted() {

                    }

                })

    }

    /**
     *  加载完成
     */
    private fun didSuccess(t: SeverBean) {
        severBean = t
        SeverUrlManager.refreshBaseUrl(t!!.serverUrl)
        SeverUrlManager.refreshIMKey(t!!.appImkey)
        RongIM.init(BaseApplication.appContext(), SeverUrlManager.IMKey())
        close()
        CustomerMainActivity.start(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (!UserManager.isLogin(context)) AccountManager.clearnAccount()
        loadBaseUrl()
    }


    /**
     * 开启下载
     */
    fun startUpDownloader() {
        downloaderProgressDialog.progress = 0
        downloaderProgressDialog.show()
        Retrofit.Builder()
                .baseUrl("http://192.168.56.1")
                .client(OkHttpClient.Builder()
                        .addNetworkInterceptor { chain: Interceptor.Chain? ->
                            val orginalResponse = chain!!.proceed(chain!!.request())
                            return@addNetworkInterceptor orginalResponse.newBuilder()
                                    .body(ProgressResponseBody(orginalResponse.body(), ProgressResponseBody.ProgressListener { progress, total, done ->
                                        downloaderProgressDialog.progress = ((progress * 100) / total).toInt()
                                    }))
                                    .build()
                        }
                        .build())
                .build()
                .create(Service::class.java)
                .downloadFileWithDynamicUrlSync(severBean!!.latestAndroidUrl)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        Observable
                                .create(Observable.OnSubscribe<File> { subscriber -> writeResponseBodyToDisk(response!!.body(), subscriber) })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : Subscriber<File>() {
                                    override fun onCompleted() {
                                        downloaderProgressDialog.dismiss()
                                    }

                                    override fun onError(e: Throwable) {
                                        downloaderProgressDialog.dismiss()
                                        if (e.message == "package error") {
                                            showReDownloadDialog()
                                        } else {
                                            ActivityCompat.finishAfterTransition(context)
                                        }

                                    }

                                    override fun onNext(file: File) {
                                        openFile(file)
                                    }
                                })
                    }
                })

    }

    private fun showReDownloadDialog() {
        var isReDowload: Boolean = false
        AlertDialog.Builder(context)
                .setTitle(R.string.dialog_title)
                .setCancelable(false)
                .setMessage("安装包出错，请重试")
                .setPositiveButton("确定") { dialog, which -> isReDowload = true }
                .setNegativeButton("取消") { dialog, which -> isReDowload = false }
                .setOnDismissListener {
                    if (!isReDowload) {
                        close()
                        System.exit(-1)
                    } else {
                        startUpDownloader()
                    }
                }
                .show()
    }


    /**
     * 写入文件
     */
    private fun writeResponseBodyToDisk(body: ResponseBody, subscriber: Subscriber<in File>): Boolean {
        try {
            // todo change the file location/name according to your needs
            val futureStudioIconFile = File(context.getExternalFilesDir(null).toString() + File.separator + "com.yingwumeijia.android.ywmj.client.apk")

            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)

                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0

                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)

                while (true) {
                    val read = inputStream!!.read(fileReader)

                    if (read == -1) {
                        break
                    }

                    outputStream!!.write(fileReader, 0, read)

                    fileSizeDownloaded += read.toLong()

                    //  progressDialog.setProgress((int) ((fileSizeDownloaded * 100) / fileSize));
                    // Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize + "||" + ((fileSizeDownloaded * 100) / fileSize) + "%");
                }

                outputStream!!.flush()
                if (severBean!!.latestAndroidMd5 == getMd5ByFile(futureStudioIconFile)) {
                    subscriber.onNext(futureStudioIconFile)
                } else {
                    subscriber.onError(Throwable("package error"))
                }

                return true
            } catch (e: IOException) {
                subscriber.onError(Throwable("other error"))
                return false
            } finally {
                if (inputStream != null) {
                    inputStream.close()
                }

                if (outputStream != null) {
                    outputStream.close()
                }

                if (futureStudioIconFile.isAbsolute) {
                    openFile(futureStudioIconFile)
                }
            }
        } catch (e: IOException) {
            subscriber.onError(Throwable("other error"))
            return false
        }

    }

    @Throws(FileNotFoundException::class)
    fun getMd5ByFile(file: File): String {
        var value: String? = null
        val `in` = FileInputStream(file)
        try {
            val byteBuffer = `in`.channel.map(FileChannel.MapMode.READ_ONLY, 0, file.length())
            val md5 = MessageDigest.getInstance("MD5")
            md5.update(byteBuffer)
            val bi = BigInteger(1, md5.digest())
            value = bi.toString(16)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (null != `in`) {
                try {
                    `in`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        return value as String
    }


    private fun openFile(file: File) {
        // TODO Auto-generated method stub
        Log.e("OpenFile", file.name)
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = android.content.Intent.ACTION_VIEW
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive")
        context.startActivity(intent)
        System.exit(-1)
    }


}
