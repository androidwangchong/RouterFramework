package com.base.app.ethprotocol.interfaces

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails

/**
 * decentralized-exchange-app-android
 * Created by wangchong on 2018/5/23.
 */
interface RegistrationResultsInterface {
    fun onRegisterSuccess(user: CognitoUser?, signUpConfirmationState: Boolean?,
                          cognitoUserCodeDeliveryDetails: CognitoUserCodeDeliveryDetails?)

    fun onRegisterFailure(exception: Exception?)
}