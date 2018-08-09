package com.base.app.ethprotocol.interfaces

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import java.lang.Exception

/**
 * decentralized-exchange-app-android
 * Created by wangchong on 2018/5/23.
 */
interface ResendConfCodeInterface {
    fun onResendConfCodeSuccess(cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails?)
    fun onResendConfCodeFailure(exception: Exception?)
}