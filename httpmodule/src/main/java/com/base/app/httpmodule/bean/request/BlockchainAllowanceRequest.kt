package com.base.app.httpmodule.bean.request

import java.io.Serializable

/**
 * RouterFramework
 * Created by wangchong on 2018/7/25.
 */

class BlockchainAllowanceRequest : Serializable {

    /**
     * owner : string
     * spender : string
     * tokenName : string
     */

    var owner: String? = null
    var spender: String? = null
    var tokenName: String? = null

    companion object {
        private const val serialVersionUID = 732629250008752522L
    }
}
