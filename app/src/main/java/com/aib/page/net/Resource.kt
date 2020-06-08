package com.aib.page.net

data class Resource<D> constructor(var msg: String?, var status: NetStatus, var data: D?) {
    constructor(status: NetStatus, data: D?) : this(null, status, data)

    companion object {
        fun <D> load(): Resource<D> {
            return Resource(NetStatus.LOAD, null)
        }

        fun <D> success(data: D?): Resource<D> {
            return Resource(NetStatus.SUCCESS, data)
        }

        fun <D> error(msg: String = "加载失败"): Resource<D> {
            return Resource(msg, NetStatus.ERROR, null)
        }

        fun <D> empty(): Resource<D> {
            return Resource(NetStatus.EMPTY, null)
        }
    }
}
