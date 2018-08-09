package com.base.app.ethprotocol.interfaces

import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation
import java.lang.Exception

/**
 * decentralized-exchange-app-android
 * Created by wangchong on 2018/6/11.
 */
interface ForgotPasswordInterface {
    fun onForgotPasswordSuccess()

    fun onForgotPasswordFailure(p0: Exception?)

    fun getForgotPasswordResetCode(p0: ForgotPasswordContinuation?)
}