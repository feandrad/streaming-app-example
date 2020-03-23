package com.outcomehealth.app.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    protected abstract val viewModel: VM
    protected abstract val layout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(layout, savedInstanceState)
    }

    protected open fun init(@LayoutRes layout: Int, savedInstanceState: Bundle?) {
        setContentView(layout)
        initViews()
        observeLiveData()
        initUiEvents()

        viewModel.activityCreated(savedInstanceState ?: intent.extras)
    }

    override fun onResume() {
        super.onResume()
        viewModel.activityResumed(this)
    }

    override fun onPause() {
        super.onPause()
        viewModel.activityPaused(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(viewModel.makeOutOfStateBundle(outState))
    }


    protected open fun initViews() {
    }

    protected open fun observeLiveData() {
    }

    protected open fun initUiEvents() {
    }

}