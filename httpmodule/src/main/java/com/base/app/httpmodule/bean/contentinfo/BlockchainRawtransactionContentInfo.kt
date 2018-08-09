package com.base.app.httpmodule.bean.contentinfo

/**
 * RouterFramework
 * Created by wangchong on 2018/7/25.
 */

class BlockchainRawtransactionContentInfo : BaseContentInfo() {


    /**
     * txHash : string
     * type : string
     */

    var txHash: String? = null
    var type: String? = null

    companion object {
        private val serialVersionUID = -752196784617901009L
    }
}
