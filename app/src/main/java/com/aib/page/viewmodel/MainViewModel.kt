package com.aib.page.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.aib.page.net.Resource
import com.aib.page.net.RetrofitManager
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val api by lazy { RetrofitManager.instance.getApiService() }

    fun getData(num: Int): LiveData<Resource<List<Int>>> {
        val data = MutableLiveData<Resource<List<Int>>>()
        data.value = Resource.load()
        viewModelScope.launch {
            runCatching {
                val bean = api.getData(num)
                if (bean.isNullOrEmpty()) {
                    data.value = Resource.empty()
                } else {
                    data.value = Resource.success(bean)
                }
            }.onFailure {
                Log.e("HLP", it.toString())
                data.value = Resource.error(it)
            }
        }
        return data
    }
}