package com.base.app.httpmodule.bean.request

import java.io.Serializable

/**
 * decentralized-exchange-app-android
 * Created by wangchong on 2018/5/17.
 */

class OrderCancelRequest : Serializable {


    /**
     * id : orderId
     */

    var orderHash: String? = null

    companion object {
        private const val serialVersionUID = -7731072928250873118L
    }
}
