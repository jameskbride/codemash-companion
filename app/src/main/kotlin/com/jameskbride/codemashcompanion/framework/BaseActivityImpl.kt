package com.jameskbride.codemashcompanion.framework

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.jameskbride.codemashcompanion.R

open abstract class BaseActivityImpl constructor(val defaultToolbar: DefaultToolbar = DefaultToolbar()) {
    abstract fun onCreate(savedInstanceState: Bundle?, qtn: BaseActivity)

    fun onOptionsItemSelected(item: MenuItem?, qtn: BaseActivity): Boolean {
        when(item?.itemId) {
            android.R.id.home ->  {
                qtn.onBackPressed()
                return true
            }
            else -> return qtn.callSuperOnOptionsItemSelected(item)
        }
    }

    abstract fun onResume(sessionDetailActivity: BaseActivity)

    abstract fun onPause(sessionDetailActivity: BaseActivity)

    fun configureActionBar(qtn: BaseActivity) {
        defaultToolbar.configureActionBar(qtn)
    }

    fun setTitle(qtn: BaseActivity, title: Int) {
        defaultToolbar.setTitle(qtn, title)
    }

    companion object {
        val PARAMETER_BLOCK: String = "PARAMETER_BLOCK"
    }
}

class DefaultToolbar {

    fun configureActionBar(qtn: BaseActivity) {
        qtn.setSupportActionBar(qtn.findViewById(R.id.toolbar))
        qtn.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun setTitle(qtn: BaseActivity, title: Int) {
        qtn.findViewById<Toolbar>(R.id.toolbar).setTitle(title)
    }
}
