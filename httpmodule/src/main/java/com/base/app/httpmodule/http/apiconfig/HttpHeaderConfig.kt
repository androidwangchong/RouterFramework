package com.base.app.httpmodule.http.apiconfig

import com.base.app.basemodule.utils.SPUtils
import com.base.app.basemodule.utils.SPUtils.MESSAGE
import com.base.app.basemodule.utils.SPUtils.SIGNATURE
import com.base.app.basemodule.app.MyApplication


/**
 * BaseModel
 * Created by wangchong on 2017/7/20.
 */
object HttpHeaderConfig {

    fun loginHeader(): Map<String, String> {
        val map = mutableMapOf<String, String>().apply {
            put("User-Agent", "ttdevs") //https://developer.github.com/v3/#user-agent-required
            put("Content-Type", "application/json; charset=utf-8")
            put("Accept", "application/json")
            put("Accept", "application/vnd.github.v3+json") //https://developer.github.com/v3/#current-version
            put("token", "eATQzos9vG9hFK4Uk218")
            put("Time-Zone", "Asia/Shanghai") //https://developer.github.com/v3/#timezones
            put("user_key", "haha")
        }

        return map
    }

    fun commonHeader(): Map<String, String> {
        val map = mutableMapOf<String, String>().apply {
//            put("urlname", "wushuang")
            put("Content-Type", "application/json")
//            put("Accept", "application/json")
            put("auth-message", SPUtils[MyApplication.instance(), MESSAGE, ""] as String)
            put("auth-signature", SPUtils[MyApplication.instance(), SIGNATURE, ""] as String)
//            if (MyApplication.instance().getOauthToken().isNullOrEmpty()) {
//                put("Authorization", "Basic aHktY2xpZW50Omh5LWNsaWVudA==")
//            } else {
//                put("Authorization", "${MyApplication.instance().getOauthToken()}".trim())
//            }
        }

        return map
    }


    fun header(): Map<String, String> {
        val map = mutableMapOf<String, String>().apply {
//            put("urlname", "kanghao")
            put("Content-Type", "application/json")
//            put("Accept", "application/json")
//            put("auth-message", SPUtils[MyApplication.instance(), MESSAGE, ""] as String)
//            put("auth-signature", SPUtils[MyApplication.instance(), SIGNATURE, ""] as String)
        }
        return map
    }

    fun orderHeader(): Map<String, String> {
        val map = mutableMapOf<String, String>().apply {
//            put("urlname", "kanghao")
            put("Content-Type", "application/json")
//            put("Accept", "application/json")
            put("auth-message", SPUtils[MyApplication.instance(), MESSAGE, ""] as String)
            put("auth-signature", SPUtils[MyApplication.instance(), SIGNATURE, ""] as String)
        }
        return map
    }


}