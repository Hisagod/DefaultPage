package com.aib.page.net

import com.aib.page.bean.BaseBean
import com.aib.page.bean.PersonBean
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("demo/getOne")
    suspend fun getOne(@Query("count") count: Int): BaseBean<List<PersonBean>>
}