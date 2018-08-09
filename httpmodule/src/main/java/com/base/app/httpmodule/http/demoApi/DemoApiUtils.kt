package com.base.app.httpmodule.http.demoApi

import com.base.app.httpmodule.http.ApiFactory
import com.base.app.httpmodule.http.EnqueueCallback
import com.base.app.httpmodule.http.apiconfig.model

/**
 * BaseModel
 * Created by wangchong on 2017/10/30.
 */
fun userInfo() {
    ApiFactory.getDemoAPI()
            ?.userInfo()
            ?.enqueue(EnqueueCallback<model>())
}