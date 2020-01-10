package com.aib.page

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aib.view.DefaultView
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var dv: DefaultView
    private lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dv = findViewById(R.id.dv)
        tv = findViewById(R.id.tv)

        getData()
    }

    private fun getData() {
        Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.newThread()))
                .baseUrl("https://tenapi.cn/")
                .build()
                .create(ApiService::class.java)
                .getData("广州")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<WeatherBean> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                        //显示加载UI
                        dv.showLoad()
                    }

                    override fun onNext(t: WeatherBean) {
                        //显示成功UI
                        dv.showSuccess()
                        tv.text = t.data.ganmao
                    }

                    override fun onError(e: Throwable) {
                        //显示错误UI，包含retry的ID控件点击事件相应
                        dv.showError {
                            getData()
                        }

                        //显示错误UI，不包含retry的ID控件点击事件相应
                        //dv.showError()
                    }
                })
    }
}