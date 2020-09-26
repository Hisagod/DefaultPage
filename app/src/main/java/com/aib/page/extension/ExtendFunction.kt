package com.aib.page.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * 在Activity获取ViewModel
 */
fun <D : ViewModel> AppCompatActivity.getViewModel(clazz: Class<D>): D {
    return ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(clazz)
}
