package com.base.app.httpmodule.http


import com.base.app.basemodule.app.MyApplication
import com.base.app.basemodule.widget.Toaster
import com.base.app.httpmodule.event.ErrorContentInfo
import com.base.app.httpmodule.http.api.errorapi.ErrorApi
import com.orhanobut.logger.Logger
import de.greenrobot.event.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * BaseModel
 * Created by wangchong on 2017/10/30.
 */
fun <T> EnqueueCallback(): Callback<T> = object : Callback<T> {
    override fun onResponse(p0: Call<T>?, p1: Response<T>?) {
        p1.let {
            HttpCommonUtil.putMessageToActivity(p1)
        }
    }

    override fun onFailure(p0: Call<T>?, p1: Throwable?) {
        Toaster.showCenter(MyApplication.instance(), ErrorApi.getErrorMessage(10000000))
        EventBus.getDefault().post(ErrorContentInfo())
        Logger.e(p0?.request()?.url()?.toString() + "\n" + p1?.message + "\n" + p0?.request()?.body())
    }
}
