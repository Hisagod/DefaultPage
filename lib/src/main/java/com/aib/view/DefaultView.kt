package com.aib.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IdRes
import com.aib.other.DefaultPage
import com.lib.df.page.R

class DefaultView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    private val TAG = javaClass.simpleName

    private val loadLayout: Int
    private val errorLayout: Int
    private val emptyLayout: Int

    private lateinit var loadView: View
    private lateinit var successView: View
    private lateinit var errorView: View
    private lateinit var emptyView: View

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DefaultView)

        //根据自定义属性加载布局，如果布局为空，则加载库中默认的布局
        loadLayout = typedArray.getResourceId(R.styleable.DefaultView_load_layout, DefaultPage.loadRes)
        errorLayout = typedArray.getResourceId(R.styleable.DefaultView_error_layout, DefaultPage.errorRes)
        emptyLayout = typedArray.getResourceId(R.styleable.DefaultView_empty_layout, DefaultPage.emptyRes)

        typedArray.recycle()

        //将子View都添加到本View
        val loadView = LayoutInflater.from(context).inflate(loadLayout, this, false)
        addView(loadView)
        val errorView = LayoutInflater.from(context).inflate(errorLayout, this, false)
        addView(errorView)
        val emptyView = LayoutInflater.from(context).inflate(emptyLayout, this, false)
        addView(emptyView)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        //当本View加载完成，取出对应子View，并全部隐藏子View
        loadView = getChildAt(0)
        errorView = getChildAt(1)
        emptyView = getChildAt(2)
        successView = getChildAt(childCount - 1)

        loadView.setVisibility(View.GONE)
        errorView.setVisibility(View.GONE)
        emptyView.setVisibility(View.GONE)
        successView.setVisibility(View.INVISIBLE)
    }

    fun showLoad() {
        if (successView.visibility == View.VISIBLE)
            return

        loadView.visibility = View.VISIBLE
        successView.visibility = View.GONE
        errorView.visibility = View.GONE
        emptyView.visibility = View.GONE
    }

    /**
     * 加载页：有文案提示，文案可动态变化(每个页面有可能不一样的文案)
     */
    fun showLoad(callback: (loadView: View) -> Unit) {
        showLoad()
        callback(loadView)
    }

    private fun showError() {
        loadView.visibility = View.GONE
        successView.visibility = View.GONE
        errorView.visibility = View.VISIBLE
        emptyView.visibility = View.GONE
    }

    fun showError(callback: (errorView: View) -> Unit) {
        showError()
        callback(errorView)
    }

    fun showEmpty() {
        if (successView.visibility == View.VISIBLE) {
            return
        }

        loadView.visibility = View.GONE
        successView.visibility = View.GONE
        errorView.visibility = View.GONE
        emptyView.visibility = View.VISIBLE
    }

    fun showEmpty(callback: (emptyView: View) -> Unit) {
        showEmpty()
        callback(emptyView)
    }

    fun showSuccess() {
        loadView.visibility = View.GONE
        successView.visibility = View.VISIBLE
        errorView.visibility = View.GONE
        emptyView.visibility = View.GONE
    }
}