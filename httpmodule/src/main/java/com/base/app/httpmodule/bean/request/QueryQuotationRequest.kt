package com.base.app.httpmodule.bean.request

import java.io.Serializable

/**
 * @Auth: WangZhuang
 * @CreateTime: 2018/5/18
 * @describe：
 */

class QueryQuotationRequest : Serializable {
    var baseToken: String? = null
    var counterToken: String? = null
}
