package com.base.app.basemodule.baseactivity

import `in`.srain.cube.views.ptr.PtrClassicFrameLayout
import `in`.srain.cube.views.ptr.PtrDefaultHandler2
import `in`.srain.cube.views.ptr.PtrFrameLayout
import `in`.srain.cube.views.ptr.header.StoreHouseHeader
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.jaeger.library.StatusBarUtil
import com.base.app.basemodule.event.ChangeAppLanguageEvent
import com.base.app.basemodule.R
import com.base.app.basemodule.dialog.ProgressBarDialog
import com.base.app.basemodule.utils.changeAppLanguage
import com.base.app.basemodule.widget.CustomToolbar
import de.greenrobot.event.EventBus
import kotlinx.android.synthetic.main.common_list.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * BaseModel
 * Created by wangchong on 2017/7/13.
 */
abstract class BaseListActivity : IBase, AppCompatActivity(), AnkoLogger {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        initToolBar()
        initListViewFrame()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        StatusBarUtil.setColorNoTranslucent(this@BaseListActivity, ContextCompat.getColor(this@BaseListActivity, R.color.colorPrimary))
        getIntentMessageData()
        initView()
        initData()
        initErrorLayout()
        changeAppLanguage()
    }

    fun initToolBar() {
        val toolbar = findViewById(R.id.toolbar) as? CustomToolbar
        toolbar?.getmTvMainTitleLeft()?.setOnClickListener {
            finish()
        }
        toolbar?.setNavigationOnClickListener {
            finish()
        }
        val toolbar_title = findViewById(R.id.toolbar_title) as? TextView
        toolbar_title?.text = title
    }

    var mLotateHeaderListViewFrame: PtrClassicFrameLayout? = null
    var mRecyclerView: RecyclerView? = null

    fun initListViewFrame() {
        mRecyclerView = findViewById(R.id.recyler_view)
                as RecyclerView
        mRecyclerView?.apply {
            layoutManager = LinearLayoutManager(this@BaseListActivity, LinearLayoutManager.VERTICAL, false)
        }
        mLotateHeaderListViewFrame = findViewById(R.id.rotate_header_list_view_frame)
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
                    hideErrorLayout()
                }, 1500)
            }
        })
        // header  设置下拉刷新header  如只需常规刷新，删除一下代码
        val header = StoreHouseHeader(this)
        header.setPadding(0, dip(15.0f), 0, dip(15.0f))
        header.initWithString("Loading...")
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

    abstract val title: String
    abstract val layoutResId: Int
    abstract fun getIntentMessageData()
    abstract override fun initView()
    abstract override fun initData()
    abstract fun onRefresh()
    abstract fun onLoadMore()

    open fun onEvent(event: Any) {
        when(event){
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
        super.onDestroy()
    }

    fun showErrorLayout() {
        error_layout.visibility = View.VISIBLE
    }

    fun hideErrorLayout() {
        error_layout.visibility = View.GONE
    }

    //    getString(R.string.Do_not_open_the_lottery)
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

    fun showProgressBarDialog(text: String = getString(R.string.please_wait)) {
        progress_bar_dialog = ProgressBarDialog(text)
        progress_bar_dialog?.show(fragmentManager, "progress_bar_dialog")
    }

    fun hideProgressBarDialog() {
        progress_bar_dialog?.dismiss()
    }

}
