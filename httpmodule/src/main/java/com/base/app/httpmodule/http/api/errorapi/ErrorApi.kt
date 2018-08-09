package com.base.app.httpmodule.http.api.errorapi


/**
 * hzx-andriod
 * Created by wangchong on 2017/8/11.
 *
 *
 *
 */
object ErrorApi {
    fun getErrorMessage(code: Int?): String {
        when (code) {
            else -> {
                return ""
            }
        }
        return ""
    }

    fun getErrorMessageCN(code: String?) =
            when (code) {
                "COMMON-1001" -> {
                    "Invalid argument(s) in request"
                }
                "COMMON-1002" -> {
                    "Resource not found"
                }
                "COMMON-1003" -> {
                    "Invalid field"
                }
                "COMMON-1004" -> {
                    "Resource already exists"
                }
                "COMMON-1005" -> {
                    "Something wrong when call remote http request: %s"
                }
                "COMMON-1006" -> {
                    "Something wrong in business process"
                }
                "AUTH-1001" -> {
                    "Get a valid response when calling wechat server for auth user"
                }
                "AUTH-1002" -> {
                    "Your wechat user has been bound to this phone number"
                }
                "AUTH-1003" -> {
                    "Your phone number has been bound to other wechat user"
                }
                "AUTH-1004" -> {
                    "Invalid wechat id"
                }
                "AUTH-1005" -> {
                    "Old password should not be blank"
                }
                "AUTH-1006" -> {
                    "Invalid phone number"
                }
                "AUTH-1007" -> {
                    "Too many request"
                }
                "AUTH-1008" -> {
                    "Verification code is invalid"
                }
                "AUTH-1009" -> {
                    "Phone number already exit"
                }
                "AUTH-1010" -> {
                    "Verification code is expired"
                }
                "AUTH-1011" -> {
                    "Failed to send verification code"
                }
                "USER-1001" -> {
                    "AuthorizationInformation is expired"
                }
                "USER-1002" -> {
                    "Agent certify can't be repeat for the user has been an agent"
                }
                "USER-1003" -> {
                    "Error occurred when generating user authorized QR code"
                }
                "USER-1004" -> {
                    "Can not be authorized to yourself"
                }
                "TRANSACTION-1001" -> {
                    "The pay type is not valid"
                }
                "TRANSACTION-1002" -> {
                    "The transaction type is not valid"
                }
                "TRANSACTION-1003" -> {
                    "The statistical type is not valid"
                }
                "TRANSACTION-1004" -> {
                    "The Credit QR Code is locked"
                }
                "TRANSACTION-1005" -> {
                    "Your balance is not enough"
                }
                "TRANSACTION-1006" -> {
                    "Can not pay to yourself"
                }
                "TRANSACTION-1007" -> {
                    "Transaction must be belong to yourself"
                }
                "PAYMENT-1001" -> {
                    "AuthorizationInformation is expired"
                }
                "PAYMENT-1002" -> {
                    "Your balance is not enough"
                }
                "PAYMENT-1003" -> {
                    "The payType of this transaction is null"
                }
                "PAYMENT-1004" -> {
                    "The payType of this transaction is invalid"
                }
                "PAYMENT-1005" -> {
                    "The transactionType of this transaction is null"
                }
                "PAYMENT-1006" -> {
                    "The transactionType of this transaction is invalid"
                }
                "PAYMENT-1007" -> {
                    "Update balance failed, because optimisticLockException"
                }
                "PROFILE-1003" -> {
                    "The statistical type is not valid"
                }
                "CREDIT-BASE-1001" -> {
                    "Failed to authorize zhima credit open id"
                }
                "CREDIT-BASE-1002" -> {
                    "Failed to authorize accessing to zhima credit"
                }
                "CREDIT-BASE-1003" -> {
                    "Failed to authorize sending zhima credit sms"
                }
                "CREDIT-BASE-1004" -> {
                    "Des encrypt credit value failed"
                }
                "CREDIT-BASE-1005" -> {
                    "Des encrypt access credit request or response failed"
                }
                "CREDIT-BASE-1006" -> {
                    "Query zhima credit auth failed"
                }
                "CREDIT-BASE-1007" -> {
                    "Not support the way [%s] to authorize ZHIMA credit"
                }
                "CREDIT-BASE-1008" -> {
                    "Failed to build zhima report request params"
                }
                "CREDIT-BASE-1009" -> {
                    "Zhima url params can not be verify passed"
                }
                "CREDIT-BASE-10010" -> {
                    "Failed to build zcx url params"
                }
                "CREDIT-MODELING-1001" -> {
                    "not have error message CREDIT-MODELING-1001"
                }
                "CREDIT-MODELING-1002" -> {
                    "not have error message CREDIT-MODELING-1002"
                }
                else -> {
                    "not have error message"
                }
            }
}