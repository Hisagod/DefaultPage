package com.aib.page.net

import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

data class Resource<D> private constructor(var status: NetStatus) {
    var response: D? = null

    lateinit var msg: String

    private constructor(msg: String, status: NetStatus) : this(status) {
        this.msg = msg
    }

    private constructor(status: NetStatus, response: D) : this(status) {
        this.response = response
    }

    companion object {
        fun <D> load(msg: String = "加载中..."): Resource<D> {
            return Resource(msg, NetStatus.LOAD)
        }

        fun <D> empty(msg: String = "数据为空"): Resource<D> {
            return Resource(msg, NetStatus.EMPTY)
        }

        fun <D> success(response: D): Resource<D> {
            return Resource(NetStatus.SUCCESS, response)
        }

        fun <D> error(msg: String = "加载出错"): Resource<D> {
            return Resource(msg, NetStatus.ERROR)
        }

        fun <D> error(exception: Throwable): Resource<D> {
            val msg: String
            when (exception) {
                is ConnectException -> {
                    //数据没打开，没有联网
                    msg = "没有联网"
                }
                is SocketTimeoutException -> {
                    //连接超时,服务器关掉
                    msg = "连接超时"
                }

                is NoRouteToHostException -> {
                    //请求URL路径找不到,java.net.NoRouteToHostException: No route to host
                    msg = "加载出错"
                }

                is UnknownHostException -> {
                    msg = "服务器异常"
                }

                else -> {
                    msg = "其它问题"
                }
            }
            return Resource(msg, NetStatus.ERROR)
        }
    }
}
