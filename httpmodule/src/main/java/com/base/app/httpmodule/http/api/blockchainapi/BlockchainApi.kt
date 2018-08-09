package com.base.app.httpmodule.http.api.blockchainapi

import com.base.app.httpmodule.bean.contentinfo.BlockchainAllowanceBaseContentInfo
import com.base.app.httpmodule.bean.contentinfo.BlockchainPretransinfoContentInfo
import com.base.app.httpmodule.bean.contentinfo.BlockchainRawtransactionContentInfo
import com.base.app.httpmodule.bean.contentinfo.BlockchainTransreceiptContentInfo
import com.base.app.httpmodule.bean.request.BlockchainAllowanceRequest
import com.base.app.httpmodule.bean.request.BlockchainPretransinfoRequest
import com.base.app.httpmodule.bean.request.BlockchainRawtransactionRequest
import com.base.app.httpmodule.bean.request.BlockchainTransreceiptRequest
import com.base.app.httpmodule.http.apiconfig.HttpHeaderConfig
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

/**
 * decentralized-exchange-app-android
 * Created by wangchong on 2018/5/10.
 */
interface BlockchainApi {

    @POST("blockchain/allowance")
    fun blockchainAllowance(@Body blockchainAllowanceRequest: BlockchainAllowanceRequest?,
                            @HeaderMap map: Map<String, String> = HttpHeaderConfig.header())
            : Call<BlockchainAllowanceBaseContentInfo>

    @POST("blockchain/pretransinfo")
    fun blockchainPretransinfo(@Body blockchainPretransinfoRequest: BlockchainPretransinfoRequest?,
                               @HeaderMap map: Map<String, String> = HttpHeaderConfig.header())
            : Call<BlockchainPretransinfoContentInfo>

    @POST("blockchain/rawtransaction")
    fun blockchainRawtransaction(@Body blockchainRawtransactionRequest: BlockchainRawtransactionRequest?,
                                 @HeaderMap map: Map<String, String> = HttpHeaderConfig.header())
            : Call<BlockchainRawtransactionContentInfo>

    @POST("blockchain/transreceipt")
    fun blockchainTransreceipt(@Body blockchainTransreceiptRequest: BlockchainTransreceiptRequest?,
                               @HeaderMap map: Map<String, String> = HttpHeaderConfig.header())
            : Call<BlockchainTransreceiptContentInfo>

}