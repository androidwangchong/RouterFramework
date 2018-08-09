package com.base.app.basemodule.baseactivity

import com.base.app.basemodule.event.ChangeAppLanguageEvent
import com.base.app.basemodule.dialog.ProgressBarDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.base.app.basemodule.utils.changeAppLanguage
import de.greenrobot.event.EventBus

/**
 * BaseModel
 * Created by wangchong on 2017/7/13.
 */
abstract class BaseFragment : IBase, Fragment(), View.OnTouchListener {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(layoutResId, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        activity.changeAppLanguage()
    }


    open fun onEvent(event: Any) {
        when(event){
            is ChangeAppLanguageEvent -> {
                activity.changeAppLanguage()
                activity.recreate()
            }
        }
    }

    abstract override fun initView()

    abstract override fun initData()

    abstract val layoutResId: Int


    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
    var progress_bar_dialog: ProgressBarDialog? = null

    fun showProgressBarDialog(text: String) {
        progress_bar_dialog = ProgressBarDialog(text)
        progress_bar_dialog?.show(activity.fragmentManager, "progress_bar_dialog")
    }

    fun hideProgressBarDialog() {
        progress_bar_dialog?.dismiss()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return true
    }
}