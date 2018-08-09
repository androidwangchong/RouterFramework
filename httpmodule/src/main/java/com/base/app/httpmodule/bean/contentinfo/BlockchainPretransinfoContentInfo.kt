package com.base.app.httpmodule.bean.contentinfo

/**
 * RouterFramework
 * Created by wangchong on 2018/7/25.
 */

class BlockchainPretransinfoContentInfo : BaseContentInfo() {


    /**
     * contractAddress : string
     * gasLimit : string
     * gasPrice : string
     * methodId : string
     * nonce : string
     */

    var contractAddress: String? = null
    var gasLimit: String? = null
    var gasPrice: String? = null
    var methodId: String? = null
    var nonce: String? = null

    companion object {
        private val serialVersionUID = 2997760987978049393L
    }
}
