package com.base.app.httpmodule.http

import com.alibaba.fastjson.JSON
import com.base.app.basemodule.widget.Toaster
import com.base.app.basemodule.app.MyApplication
import com.base.app.httpmodule.event.ErrorContentInfo
import com.base.app.httpmodule.http.api.errorapi.ErrorApi
import de.greenrobot.event.EventBus
import retrofit2.Response

/**
 * hzx-andriod
 * Created by wangchong on 2017/8/11.
 */
object HttpCommonUtil {
    fun <T> putMessageToActivity(p1: Response<T>?) {
        if (p1?.code() == 200) {
            val body = p1.body()
            EventBus.getDefault().post(body ?: "")
//            when (body) {
//                is BaseContentInfo -> {
//                    when {
//                        body.success -> {
//                            EventBus.getDefault().post(body ?: "")
//                        }
//                        else -> {
//                            Toaster.showCenter(MyApplication.instance(), ErrorApi.getErrorMessage(body.errorCode))
//                        }
//                    }
//                }
//            }
        } else if (p1?.code() == 401) {
            val contentinfo = JSON.parseObject(p1?.errorBody()?.string())
            Toaster.showCenter(MyApplication.instance(), ErrorApi.getErrorMessage(contentinfo.getIntValue("errorCode")))
            EventBus.getDefault().post(ErrorContentInfo())
        } else if (p1?.code() == 400) {
            //错误处理
            val contentinfo = JSON.parseObject(p1?.errorBody()?.string())
//            Toaster.showCenter(MyApplication.instance(), ErrorApi.getErrorMessage(contentinfo.getIntValue("errorCode")))
            EventBus.getDefault().post(ErrorContentInfo())
        } else {
//            Toaster.showCenter(MyApplication.instance(), ErrorApi.getErrorMessage(10000000))
            EventBus.getDefault().post(ErrorContentInfo())
        }

    }

}