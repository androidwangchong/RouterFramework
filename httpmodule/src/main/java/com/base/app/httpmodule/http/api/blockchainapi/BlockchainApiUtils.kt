package com.base.app.httpmodule.http.api.blockchainapi


import com.base.app.httpmodule.bean.contentinfo.BlockchainAllowanceBaseContentInfo
import com.base.app.httpmodule.bean.contentinfo.BlockchainPretransinfoContentInfo
import com.base.app.httpmodule.bean.contentinfo.BlockchainRawtransactionContentInfo
import com.base.app.httpmodule.bean.contentinfo.BlockchainTransreceiptContentInfo
import com.base.app.httpmodule.bean.request.BlockchainAllowanceRequest
import com.base.app.httpmodule.bean.request.BlockchainPretransinfoRequest
import com.base.app.httpmodule.bean.request.BlockchainRawtransactionRequest
import com.base.app.httpmodule.bean.request.BlockchainTransreceiptRequest
import com.base.app.httpmodule.http.ApiFactory
import com.base.app.httpmodule.http.EnqueueCallback

/**
 * decentralized-exchange-app-android
 * Created by wangchong on 2018/5/10.
 */

val CETF_TYPE = "CETF"
val ETH_TYPE = "ETH"


fun blockchainAllowance(address: String, spender: String = "0x4e9aad8184de8833365fea970cd9149372fdf1e6", tokenName: String = "CETF") {
    var blockchainAllowanceRequest = BlockchainAllowanceRequest()
    blockchainAllowanceRequest.owner = address
    blockchainAllowanceRequest.spender = spender
    blockchainAllowanceRequest.tokenName = tokenName
    ApiFactory.getBlockchainApi()
            ?.blockchainAllowance(blockchainAllowanceRequest)
            ?.enqueue(EnqueueCallback<BlockchainAllowanceBaseContentInfo>())

}


fun blockchainPretransinfo(address: String, type: String = "ETH", tokenName: String = "CETF") {
    val blockchainPretransinfoRequest = BlockchainPretransinfoRequest()
    blockchainPretransinfoRequest.address = address
    blockchainPretransinfoRequest.type = type
    blockchainPretransinfoRequest.tokenName = tokenName
    ApiFactory.getBlockchainApi()
            ?.blockchainPretransinfo(blockchainPretransinfoRequest)
            ?.enqueue(EnqueueCallback<BlockchainPretransinfoContentInfo>())
}

fun blockchainRawtransaction(data: String, type: String = "ETH") {
    val blockchainRawtransaction = BlockchainRawtransactionRequest()
    blockchainRawtransaction.data = data
    blockchainRawtransaction.type = type
    ApiFactory.getBlockchainApi()
            ?.blockchainRawtransaction(blockchainRawtransaction)
            ?.enqueue(EnqueueCallback<BlockchainRawtransactionContentInfo>())
}

fun blockchainTransreceipt(hash: String?, type: String = "ETH") {
    val blockchainTransreceipt = BlockchainTransreceiptRequest()
    blockchainTransreceipt.hash = hash
    blockchainTransreceipt.type = type
    ApiFactory.getBlockchainApi()
            ?.blockchainTransreceipt(blockchainTransreceipt)
            ?.enqueue(EnqueueCallback<BlockchainTransreceiptContentInfo>())
}

