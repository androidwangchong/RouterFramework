package com.base.app.routerframework

import com.alibaba.android.arouter.launcher.ARouter
import com.base.app.basemodule.app.MyApplication
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

/**
 * RouterFramework
 * Created by wangchong on 2018/7/13.
 */
class MyApp : MyApplication() {
    override fun onCreate() {
        super.onCreate()
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
        initLoggerInfo()
    }
    fun initLoggerInfo() {
//        var formatStrategy = PrettyFormatStrategy.newBuilder()
//                .showThreadInfo(true)   //（可选）是否显示线程信息。默认值为true
//                .methodCount(2)          //（可选）要显示的方法行数。默认值2
//                .methodOffset(5)         //（可选）隐藏内部方法调用到偏移量。默认5
////        .logStrategy(customLog)//（可选）更改要打印的日志策略。默认LogCat
////                .tag("我的自定义标签")    //（可选）每个日志的全局标签。默认PRETTY_LOGGER
//                .build()
        Logger.addLogAdapter(AndroidLogAdapter())
    }
    private fun isDebug(): Boolean {
        return true
    }
}