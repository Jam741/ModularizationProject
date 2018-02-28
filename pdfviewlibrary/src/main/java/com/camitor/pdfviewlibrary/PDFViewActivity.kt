package com.camitor.pdfviewlibrary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import com.joanzapata.pdfview.listener.OnLoadCompleteListener
import com.orhanobut.logger.Logger
import com.yingwumeijia.commonlibrary.base.BaseActivity
import kotlinx.android.synthetic.main.pdf_view_act.*
import kotlinx.android.synthetic.main.toolbr_layout.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.*
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_OK
import java.net.MalformedURLException
import java.net.URL


/**
 * Created by jamisonline on 2017/8/15.
 */
class PDFViewActivity : BaseActivity() {

    val apiService by lazy {
        Retrofit
                .Builder()
                .baseUrl("https://customer.yingwumeijia.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(OkHttpClient())
                .build()
                .create(Api::class.java)
    }
    val pdfPath by lazy { intent.getStringExtra(KEY_PDF_PATH) }
    val title by lazy { intent.getStringExtra(KEY_TITLE) }

    val outfilepath = Environment.getExternalStorageDirectory().absolutePath

    private var outfile: File? = null

    private val pdfNameAll = "a.pdf"


    var pdfFile: File? = null

    companion object {

        val KEY_PDF_PATH = "KEY_PDF_PATH"
        val KEY_TITLE = "KEY_TITLE"

        fun start(context: Context, title: String, pdfPath: String) {
            val starter = Intent(context, PDFViewActivity::class.java)
            starter.putExtra(KEY_PDF_PATH, pdfPath)
            starter.putExtra(KEY_TITLE, title)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pdf_view_act)
        topTitle.text = title
        topLeft.setOnClickListener { close() }
//        pdfView.fromFile()

        Logger.d(pdfPath)
        downloadPDF()

    }

    fun downloadPDF() {
        progressDialog.show()
        Retrofit.Builder()
                .baseUrl("http://192.168.56.1")
                .client(OkHttpClient.Builder()
                        .addNetworkInterceptor { chain: Interceptor.Chain? ->
                            val orginalResponse = chain!!.proceed(chain!!.request())
                            return@addNetworkInterceptor orginalResponse.newBuilder()
                                    .body(ProgressResponseBody(orginalResponse.body(), ProgressResponseBody.ProgressListener { progress, total, done ->
                                    }))
                                    .build()
                        }
                        .build())
                .build()
                .create(Api::class.java)
                .downloadFileWithDynamicUrlSync(pdfPath)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {

                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        Observable
                                .create(Observable.OnSubscribe<File> { subscriber -> writeResponseBodyToDisk(response!!.body(), subscriber) })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : Subscriber<File>() {
                                    override fun onCompleted() {
                                        progressDialog.dismiss()
                                    }

                                    override fun onError(e: Throwable) {
                                        Logger.d(e.message)
                                        progressDialog.dismiss()
                                    }

                                    override fun onNext(file: File) {
                                        showPDFWith(file)
                                    }
                                })
                    }
                })
    }


    fun showPDFWith(pdfFile: File) {
        this.pdfFile = pdfFile
        pdfView.fromFile(pdfFile)
                .defaultPage(1)
                .enableSwipe(true)
                .showMinimap(false)
                .swipeVertical(true)
                .load()
    }


    interface Api {

        /**
         * 下载文件
         * @param fileUrl
         * *
         * @return
         */
        @GET
        fun downloadFileWithDynamicUrlSync(@Url fileUrl: String): Call<ResponseBody>
    }


    private fun writeResponseBodyToDisk(body: ResponseBody, subscriber: Subscriber<in File>): Boolean {
        try {
            // todo change the file location/name according to your needs
            val futureStudioIconFile = File(context.getExternalFilesDir(null).toString() + File.separator + "ywmj.pdf")

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
                subscriber.onError(Throwable("package error"))

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
                    showPDFWith(futureStudioIconFile)
                }
            }
        } catch (e: IOException) {
            subscriber.onError(Throwable("other error"))
            return false
        }

    }

    override fun onDestroy() {
        if (pdfFile != null)
            if (pdfFile!!.exists()) {
                pdfFile!!.delete()
            }
        super.onDestroy()
    }
}