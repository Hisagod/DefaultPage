package com.aib.page.net

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    /**
     * 参数num：返回数据条数
     */
    @GET("test")
    suspend fun getData(@Query("num") num: Int): List<Int>
}