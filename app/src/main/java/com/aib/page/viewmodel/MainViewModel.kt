package com.aib.page.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aib.page.bean.WeatherBean
import com.aib.page.net.Resource
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel() {
    fun getWeatherData(city: String): LiveData<Resource<WeatherBean>> {
        val data = MutableLiveData<Resource<WeatherBean>>()
        data.value = Resource.load()
        viewModelScope.launch {
            runCatching {
                val weather = api
                        .getData(city)
                        .dataConvert()
                data.value = Resource.success(weather)
            }.onFailure {
                data.value = Resource.error(it.message ?: "加载失败")
            }
        }
        return data
    }
}