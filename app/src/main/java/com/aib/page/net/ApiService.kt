package com.aib.page.net

import com.aib.page.bean.BaseBean
import com.aib.page.bean.PersonBean
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    /**
     * count: -1(返回出错数据)    0(不反返回数据)   大于0（返回几条数据）
     */
    @GET("demo/getOne")
    suspend fun getOne(@Query("count") count: Int): BaseBean<List<PersonBean>>
}