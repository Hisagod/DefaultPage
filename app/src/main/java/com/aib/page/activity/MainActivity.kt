package com.aib.page.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aib.page.R
import com.aib.page.extension.getViewModel
import com.aib.page.viewmodel.MainViewModel
import com.aib.page.net.NetStatus
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error.*


class MainActivity : AppCompatActivity() {

    private val vm by lazy { getViewModel(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadSuccess()
        loadEmpty()
        loadError()
    }

    private fun loadSuccess() {
        btn_success.setOnClickListener {
            getData(10)
        }
    }

    private fun loadEmpty() {
        btn_empty.setOnClickListener {
            getData(0)
        }
    }

    private fun loadError() {
        btn_error.setOnClickListener {
            getData(-1)
        }
    }

    private fun getData(count: Int) {
        vm.getOne(count).observe(this, androidx.lifecycle.Observer {
            when (it.status) {
                NetStatus.LOAD -> {
                    //显示加载UI
                    dv.showLoad()
                }
                NetStatus.EMPTY -> {
                    //首次获取数据为null
                    dv.showEmpty()
                }
                NetStatus.SUCCESS -> {
                    //第一步：显示成功UI
                    dv.showSuccess()
                    //第二步：设置UI数据
                    tv.text = it.data.toString()
                }
                NetStatus.ERROR -> {
                    //显示错误信息提示
                    tv_error_msg.text = it.msg
                    //设置retry的ID控件点击事件
                    dv.showError {
                        getData(count)
                    }
                }
            }
        })
    }
}