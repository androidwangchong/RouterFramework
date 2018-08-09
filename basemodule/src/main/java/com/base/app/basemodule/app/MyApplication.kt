package com.base.app.basemodule.app

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex

/**
 * RouterFramework
 * Created by wangchong on 2018/7/25.
 */

open class MyApplication : Application() {

    companion object {
        private var instance: MyApplication? = null
        fun instance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
