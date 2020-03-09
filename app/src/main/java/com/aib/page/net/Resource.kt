package com.aib.page.net

data class Resource<D> constructor(var msg: String, var status: NetStatus, var data: D?) {
    companion object {
        fun <D> load(msg: String = "加载中..."): Resource<D> {
            return Resource(msg, NetStatus.LOAD, null)
        }

        fun <D> success(data: D?): Resource<D> {
            return Resource("加载成功", NetStatus.SUCCESS, data)
        }

        fun <D> error(msg: String = "加载失败"): Resource<D> {
            return Resource(msg, NetStatus.ERROR, null)
        }

        fun <D> empty(msg: String = "数据为空"): Resource<D> {
            return Resource(msg, NetStatus.EMPTY, null)
        }
    }
}
