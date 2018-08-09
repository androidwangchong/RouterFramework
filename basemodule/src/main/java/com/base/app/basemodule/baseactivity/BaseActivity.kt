package com.base.app.basemodule.baseactivity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.jaeger.library.StatusBarUtil
import com.base.app.basemodule.dialog.ProgressBarDialog
import de.greenrobot.event.EventBus
import org.jetbrains.anko.AnkoLogger
import android.content.Intent
import android.content.IntentFilter
import android.content.BroadcastReceiver
import android.content.Context
import com.base.app.basemodule.R
import com.base.app.basemodule.event.ChangeAppLanguageEvent
import com.base.app.basemodule.utils.changeAppLanguage
import com.base.app.basemodule.widget.CustomToolbar


/**
 * BaseModel
 * Created by wangchong on 2017/7/13.
 */
abstract class BaseActivity : IBase, AppCompatActivity(), AnkoLogger {
    companion object {
        val ACTION_CLOSE = "close_all_activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        val toolbar = findViewById(R.id.toolbar) as? CustomToolbar
        toolbar?.getmTvMainTitleLeft()?.setOnClickListener {
            onBack()
        }

        toolbar?.setNavigationOnClickListener {
            onBack()
        }
        val toolbar_title = findViewById(R.id.toolbar_title) as? TextView
        toolbar_title?.text = title

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        StatusBarUtil.setColorNoTranslucent(this@BaseActivity, ContextCompat.getColor(this@BaseActivity, R.color.colorPrimary))
        getIntentMessageData()
        initView()
        initData()
        changeAppLanguage()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ACTION_CLOSE)
        registerReceiver(receiver, intentFilter)
    }

    open fun onBack() {
        finish()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    abstract val title: String
    abstract val layoutResId: Int
    abstract fun getIntentMessageData()
    abstract override fun initView()
    abstract override fun initData()

    open fun onEvent(event: Any) {
        when (event) {
            is ChangeAppLanguageEvent -> {
                changeAppLanguage()
                recreate()
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }


    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        unregisterReceiver(receiver);
        super.onDestroy()
    }

//    override fun getResources(): Resources {
//        val resources = super.getResources()
//        val configuration = Configuration()
//        configuration.setToDefaults()
//        resources.updateConfiguration(configuration,
//                resources.displayMetrics)
//        return resources
//    }

    var progress_bar_dialog: ProgressBarDialog? = null

    fun showProgressBarDialog(text: String) {
        progress_bar_dialog = ProgressBarDialog(text)
        progress_bar_dialog?.show(fragmentManager, "progress_bar_dialog")
    }

    var receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            finish()
        }
    }

    fun hideProgressBarDialog() {
        progress_bar_dialog?.dismiss()
    }

}
