package com.base.app.httpmodule.bean.request

import java.io.Serializable

/**
 * RouterFramework
 * Created by wangchong on 2018/7/25.
 */

class BlockchainRawtransactionRequest : Serializable {


    /**
     * data : string
     * type : string
     */

    var data: String? = null
    var type: String? = null

    companion object {
        private const val serialVersionUID = -3698424095175591570L
    }
}
