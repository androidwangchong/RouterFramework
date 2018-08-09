package com.base.app.routerframework

import java.io.Serializable

/**
 * RouterFramework
 * Created by wangchong on 2018/7/26.
 */

class OrderAndSignature : Serializable {


    /**
     * maker : 0x6020ee58e2a695c6d696dcf0919a04c7a6d316f9
     * taker : 0x0000000000000000000000000000000000000000
     * feeRecipient : 0x0000000000000000000000000000000000000000
     * makerTokenAddress : 0x4bc8e905df73d617eb37d7880fc4751d7cb1c645
     * takerTokenAddress : 0xc778417e063141139fce010982780140aa0cd5ab
     * exchangeContractAddress : 0x479cc461fecd078f766ecc58533d6f69580cf3ac
     * salt : 89502856758062375875109081608345260133275285410476126562983197541434821065476
     * makerFee : 0
     * takerFee : 0
     * makerTokenAmount : 100000000000000000000
     * takerTokenAmount : 100000000000000
     * expirationUnixTimestampSec : 4099680000000
     * ecSignature : {"v":27,"r":"0xebba2406654b4ee2a47d524833166189d5fd59926fdc6757f9478ff29a85c29f","s":"0x712f81b59509a45fffe761718d09f3046d97afc1f840214417e9898f62a9364c"}
     */

    var maker: String? = null
    var taker: String? = null
    var feeRecipient: String? = null
    var makerTokenAddress: String? = null
    var takerTokenAddress: String? = null
    var exchangeContractAddress: String? = null
    var salt: String? = null
    var makerFee: String? = null
    var takerFee: String? = null
    var makerTokenAmount: String? = null
    var takerTokenAmount: String? = null
    var expirationUnixTimestampSec: String? = null
    var ecSignature: EcSignatureBean? = null

    class EcSignatureBean {
        /**
         * v : 27
         * r : 0xebba2406654b4ee2a47d524833166189d5fd59926fdc6757f9478ff29a85c29f
         * s : 0x712f81b59509a45fffe761718d09f3046d97afc1f840214417e9898f62a9364c
         */

        var v: String? = null
        var r: String? = null
        var s: String? = null
    }

    companion object {
        private const val serialVersionUID = 1200038664048419351L
    }
}
