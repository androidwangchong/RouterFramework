package com.base.app.httpmodule.bean.request

import java.io.Serializable

/**
 * RouterFramework
 * Created by wangchong on 2018/7/25.
 */

class BlockchainPretransinfoRequest : Serializable {


    /**
     * address : 064a724b35c80387dc72c5cd88ccb5d6ed7a1b6e
     * tokenName : CETF
     * type : ETH
     */

    var address: String? = null
    var tokenName: String? = null
    var type: String? = null

    companion object {
        private const val serialVersionUID = 6007345788942266144L
    }
}
