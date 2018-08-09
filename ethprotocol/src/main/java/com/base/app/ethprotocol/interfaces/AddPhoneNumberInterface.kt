package com.base.app.ethprotocol.interfaces

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails
import java.lang.Exception

/**
 * decentralized-exchange-app-android
 * Created by wangchong on 2018/5/23.
 */
interface AddPhoneNumberInterface {
    fun onAddPhoneNumberSuccess(attributesVerificationList: MutableList<CognitoUserCodeDeliveryDetails>?)
    fun onAddPhoneNumberFailure(exception: Exception?)
}