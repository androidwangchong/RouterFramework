package com.base.app.ethprotocol.outsidecalls

import com.base.app.basemodule.utils.SPUtils
import android.util.Log
import com.alibaba.fastjson.JSON
import com.base.app.basemodule.app.MyApplication
import com.base.app.basemodule.baseactivity.BaseActivity
import com.base.app.ethprotocol.event.SignOwnerShipResults
import com.base.app.ethprotocol.utils.ExchangeCalculator
import com.base.app.ethprotocol.utils.WalletStorage
import com.orhanobut.logger.Logger
import de.greenrobot.event.EventBus
import org.spongycastle.util.encoders.Hex
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.core.methods.request.RawTransaction
import org.web3j.utils.Numeric
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*
import org.web3j.crypto.*


/**
 * RouterFramework
 * Created by wangchong on 2018/7/25.
 */


fun signOwnerShip(baesActivity: BaseActivity, address: String) {
    val keys = WalletStorage.getInstance(baesActivity)
            .getFullWallet(baesActivity, "Passw0rd!", address) ?: ""
    Log.d("keys", keys.toString())
    var m = "QuantBull-Login-Confirm-" + System.currentTimeMillis() + "."
    Log.d("m", m)
//        val hash = Hash.sha3(Numeric.toHexStringNoPrefix(m.toByteArray()))
//        Log.d("hash", hash)


    val message = "\u0019Ethereum Signed Message:\n" + m.length + m

    val data = message.toByteArray()
    val signature = Sign.signMessage(data, (keys as Credentials).ecKeyPair)

    Log.d("R", Numeric.toHexString(signature.r).toString())
    Log.d("S", Numeric.toHexString(signature.s).toString())
    Log.d("V", Integer.toString(signature.v.toInt()).toString())


    var signatureStr = Numeric.toHexString(signature.r).toString() + Numeric.toHexString(signature.s).toString().replace("0x", "") + Integer.toHexString(signature.v.toInt()).toString()
    Log.d("signatureStr", signatureStr)
    ethAuthTest(m, signatureStr, address, baesActivity)
}

@Throws(Exception::class)
fun ethAuthTest(message: String, signature: String, baseAddress: String, baeActivity: BaseActivity) {
//        var message = "QuantBull-Login-Confirm-1532326539619."
//        var signature: String? = "0x2f44790ead5acaf195c9460ca5b2ededb3a9a02191cbfb0f67d11697f9d642f70328ebd72af7210927916b7a90e608fe494679e87ec6801fdd723bca481617c71c"
    var message = message
    var spmessage = message
    var signature = signature
    var spsignature = signature

    message = "\u0019Ethereum Signed Message:\n" + message.length + message

    if (signature != null && signature.startsWith("0x")) {
        signature = signature.substring(2)
    }

    val rsv = BigInteger(signature, 16).toByteArray()
    val rsv_offset = rsv.size - 65
    val v = rsv[64 + rsv_offset]
    val r = Arrays.copyOfRange(rsv, 0 + rsv_offset, 32 + rsv_offset)
    val s = Arrays.copyOfRange(rsv, 32 + rsv_offset, 64 + rsv_offset)

    val data = Sign.SignatureData(v, r, s)

    Log.d("data R", Numeric.toHexString(data.r))
    Log.d("data S", Numeric.toHexString(data.s))
    Log.d("data V", Integer.toString(data.v.toInt()))


    val key = Sign.signedMessageToKey(message.toByteArray(), data)
    var address = Keys.getAddress(key)

    if (!address.startsWith("0x")) {
        address = "0x" + address
    }

    Log.d("address", address)
    if (baseAddress == address) {
        SPUtils.put(baeActivity, SPUtils.MESSAGE, spmessage)
        SPUtils.put(baeActivity, SPUtils.SIGNATURE, spsignature)
        val signOwnerShipResults = SignOwnerShipResults()
        signOwnerShipResults.isSuccessful = true
        EventBus.getDefault().post(signOwnerShipResults)
    } else {
        val signOwnerShipResults = SignOwnerShipResults()
        signOwnerShipResults.isSuccessful = false
        EventBus.getDefault().post(signOwnerShipResults)
    }
}


fun doUnLock(
        fromAddress: String?, //sellBaseCoinAddress
        contractAddress: String?, //buyBaseCoinAddress
        nonce: Int?,
        gas_price: String?,
        gas_limit: String?, //20000
        password: String? = "Passw0rd!"): String {
    val keys = WalletStorage.getInstance(MyApplication.instance())
            .getFullWallet(MyApplication.instance(), password, fromAddress) ?: return ""
    //    val keys = Credentials.create("3a90075e46cfe98515d357a8f47f12c13c5163ffc04ace876769de767807066e")
    //    val keys = Credentials.create("237a9705de2a2ef4dca5f24081fde9c2f15c2f17b7f4b8ec256e31e657dd8828")
//    val decAmount = BigDecimal(amount).multiply(BigDecimal(Math.pow(10.toDouble(), decimals?.toDouble()!!))).toBigInteger()
//    val hexAmount = decAmount.toString(16)
//    val leftFillingAmountFor32Byte = FillForString.addZeroForNum(hexAmount, 64)
//    val leftFillingToAddressFor32Byte = FillForString.addZeroForNum(toAddress?.replace("0x", ""), 64)
//    val dataFor68Byte = methodId + leftFillingToAddressFor32Byte + leftFillingAmountFor32Byte

//    val  balanceFunction = Function(
//            "approve",
//            Arrays.asList(Address("4e9aad8184de8833365fea970cd9149372fdf1e6"),
//                    Uint256(BigInteger("ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff", 16))) as List<Type<Any>>?,
//            Collections.emptyList<TypeReference<*>>())
//
//    val encodedFunction = FunctionEncoder.encode(balanceFunction)
//    Logger.d(encodedFunction)

    val dataFor68Byte = "0x095ea7b3" + "0000000000000000000000004e9aad8184de8833365fea970cd9149372fdf1e6" +
            "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff"




    Logger.d(dataFor68Byte)


    val tx = RawTransaction.createTransaction(
            BigInteger(nonce.toString()),
            BigInteger(gas_price?.replace("0x", ""), 16),
            BigInteger(gas_limit?.replace("0x", ""), 16),
            contractAddress,
            BigDecimal(0).multiply(ExchangeCalculator.ONE_ETHER).toBigInteger(),
            dataFor68Byte
    )

    Logger.d(
            "Nonce: " + tx.nonce + "\n" +
                    "gasPrice: " + tx.gasPrice + "\n" +
                    "gasLimit: " + tx.gasLimit + "\n" +
                    "To: " + tx.to + "\n" +
                    "Amount: " + tx.value + "\n" +
                    "Data: " + tx.data
    )

    val signed = TransactionEncoder.signMessage(tx, 3.toByte(), keys)
    val data = "0x" + Hex.toHexString(signed)
    return data
}

fun placeTheOrder(baesActivity: BaseActivity, address: String, orderString: String): Sign.SignatureData {
    val hash = Hash.sha3(Numeric.toHexStringNoPrefix(orderString.toByteArray()))
    Logger.d("hash", hash)
    val keys = WalletStorage.getInstance(baesActivity)
            .getFullWallet(baesActivity, "Passw0rd!", address) ?: ""
    val signature = Sign.signMessage(hash.toByteArray(), (keys as Credentials).ecKeyPair)

    Log.d("R", Numeric.toHexString(signature.r).toString())
    Log.d("S", Numeric.toHexString(signature.s).toString())
    Log.d("V", Integer.toString(signature.v.toInt()).toString())
    return signature

}

fun generatePseudoRandomSalt(): String {
    // BigNumber.random returns a pseudo-random number between 0 & 1 with a passed in number of decimal places.
    // Source: https://mikemcl.github.io/bignumber.js/#random
    var random = Random()
    var result = ""
    for (i in 0..76) {
        result += random.nextInt(10)
    }
    return result
}

/**
 * 充值
 */
fun depositToData(fromAddress: String?, //sellBaseCoinAddress
                  contractAddress: String?, //buyBaseCoinAddress
                  nonce: Int?,
                  gas_price: String?,
                  gas_limit: String?, //20000
                  value: String?, //0.5
                  password: String? = "Passw0rd!"): String {
    val keys = WalletStorage.getInstance(MyApplication.instance())
            .getFullWallet(MyApplication.instance(), password, fromAddress) ?: return ""
    //    val keys = Credentials.create("3a90075e46cfe98515d357a8f47f12c13c5163ffc04ace876769de767807066e")
//    val decAmount = BigDecimal(amount).multiply(BigDecimal(Math.pow(10.toDouble(), decimals?.toDouble()!!))).toBigInteger()
//    val hexAmount = decAmount.toString(16)
//    val leftFillingAmountFor32Byte = FillForString.addZeroForNum(hexAmount, 64)
//    val leftFillingToAddressFor32Byte = FillForString.addZeroForNum(toAddress?.replace("0x", ""), 64)
//    val dataFor68Byte = methodId + leftFillingToAddressFor32Byte + leftFillingAmountFor32Byte

//    val  balanceFunction = org.web3j.abi.datatypes.Function(
//            "deposit",
//            Arrays.asList(Address(""),
//                    Uint256(BigInteger("0", 16))) as List<Type<Any>>?,
//            Collections.emptyList<TypeReference<*>>())
//
//    val encodedFunction = FunctionEncoder.encode(balanceFunction)
//    Logger.d(encodedFunction)


    val dataFor68Byte = "0xd0e30db0"

    Logger.d(dataFor68Byte)


    val tx = RawTransaction.createTransaction(
            BigInteger(nonce.toString()),
            BigInteger(gas_price?.replace("0x", ""), 16),
            BigInteger(gas_limit?.replace("0x", ""), 16),
            contractAddress,
            BigDecimal(value).multiply(ExchangeCalculator.ONE_ETHER).toBigInteger(),
            dataFor68Byte
    )

    Logger.d(
            "Nonce: " + tx.nonce + "\n" +
                    "gasPrice: " + tx.gasPrice + "\n" +
                    "gasLimit: " + tx.gasLimit + "\n" +
                    "To: " + tx.to + "\n" +
                    "Amount: " + tx.value + "\n" +
                    "Data: " + tx.data
    )

    val signed = TransactionEncoder.signMessage(tx, 3.toByte(), keys)
    val data = "0x" + Hex.toHexString(signed)
    return data
}

fun withdrawToData(fromAddress: String?, //sellBaseCoinAddress
                   contractAddress: String?, //buyBaseCoinAddress
                   nonce: Int?,
                   gas_price: String?,
                   gas_limit: String?, //20000
                   password: String? = "Passw0rd!"): String {
    val keys = WalletStorage.getInstance(MyApplication.instance())
            .getFullWallet(MyApplication.instance(), password, fromAddress) ?: return ""
    //    val keys = Credentials.create("3a90075e46cfe98515d357a8f47f12c13c5163ffc04ace876769de767807066e")
//    val decAmount = BigDecimal(amount).multiply(BigDecimal(Math.pow(10.toDouble(), decimals?.toDouble()!!))).toBigInteger()
//    val hexAmount = decAmount.toString(16)
//    val leftFillingAmountFor32Byte = FillForString.addZeroForNum(hexAmount, 64)
//    val leftFillingToAddressFor32Byte = FillForString.addZeroForNum(toAddress?.replace("0x", ""), 64)
//    val dataFor68Byte = methodId + leftFillingToAddressFor32Byte + leftFillingAmountFor32Byte

    val  balanceFunction = org.web3j.abi.datatypes.Function(
            "withdraw",
            Arrays.asList(
                    Uint256(BigInteger("64", 16))) as List<Type<Any>>?,
            Collections.emptyList<TypeReference<*>>())

    val dataFor68Byte = FunctionEncoder.encode(balanceFunction)
    Logger.d(dataFor68Byte)


    Logger.d(dataFor68Byte)


    val tx = RawTransaction.createTransaction(
            BigInteger(nonce.toString()),
            BigInteger(gas_price?.replace("0x", ""), 16),
            BigInteger(gas_limit?.replace("0x", ""), 16),
            contractAddress,
            BigDecimal(0).multiply(ExchangeCalculator.ONE_ETHER).toBigInteger(),
            dataFor68Byte
    )

    Logger.d(
            "Nonce: " + tx.nonce + "\n" +
                    "gasPrice: " + tx.gasPrice + "\n" +
                    "gasLimit: " + tx.gasLimit + "\n" +
                    "To: " + tx.to + "\n" +
                    "Amount: " + tx.value + "\n" +
                    "Data: " + tx.data
    )

    val signed = TransactionEncoder.signMessage(tx, 3.toByte(), keys)
    val data = "0x" + Hex.toHexString(signed)
    return data
}