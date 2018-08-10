package com.base.app.routerframework

import android.content.Intent
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.alibaba.fastjson.JSON
import com.base.app.R
import com.base.app.basemodule.app.MyApplication
import com.base.app.basemodule.baseactivity.BaseActivity
import com.base.app.basemodule.utils.SPUtils
import com.base.app.basemodule.utils.SPUtils.WALLET_PASSWORD
import com.base.app.basemodule.utils.clickThrottleFirst
import com.base.app.basemodule.widget.Toaster
import com.base.app.ethprotocol.data.bean.CreateETHAdressSucced
import com.base.app.ethprotocol.event.SignOwnerShipResults
import com.base.app.ethprotocol.interfaces.StorableWallet
import com.base.app.ethprotocol.outsidecalls.*
import com.base.app.ethprotocol.services.WalletGenService
import com.base.app.ethprotocol.utils.WalletStorage
import com.base.app.httpmodule.bean.contentinfo.*
import com.base.app.httpmodule.bean.request.OrderCreateRequest
import com.base.app.httpmodule.event.ErrorContentInfo
import com.base.app.httpmodule.http.api.blockchainapi.blockchainAllowance
import com.base.app.httpmodule.http.api.blockchainapi.blockchainPretransinfo
import com.base.app.httpmodule.http.api.blockchainapi.blockchainRawtransaction
import com.base.app.httpmodule.http.api.blockchainapi.blockchainTransreceipt
import com.base.app.httpmodule.http.api.orderapi.orderAuthCreate
import com.base.app.httpmodule.http.api.orderapi.orderCancel
import com.base.app.httpmodule.http.api.orderapi.queryOrderMy
import com.base.app.httpmodule.http.api.orderapi.queryTheQuotation
import com.orhanobut.logger.Logger
import io.reactivex.rxkotlin.toSingle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.json.JSONObject
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Hash
import org.web3j.utils.Numeric
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity() {

    var IS_IMPORT_ACCOUNT = false
    var retryblockNumber = 1
    var isdeposit = false
    var lastPrice = 0f
    var buyAndSellNum = 0
    var isBuy = true
    var orderlist: ArrayList<OrderContentInfo.ListBean>? = ArrayList()
    override val title: String by lazy {
        ""
    }
    override val layoutResId: Int = R.layout.activity_main

    override fun getIntentMessageData() {

    }

    override fun initView() {
        val storedwallets = java.util.ArrayList<StorableWallet>(WalletStorage.getInstance(this@MainActivity).get())
        if (storedwallets != null && storedwallets.size > 0) {
            textView.text = storedwallets[0].pubKey
            Logger.d("address ${storedwallets[0].pubKey}")
            IS_IMPORT_ACCOUNT = true
        } else {
            textView.text = "还未导入钱包！"
            IS_IMPORT_ACCOUNT = false
        }
        //获取用户当前lock状态
        blockchainAllowance(textView.text.toString())

        clickThrottleFirst(tv_import_account) {
            importAccount()
        }

        clickThrottleFirst(sign) {
            isImportAccount(
                    trueToDo = {
                        signOwnerShip(this@MainActivity, textView.text.toString())
                    },
                    falseToDo = {
                        Toaster.showCenter(this@MainActivity, "还未导入钱包！")
                    }
            )
        }

        clickThrottleFirst(unlock) {
            isImportAccount(
                    trueToDo = {
                        //解锁
                        blockchainPretransinfo(textView.text.toString())
                    },
                    falseToDo = {
                        Toaster.showCenter(this@MainActivity, "还未导入钱包！")
                    }
            )
        }

        clickThrottleFirst(buy_order) {
            countDownTimer.start()
            //        var s = "{"maker":"0x6020ee58e2a695c6d696dcf0919a04c7a6d316f9"
//        ,"taker":"0x0000000000000000000000000000000000000000"
//        ,"feeRecipient":"0x0000000000000000000000000000000000000000"
//        ,"makerTokenAddress":"0x4bc8e905df73d617eb37d7880fc4751d7cb1c645"
//        ,"takerTokenAddress":"0xc778417e063141139fce010982780140aa0cd5ab"
//        ,"exchangeContractAddress":"0x479cc461fecd078f766ecc58533d6f69580cf3ac"
//        ,"salt":"89502856758062375875109081608345260133275285410476126562983197541434821065476"
//        ,"makerFee":"0"
//        ,"takerFee":"0"
//        ,"makerTokenAmount":"100000000000000000000"
//        ,"takerTokenAmount":"100000000000000"
//        ,"expirationUnixTimestampSec":"4099680000000"}"


        }

        clickThrottleFirst(deposit) {
            //充值
            //获取基本信息
            isdeposit = true
            showProgressBarDialog("正在充值")
            blockchainPretransinfo(textView.text.toString())
        }

        clickThrottleFirst(withdraw) {
            //提现
            //获取基本信息
            isdeposit = false
            showProgressBarDialog("正在提现")
            blockchainPretransinfo(textView.text.toString())
        }


    }

    override fun initData() {

    }

    override fun onPause() {
        super.onPause()
        countDownTimer.cancel()
    }


    fun importAccount(): Unit {
        //导入账户
        val generatingService = Intent(this@MainActivity, WalletGenService::class.java)
        generatingService.putExtra("PASSWORD", et_keystore_password.text.toString())
        generatingService.putExtra("NAME", "wangchong")
        if (!TextUtils.isEmpty(et_keystore_content.text.toString()))
            generatingService.putExtra("PRIVATE_KEY", et_keystore_content.text.toString())
        startService(generatingService)
    }


    override fun onEvent(event: Any) {
        when (event) {
            is BlockchainAllowanceBaseContentInfo -> {
                when {
                    event.remaining?.toFloat()!! > 0 -> {
                        //解锁
                        unlock.text = "unlock"
                        unlock.isEnabled = false
                    }
                    else -> {
                        //未解锁
                        unlock.text = "lock"
                    }
                }
            }
            is CreateETHAdressSucced -> {
                if (5 == event.state) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "导入成功", Toast.LENGTH_SHORT).show()
                        var keystore_password = et_keystore_password.text.toString().trim()

                        SPUtils.put(this, WALLET_PASSWORD, keystore_password)
                    }
                } else if (0 == event.state) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "导入0", Toast.LENGTH_SHORT).show()

                    }
                } else if (2 == event.state) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "导入2", Toast.LENGTH_SHORT).show()

                    }

                } else {

                }
            }
            is SignOwnerShipResults -> {
                if (event.isSuccessful) {
                    Toaster.showCenter(this@MainActivity, "签名成功并保存！")
                } else {
                    Toaster.showCenter(this@MainActivity, "签名失败！")
                }
            }
            is BlockchainPretransinfoContentInfo -> {
                if (unlock.text == "unlock") {
                    if (isdeposit) {
                        //充值
                        blockchainRawtransaction(
                                depositToData(
                                        textView.text.toString(),
                                        "0xc778417e063141139fce010982780140aa0cd5ab",
                                        BigInteger(event.nonce?.replace("0x", ""), 16).toInt(),
                                        event.gasPrice,
                                        event.gasLimit,
                                        "0.5"), SPUtils.get(this, WALLET_PASSWORD, "Passw0rd!").toString())
                    } else {
                        blockchainRawtransaction(
                                withdrawToData(
                                        textView.text.toString(),
                                        "0xc778417e063141139fce010982780140aa0cd5ab",
                                        BigInteger(event.nonce?.replace("0x", ""), 16).toInt(),
                                        event.gasPrice,
                                        event.gasLimit), SPUtils.get(this, WALLET_PASSWORD, "Passw0rd!").toString())
                    }

                } else {
                    //解锁
                    runOnUiThread {
                        showProgressBarDialog("正在解锁...")
                    }
                    blockchainRawtransaction(
                            doUnLock(
                                    textView.text.toString(),
                                    event.contractAddress,
                                    BigInteger(event.nonce?.replace("0x", ""), 16).toInt(),
                                    event.gasPrice,
                                    event.gasLimit, SPUtils.get(this, WALLET_PASSWORD, "Passw0rd!").toString()))
                }


            }
            is BlockchainRawtransactionContentInfo -> {
                blockchainTransreceipt(event.txHash)
            }
            is BlockchainTransreceiptContentInfo -> {
                if (!event.blockNumber.isNullOrEmpty()) {
                    if (unlock.text == "unlock") {
                        if (isdeposit) {
                            Toaster.showCenter(this@MainActivity, "充值成功！")
                        } else {
                            Toaster.showCenter(this@MainActivity, "提现成功！")
                        }
                    } else {
                        //上链
                        Toaster.showCenter(this@MainActivity, "解锁成功！")
                    }
                    runOnUiThread {
                        hideProgressBarDialog()
                    }
                } else {
                    //未上链
                    launch(CommonPool) {
                        delay(15000L)
                        Logger.d("重新获取blockNumber = $retryblockNumber")
                        blockchainTransreceipt(event.txHash)
                        retryblockNumber++
                    }
                }
            }
            is OrderCreateContentInfo -> {
                when {
                    event.success -> Toaster.showCenter(this@MainActivity, "下单成功（买单）")
                }
            }
            is ErrorContentInfo -> {
                runOnUiThread {
                    hideProgressBarDialog()
                }
                Toaster.showCenter(this@MainActivity, getString(R.string.network_error))
            }
            is QueryQuotationContentInfo -> {
                var recentPrice = event.lastPrice!!.toFloat()
                Log.i("", event.lastPrice)
                if (TextUtils.isEmpty(event.lastPrice) || event.lastPrice!!.toFloat() == 0f) {
                } else {

                    if (lastPrice == 0f) {
                        underTheCheck(BigDecimal((recentPrice * 0.8f).toString()).toFloat())
                        unDerTheSale(BigDecimal((recentPrice * 1.2f).toString()).toFloat())
                        lastPrice = recentPrice
                    } else {
                        if (recentPrice > lastPrice) { //价格上涨
                            queryOrderMy()
                            isBuy = true
                            lastPrice = recentPrice
                        } else if (recentPrice < lastPrice) {//价格下跌
                            queryOrderMy()
                            isBuy = false
                            lastPrice = recentPrice
                        }
                    }
                }

            }

            is OrderContentInfo -> {//卖单数据/买单数据
                orderlist = event.list
                for (orderContentInfo in orderlist!!) {
                    if (isBuy) {
                        if ("BUY".equals(orderContentInfo.orderType)) {
                            orderCancel(orderContentInfo?.orderHash)
                        }
                    } else {
                        if ("SELL".equals(orderContentInfo.orderType)) {
                            orderCancel(orderContentInfo?.orderHash)
                        }
                    }
                }
            }
            is OrderCancelContentInfo -> {
                if (isBuy) {
                    underTheCheck(lastPrice * 0.8f)
                } else {
                    unDerTheSale(lastPrice * 1.2f)
                }
            }
        }
    }


    private val countDownTimer = object : CountDownTimer(Integer.MAX_VALUE.toLong(), (10 * 1000).toLong()) {
        override fun onTick(millisUntilFinished: Long) {
            queryTheQuotation()
        }

        override fun onFinish() {
        }
    }

    fun isImportAccount(trueToDo: () -> Unit, falseToDo: () -> Unit): Unit {
        when (IS_IMPORT_ACCOUNT) {
            true -> {
                trueToDo()
            }
            false -> {
                falseToDo()
            }
        }
    }

    /**
     * 下买单
     */
    fun underTheCheck(price: Float) {
        //买单
        val rand = Random()
        var number = rand.nextInt(1000) + 10
        var fee = 0.0009

        var order = Order()
        order.maker = textView.text.toString()
        order.taker = "0x0000000000000000000000000000000000000000"
        order.feeRecipient = "0x0000000000000000000000000000000000000000"
        order.makerTokenAddress = "0xc778417e063141139fce010982780140aa0cd5ab"
        order.takerTokenAddress = "0x4bc8e905df73d617eb37d7880fc4751d7cb1c645"
        order.exchangeContractAddress = "0x479cc461fecd078f766ecc58533d6f69580cf3ac"
        order.salt = generatePseudoRandomSalt()
        order.makerFee = "0"
        order.takerFee = "0"
        order.makerTokenAmount = BigDecimal((price * number + fee).toString()).multiply(BigDecimal(Math.pow(10.toDouble(), 18.0))).toBigInteger().toString()
        order.takerTokenAmount = BigDecimal(number.toString()).multiply(BigDecimal(Math.pow(10.toDouble(), 18.0))).toBigInteger().toString()
        order.expirationUnixTimestampSec = "4099680000000"
        val orderString = JSON.toJSONString(order)
        Logger.json(orderString)
        //获得签名
        val signature = placeTheOrder(this@MainActivity, textView.text.toString(), orderString)
        var orderAndSignature = OrderAndSignature()
        orderAndSignature.maker = order.maker
        orderAndSignature.taker = order.taker
        orderAndSignature.feeRecipient = order.feeRecipient
        orderAndSignature.makerTokenAddress = order.makerTokenAddress
        orderAndSignature.takerTokenAddress = order.takerTokenAddress
        orderAndSignature.exchangeContractAddress = order.exchangeContractAddress
        orderAndSignature.salt = order.salt
        orderAndSignature.makerFee = order.makerFee
        orderAndSignature.takerFee = order.takerFee
        orderAndSignature.makerTokenAmount = order.makerTokenAmount
        orderAndSignature.takerTokenAmount = order.takerTokenAmount
        orderAndSignature.expirationUnixTimestampSec = order.expirationUnixTimestampSec
        var ecSignatureBean = OrderAndSignature.EcSignatureBean()
        ecSignatureBean.r = Numeric.toHexString(signature.r).toString()
        ecSignatureBean.s = Numeric.toHexString(signature.s).toString()
        ecSignatureBean.v = Integer.toString(signature.v.toInt()).toString()
        orderAndSignature.ecSignature = ecSignatureBean
        //组装post
        var orderCreateRequest = OrderCreateRequest()
        orderCreateRequest.baseToken = "CETF"
        orderCreateRequest.counterToken = "WETH"
        orderCreateRequest.orderType = "BUY"
        orderCreateRequest.amount = number.toString()
        orderCreateRequest.price = BigDecimal(price.toString()).toString()
        orderCreateRequest.fee = fee.toString()
        orderCreateRequest.signedJson = JSON.parseObject(JSON.toJSONString(orderAndSignature))
        orderCreateRequest.orderHash = Hash.sha3(Numeric.toHexStringNoPrefix(orderString.toByteArray()))
        orderAuthCreate(orderCreateRequest)

    }

    fun unDerTheSale(price: Float) {
        val rand = Random()
        var number = rand.nextInt(1000) + 10

        //卖单

        var fee = 0.0009

        var order = Order()
        order.maker = textView.text.toString()
        order.taker = "0x0000000000000000000000000000000000000000"
        order.feeRecipient = "0x0000000000000000000000000000000000000000"
        order.makerTokenAddress = "0x4bc8e905df73d617eb37d7880fc4751d7cb1c645"
        order.takerTokenAddress = "0xc778417e063141139fce010982780140aa0cd5ab"
        order.exchangeContractAddress = "0x479cc461fecd078f766ecc58533d6f69580cf3ac"
        order.salt = generatePseudoRandomSalt()
        order.makerFee = "0"
        order.takerFee = "0"
        order.makerTokenAmount = BigDecimal(number.toString()).multiply(BigDecimal(Math.pow(10.toDouble(), 18.0))).toBigInteger().toString()
        order.takerTokenAmount = BigDecimal((price * number - fee).toString()).multiply(BigDecimal(Math.pow(10.toDouble(), 18.0))).toBigInteger().toString()
        order.expirationUnixTimestampSec = "4099680000000"
        val orderString = JSON.toJSONString(order)
        Logger.json(orderString)
        //获得签名
        val signature = placeTheOrder(this@MainActivity, textView.text.toString(), orderString)
        var orderAndSignature = OrderAndSignature()
        orderAndSignature.maker = order.maker
        orderAndSignature.taker = order.taker
        orderAndSignature.feeRecipient = order.feeRecipient
        orderAndSignature.makerTokenAddress = order.makerTokenAddress
        orderAndSignature.takerTokenAddress = order.takerTokenAddress
        orderAndSignature.exchangeContractAddress = order.exchangeContractAddress
        orderAndSignature.salt = order.salt
        orderAndSignature.makerFee = order.makerFee
        orderAndSignature.takerFee = order.takerFee
        orderAndSignature.makerTokenAmount = order.makerTokenAmount
        orderAndSignature.takerTokenAmount = order.takerTokenAmount
        orderAndSignature.expirationUnixTimestampSec = order.expirationUnixTimestampSec
        var ecSignatureBean = OrderAndSignature.EcSignatureBean()
        ecSignatureBean.r = Numeric.toHexString(signature.r).toString()
        ecSignatureBean.s = Numeric.toHexString(signature.s).toString()
        ecSignatureBean.v = Integer.toString(signature.v.toInt()).toString()
        orderAndSignature.ecSignature = ecSignatureBean
        //组装post
        var orderCreateRequest = OrderCreateRequest()
        orderCreateRequest.baseToken = "CETF"
        orderCreateRequest.counterToken = "WETH"
        orderCreateRequest.orderType = "SELL"
        orderCreateRequest.amount = number.toString()
        orderCreateRequest.price = BigDecimal(price.toString()).toString()
        orderCreateRequest.fee = fee.toString()
        orderCreateRequest.signedJson = JSON.parseObject(JSON.toJSONString(orderAndSignature))
        orderCreateRequest.orderHash = Hash.sha3(Numeric.toHexStringNoPrefix(orderString.toByteArray()))
        orderAuthCreate(orderCreateRequest)
    }

}