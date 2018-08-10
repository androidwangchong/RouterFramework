package com.base.app.httpmodule.http.api.orderapi

import com.base.app.httpmodule.bean.contentinfo.OrderCancelContentInfo
import com.base.app.httpmodule.bean.contentinfo.OrderContentInfo
import com.base.app.httpmodule.bean.contentinfo.OrderCreateContentInfo
import com.base.app.httpmodule.bean.contentinfo.QueryQuotationContentInfo
import com.base.app.httpmodule.bean.request.OrderCancelRequest
import com.base.app.httpmodule.bean.request.OrderCreateRequest
import com.base.app.httpmodule.bean.request.QueryOrderMyRequest
import com.base.app.httpmodule.bean.request.QueryQuotationRequest
import com.base.app.httpmodule.http.ApiFactory
import com.base.app.httpmodule.http.EnqueueCallback

/**
 * RouterFramework
 * Created by wangchong on 2018/7/26.
 */
fun orderAuthCreate(orderCreateRequest: OrderCreateRequest?) {
    ApiFactory.getOrderApi()
            ?.orderAuthCreate(orderCreateRequest)
            ?.enqueue(EnqueueCallback<OrderCreateContentInfo>())
    /**
     * 查询买单卖单
     */

}

fun queryOrderMy(baseToken:String? = "CETF",counterToken:String?= "WETH",offset:String?= "0",limit:String? = "100") {
    var queryOrderMyRequest = QueryOrderMyRequest()
    queryOrderMyRequest.baseToken =baseToken
    queryOrderMyRequest.counterToken =counterToken
    queryOrderMyRequest.offset =offset
    queryOrderMyRequest.limit =limit
    ApiFactory.getOrderApi()
            ?.queryOrderMy(queryOrderMyRequest)
            ?.enqueue(EnqueueCallback<MutableList<OrderContentInfo>>())
}

/**
 * 撤销订单
 */
fun orderCancel(orderHash: String?) {
    val orderCancelRequest = OrderCancelRequest()
    orderCancelRequest.orderHash = orderHash
    ApiFactory.getOrderApi()
            ?.orderCancel( orderCancelRequest)
            ?.enqueue(EnqueueCallback<OrderCancelContentInfo>())
}
val COIN_PAIR_TYPE = "CETF/USD"

/**
 * 查询行情接口
 */
fun queryTheQuotation(baseToken:String? = "CETF",counterToken:String?= "WETH") {
    var queryQuotationRequest = QueryQuotationRequest()
    queryQuotationRequest.baseToken =baseToken
    queryQuotationRequest.counterToken =counterToken
    ApiFactory.getOrderApi()
            ?.queryTheQuotation(queryQuotationRequest)
            ?.enqueue(EnqueueCallback<QueryQuotationContentInfo>())
}