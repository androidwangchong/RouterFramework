package com.base.app.httpmodule.bean.request

import org.json.JSONObject
import java.io.Serializable

/**
 * RouterFramework
 * Created by wangchong on 2018/7/26.
 */

class OrderCreateRequest : Serializable {
    companion object {
        private const val serialVersionUID = -6567781352860843593L
    }

    var baseToken: String? = null
    var counterToken: String? = null
    var orderType: String? = null
    var amount: String? = null
    var price: String? = null
    var fee: String? = null
    var signedJson: com.alibaba.fastjson.JSONObject? = null
    var orderHash: String? = null
}
