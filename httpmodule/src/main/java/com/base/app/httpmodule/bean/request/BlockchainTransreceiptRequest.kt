package com.base.app.httpmodule.bean.request

import java.io.Serializable

/**
 * RouterFramework
 * Created by wangchong on 2018/7/25.
 */

class BlockchainTransreceiptRequest : Serializable {


    /**
     * hash : string
     * type : string
     */

    var hash: String? = null
    var type: String? = null

    companion object {
        private const val serialVersionUID = 3462427851016176580L
    }
}
