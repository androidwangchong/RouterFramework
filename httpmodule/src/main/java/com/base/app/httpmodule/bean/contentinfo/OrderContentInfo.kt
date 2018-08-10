package com.base.app.httpmodule.bean.contentinfo

import java.io.Serializable

/**
 * @Auth: WangZhuang
 * @CreateTime: 2018/8/10
 * @describe：
 */

class OrderContentInfo : Serializable {
    /**
     * count : 8
     * list : [{"orderType":"BUY","amount":742,"counterToken":"WETH","createTime":"2018-08-10T05:53:01.000+0000","baseToken":"CETF","price":100,"orderHash":"0x762fefd9a41cc8925bd875753260f5a6bc4c801a01c341dde178e511eb10fea0"},{"orderType":"BUY","amount":66,"counterToken":"WETH","createTime":"2018-08-10T05:52:51.000+0000","baseToken":"CETF","price":100,"orderHash":"0x973ddaec76cabf7e873ca1a92d50262074df79531a5b62748791df2923c92de5"}]
     */

    var count: Int = 0
    var list: ArrayList<ListBean>? = ArrayList()

    class ListBean : Serializable {
        /**
         * orderType : BUY
         * amount : 742
         * counterToken : WETH
         * createTime : 2018-08-10T05:53:01.000+0000
         * baseToken : CETF
         * price : 100
         * orderHash : 0x762fefd9a41cc8925bd875753260f5a6bc4c801a01c341dde178e511eb10fea0
         */

        var orderType: String? = null
        var amount: Int = 0
        var counterToken: String? = null
        var createTime: String? = null
        var baseToken: String? = null
        var price: Int = 0
        var orderHash: String? = null
    }

    companion object {

        private const val serialVersionUID = 6918532810825178170L
    }
}
