package com.base.app.httpmodule.http


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.base.app.httpmodule.http.apiconfig.HttpConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * BaseModel
 * Created by wangchong on 2017/7/14.
 */
object RetrofitClient {

    var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
                //设置OKHttpClient
                .client(OKHttpFactory.okHttpClient)

                //baseUrl
                .baseUrl(HttpConfig.SERVER_URL)

                //string转化器
                .addConverterFactory(StringConverter.create())

                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())

                //Rx
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

                //创建
                .build()
    }
}
