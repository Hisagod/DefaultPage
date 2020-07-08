package com.aib.page

import android.app.Application
import com.aib.other.DefaultPage

class PageApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        //初始化全局布局文件
        DefaultPage.init(R.layout.load, R.layout.empty, R.layout.error)
    }
}