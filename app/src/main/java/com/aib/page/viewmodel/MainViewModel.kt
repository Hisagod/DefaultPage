package com.aib.page.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aib.page.bean.PersonBean
import com.aib.page.extension.dataConvert
import com.aib.page.net.Resource
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {
    fun getOne(count:Int): LiveData<Resource<List<PersonBean>>> {
        val data = MutableLiveData<Resource<List<PersonBean>>>()
        data.value = Resource.load()
        viewModelScope.launch {
            runCatching {
                val bean = api
                        .getOne(count)
                        .dataConvert()
                if (bean.isNullOrEmpty()) {
                    data.value = Resource.empty()
                } else {
                    data.value = Resource.success(bean)
                }
            }.onFailure {
                data.value = Resource.error(it.message ?: "加载失败")
                Log.w("HLP",it)
            }
        }
        return data
    }
}