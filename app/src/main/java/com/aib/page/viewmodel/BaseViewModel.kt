package com.aib.page.viewmodel

import androidx.lifecycle.ViewModel
import com.aib.page.bean.BaseBean
import com.aib.page.net.RetrofitManager

open class BaseViewModel : ViewModel() {
    protected val api by lazy { RetrofitManager.instance.getApiService() }

    /**
     * 数据转换
     */
    protected fun <D> BaseBean<D>.dataConvert(): D {
        when (status) {
            1000 -> {
                return data
            }
            else -> {
                throw Exception(desc)
            }
        }
    }
}