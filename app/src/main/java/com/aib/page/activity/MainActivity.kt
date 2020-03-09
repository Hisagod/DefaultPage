package com.aib.page.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aib.page.R
import com.aib.page.viewmodel.MainViewModel
import com.aib.page.net.NetStatus
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error.*


class MainActivity : AppCompatActivity() {

    private val vm by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()
    }

    private fun getData() {
        vm.getWeatherData("广州").observe(this, androidx.lifecycle.Observer {
            when (it.status) {
                NetStatus.LOAD -> {
                    //显示加载UI
                    dv.showLoad()
                }
                NetStatus.SUCCESS -> {
                    //第一步：显示成功UI
                    dv.showSuccess()
                    //第二步：设置UI数据
                    tv.text = it.data.toString()
                }
                NetStatus.ERROR -> {
                    //方式一：显示错误信息提示
                    tv_error_tip.text = it.msg
                    //设置retry的ID控件点击事件
                    dv.showError {
                        getData()
                    }

                    //方式二：显示错误UI，不包含retry的ID控件点击事件相应
                    //tv_error_tip.text = it.msg
                    //dv.showError()
                }
            }
        })
    }
}