package com.aib.page.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aib.page.bean.BaseBean

/**
 * 在Activity获取ViewModel
 */
fun <D : ViewModel> AppCompatActivity.getViewModel(clazz: Class<D>): D {
    return ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(clazz)
}

/**
 * 数据转换
 */
fun <D> BaseBean<D>.dataConvert(): D {
    when (code) {
        1000 -> return data
        1001 -> throw Exception("请登录APP")
        1002 -> throw Exception("密码错误")
        else -> throw Exception(desc)
    }
}