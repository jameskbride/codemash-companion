package com.jameskbride.codemashcompanion.framework

import android.os.Bundle
import android.view.MenuItem

open abstract class BaseActivityImpl {
    abstract fun onCreate(savedInstanceState: Bundle?, qtn: BaseActivity)
    abstract fun onOptionsItemSelected(item: MenuItem?, qtn: BaseActivity): Boolean
    abstract fun onResume(sessionDetailActivity: BaseActivity)
    abstract fun onPause(sessionDetailActivity: BaseActivity)

    companion object {
        val PARAMETER_BLOCK: String = "PARAMETER_BLOCK"
    }
}
