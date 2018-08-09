package com.base.app.httpmodule.http.api.orderapi

import com.base.app.httpmodule.bean.contentinfo.BlockchainAllowanceBaseContentInfo
import com.base.app.httpmodule.bean.contentinfo.OrderCreateContentInfo
import com.base.app.httpmodule.bean.request.BlockchainAllowanceRequest
import com.base.app.httpmodule.bean.request.OrderCreateRequest
import com.base.app.httpmodule.http.apiconfig.HttpHeaderConfig
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

/**
 * RouterFramework
 * Created by wangchong on 2018/7/26.
 */
interface OrderApi {
    @POST("order/auth/create")
    fun orderAuthCreate(@Body orderCreateRequest: OrderCreateRequest?,
                    @HeaderMap map: Map<String, String> = HttpHeaderConfig.orderHeader())
            : Call<OrderCreateContentInfo>

}