package com.base.app.httpmodule.http

import com.base.app.httpmodule.http.api.blockchainapi.BlockchainApi
import com.base.app.httpmodule.http.api.orderapi.OrderApi
import com.base.app.httpmodule.http.demoApi.DemoAPI

/**
 * BaseModel
 * Created by wangchong on 2017/7/14.
 */
object ApiFactory {

    private var demoAPI: DemoAPI? = null
    private var blockchainApi: BlockchainApi? = null
    private var orderApi: OrderApi? = null

    fun getDemoAPI(): DemoAPI? {
        if (demoAPI == null) {
            demoAPI = RetrofitClient.retrofit.create<DemoAPI>(DemoAPI::class.java)
        }
        return demoAPI
    }

    fun getBlockchainApi(): BlockchainApi? {
        if (blockchainApi == null) {
            blockchainApi = RetrofitClient.retrofit.create<BlockchainApi>(BlockchainApi::class.java)
        }
        return blockchainApi
    }

    fun getOrderApi(): OrderApi? {
        if (orderApi == null) {
            orderApi = RetrofitClient.retrofit.create<OrderApi>(OrderApi::class.java)
        }
        return orderApi
    }


}
