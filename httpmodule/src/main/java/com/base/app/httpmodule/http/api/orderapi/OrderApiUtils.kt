package com.base.app.httpmodule.http.api.orderapi

import com.base.app.httpmodule.bean.contentinfo.OrderCreateContentInfo
import com.base.app.httpmodule.bean.request.OrderCreateRequest
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
}