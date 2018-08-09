package com.base.app.basemodule.utils

import android.app.Activity
import android.text.TextUtils
import java.util.*

/**
 * RouterFramework
 * Created by wangchong on 2018/7/25.
 */


val CHANG_LANGUAGE_KEY = "lang"
val CHANG_LANGUAGE_AUTO = "auto"
val CHANG_LANGUAGE_ZH = "zh"
val CHANG_LANGUAGE_EN = "en"

fun Activity.changeAppLanguage() {
    val sta = SPUtils.get(this, CHANG_LANGUAGE_KEY, CHANG_LANGUAGE_AUTO) as String
    // 本地语言设置
    val myLocale = Locale(sta)
    val res = resources
    val dm = res.displayMetrics
    val conf = res.configuration
    if (TextUtils.equals(CHANG_LANGUAGE_AUTO, sta)) {
        conf.locale = Locale.getDefault()
    } else {
        conf.locale = myLocale
    }
    res.updateConfiguration(conf, dm)
}