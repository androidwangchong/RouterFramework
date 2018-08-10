package com.base.app.httpmodule.http.api.orderapi

import com.base.app.httpmodule.bean.contentinfo.*
import com.base.app.httpmodule.bean.request.*
import com.base.app.httpmodule.http.apiconfig.HttpHeaderConfig
import retrofit2.Call
import retrofit2.http.*

/**
 * RouterFramework
 * Created by wangchong on 2018/7/26.
 */
interface OrderApi {
    @POST("order/auth/create")
    fun orderAuthCreate(@Body orderCreateRequest: OrderCreateRequest?,
                        @HeaderMap map: Map<String, String> = HttpHeaderConfig.orderHeader())
            : Call<OrderCreateContentInfo>

    @POST("order/auth/list/open")
    fun queryOrderMy(
            @Body queryOrderMyRequest: QueryOrderMyRequest?,
            @HeaderMap map: Map<String, String> = HttpHeaderConfig.commonHeader()
    ): Call<MutableList<OrderContentInfo>>

    @POST("order/auth/cancel")
    fun orderCancel(@Body orderCancelRequest: OrderCancelRequest?,
                    @HeaderMap map: Map<String, String> = HttpHeaderConfig.commonHeader())
            : Call<OrderCancelContentInfo>

    @POST("order/market")
    fun queryTheQuotation(@Body queryQuotationRequest: QueryQuotationRequest?,
                          @HeaderMap map: Map<String, String> = HttpHeaderConfig.commonHeader())
            : Call<QueryQuotationContentInfo>
}