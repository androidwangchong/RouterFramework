package com.base.app.httpmodule.bean.contentinfo

/**
 * RouterFramework
 * Created by wangchong on 2018/7/25.
 */

class BlockchainTransreceiptContentInfo : BaseContentInfo() {


    /**
     * blockConfirmations : string
     * blockNumber : string
     * from : string
     * input : string
     * status : string
     * to : string
     * txHash : string
     * value : string
     */

    var blockConfirmations: String? = null
    var blockNumber: String? = null
    var from: String? = null
    var input: String? = null
    var status: String? = null
    var to: String? = null
    var txHash: String? = null
    var value: String? = null

    companion object {
        private val serialVersionUID = 7547305079631711324L
    }
}
