package com.base.app.httpmodule.http.apiconfig


/**
 * BaseModel
 * Created by wangchong on 2017/7/14.
 */
object HttpConfig {
    val IS_TESTING_SERVER = true
    val IS_UAT = false
    //val KANGHAO_URL = "https://homn48zsja.execute-api.us-east-1.amazonaws.com/dev/"
//    val WUSHUANG_URL = "http://hd-dex-test-213846971.us-east-1.elb.amazonaws.com/"
    val TEST_SERVER = "https://homn48zsja.execute-api.us-east-1.amazonaws.com/dev/"
//    val TEST_SERVER = "http://192.168.128.115:8080/"
    val ONLINE_SERVER = "http://baidu.com"
    val SERVER_URL = if (IS_TESTING_SERVER)
        TEST_SERVER
    else
        ONLINE_SERVER

    fun getServiceHost(): String {
        return SERVER_URL
    }

}
