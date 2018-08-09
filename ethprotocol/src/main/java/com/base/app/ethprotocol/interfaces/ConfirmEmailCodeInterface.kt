package com.base.app.ethprotocol.interfaces

import java.lang.Exception

/**
 * decentralized-exchange-app-android
 * Created by wangchong on 2018/5/23.
 */
interface ConfirmEmailCodeInterface {

    fun onConfirmEmailCodeSuccess()
    fun onConfirmEmailCodeFailure(exception: Exception?)
}