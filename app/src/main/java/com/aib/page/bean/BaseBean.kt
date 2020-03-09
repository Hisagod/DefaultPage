package com.aib.page.bean

data class BaseBean<D>(
         val data: D,
         val status: Int,
         val desc: String)