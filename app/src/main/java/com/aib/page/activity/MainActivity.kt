package com.aib.page.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aib.page.R
import com.aib.page.extension.getViewModel
import com.aib.page.viewmodel.MainViewModel
import com.aib.page.net.NetStatus
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val vm by lazy { getViewModel(MainViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getDataFromNet(10)

        btn_more.setOnClickListener {
            getDataFromNet(-1)
        }
    }

    private fun getDataFromNet(num: Int) {
        vm.getData(num).observe(this) {
            when (it.status) {
                NetStatus.LOAD -> {
                    dv.showLoad()
                }
                NetStatus.SUCCESS -> {
                    dv.showSuccess()
                    it.response
                }
                NetStatus.ERROR -> {
                    dv.showError {

                    }
                }
                NetStatus.EMPTY -> {
                    dv.showEmpty()
                }
            }
        }
    }
}