package com.aib.page.net

import com.aib.page.bean.BaseBean
import com.aib.page.bean.WeatherBean
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(NetConstant.WHEATHER)
    suspend fun getData(@Query("city") city: String?): BaseBean<WeatherBean>
}