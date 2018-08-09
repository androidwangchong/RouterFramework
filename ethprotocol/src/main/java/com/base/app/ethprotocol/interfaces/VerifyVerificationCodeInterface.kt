package com.base.app.ethprotocol.interfaces

import java.lang.Exception

/**
 * decentralized-exchange-app-android
 * Created by wangchong on 2018/5/23.
 */
interface VerifyVerificationCodeInterface {
    fun onSuccess()
    fun onFailure(exception: Exception?)
}