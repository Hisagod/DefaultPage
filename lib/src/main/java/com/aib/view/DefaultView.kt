package com.aib.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.aib.lib.R

class DefaultView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

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
        loadLayout = typedArray.getResourceId(R.styleable.DefaultView_load_layout, R.layout.default_view_load)
        errorLayout = typedArray.getResourceId(R.styleable.DefaultView_error_layout, R.layout.default_view_error)
        emptyLayout = typedArray.getResourceId(R.styleable.DefaultView_empty_layout, R.layout.default_view_empty)

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
        successView.setVisibility(View.GONE)
    }

    /**
     * 显示加载UI
     */
    fun showLoad() {
        loadView.visibility = View.VISIBLE
        successView.visibility = View.GONE
        errorView.visibility = View.GONE
        emptyView.visibility = View.GONE
    }

    fun showError() {
        loadView.visibility = View.GONE
        successView.visibility = View.GONE
        errorView.visibility = View.VISIBLE
        emptyView.visibility = View.GONE
    }

    /**
     * 封装错误布局里，重试按钮回调
     */
    fun showError(callback: () -> Unit) {
        showError()
        try {
            errorView.findViewById<View>(R.id.retry)
                    .setOnClickListener {
                        callback()
                    }
        } catch (e: Exception) {
            throw NullPointerException("重试控件ID必为<retry>")
        }
    }

    fun showEmpty() {
        loadView.visibility = View.GONE
        successView.visibility = View.GONE
        errorView.visibility = View.GONE
        emptyView.visibility = View.VISIBLE
    }

    fun showSuccess() {
        loadView.visibility = View.GONE
        successView.visibility = View.VISIBLE
        errorView.visibility = View.GONE
        emptyView.visibility = View.GONE
    }
}