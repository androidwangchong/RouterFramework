package com.base.app.basemodule.baseactivity

import `in`.srain.cube.views.ptr.PtrClassicFrameLayout
import `in`.srain.cube.views.ptr.PtrDefaultHandler2
import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.header.StoreHouseHeader
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.app.basemodule.event.ChangeAppLanguageEvent
import com.base.app.basemodule.R
import com.base.app.basemodule.dialog.ProgressBarDialog
import com.base.app.basemodule.utils.changeAppLanguage
import de.greenrobot.event.EventBus
import kotlinx.android.synthetic.main.common_list.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.dip

/**
 * BaseModel
 * Created by wangchong on 2017/7/13.
 */
abstract class BaseListFragment : IBase, Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater?.inflate(layoutResId, container, false)
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
        initListViewFrame(view)
        initData()
        initErrorLayout()
        activity.changeAppLanguage()
    }

    var mLotateHeaderListViewFrame: PtrClassicFrameLayout? = null

    fun initListViewFrame(view: View?) {
        mLotateHeaderListViewFrame = view?.findViewById(R.id.rotate_header_list_view_frame)
                as PtrClassicFrameLayout
        mLotateHeaderListViewFrame?.setPtrHandler(object : PtrDefaultHandler2() {
            override fun onLoadMoreBegin(frame: PtrFrameLayout) {
                mLotateHeaderListViewFrame?.postDelayed(Runnable {
                    onLoadMore()
                }, 1500)
            }

            override fun onRefreshBegin(frame: PtrFrameLayout) {
                mLotateHeaderListViewFrame?.postDelayed(Runnable {
                    onRefresh()
                    goneErrorLayout()
                }, 1500)
            }
        })
        // header  设置下拉刷新header  如只需常规刷新，删除一下代码
        val header = StoreHouseHeader(activity)
        header.setPadding(0, dip(15.0f), 0, dip(15.0f))
        header.initWithString("Loading")
        header.setTextColor(R.color.colorPrimary)
        mLotateHeaderListViewFrame?.setHeaderView(header)
        mLotateHeaderListViewFrame?.addPtrUIHandler(header)
    }

    //停止刷新
    fun refreshComplete() {
        mLotateHeaderListViewFrame?.refreshComplete()
    }

    //设置网络错误时，点击重新请求
    fun initErrorLayout() {
        error_layout.onClick {
            onRefresh()
        }
    }

    open fun onEvent(event: Any) {
        when (event) {
            is ChangeAppLanguageEvent -> {
                activity.changeAppLanguage()
                activity.recreate()
            }
        }
    }

    abstract override fun initView()
    abstract override fun initData()
    abstract fun onRefresh()
    abstract fun onLoadMore()

    abstract val layoutResId: Int


    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    fun showErrorLayout() {
        error_layout.visibility = View.VISIBLE
    }

    fun goneErrorLayout() {
        error_layout.visibility = View.GONE
    }

    fun resultListIsEmpty(size: Int, content: String = getString(R.string.no_data)) {
        if (size == 0) {
            showEmptylayout(content)
        } else {
            hideEmptylayout()
        }
    }


    fun showEmptylayout(content: String) {
        empty_layout.visibility = View.VISIBLE
        content_text.text = content
    }

    fun hideEmptylayout() {
        empty_layout.visibility = View.GONE
    }

    var progress_bar_dialog: ProgressBarDialog? = null

    fun showProgressBarDialog(text: String) {
        progress_bar_dialog = ProgressBarDialog(text)
        progress_bar_dialog?.show(activity.fragmentManager, "progress_bar_dialog")
    }

    fun hideProgressBarDialog() {
        progress_bar_dialog?.dismiss()
    }

}