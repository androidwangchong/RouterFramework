package com.base.app.httpmodule.bean.request

import java.io.Serializable

/**
 * @Auth: WangZhuang
 * @CreateTime: 2018/8/9
 * @describe：
 */
class QueryOrderMyRequest : Serializable {
    var baseToken: String? = null
    var counterToken: String? = null
    var offset: String? = null
    var limit: String? = null
}