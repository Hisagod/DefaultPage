package com.aib.page.bean

data class BaseBean<D>(
         val data: D,
         val code: Int,
         val desc: String)