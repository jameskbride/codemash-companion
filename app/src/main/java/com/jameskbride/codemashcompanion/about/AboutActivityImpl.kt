package com.jameskbride.codemashcompanion.about

import android.os.Bundle
import android.view.MenuItem
import com.jameskbride.codemashcompanion.R

class AboutActivityImpl {
    fun onCreate(savedInstanceState: Bundle?, qtn: AboutActivity) {
        qtn.setContentView(R.layout.activity_about)

        configureActionBar(qtn)
    }

    private fun configureActionBar(qtn: AboutActivity) {
        qtn.setSupportActionBar(qtn.findViewById(R.id.toolbar))
        qtn.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun onOptionsItemSelected(item: MenuItem?, qtn: AboutActivity): Boolean {
        when(item?.itemId) {
            android.R.id.home ->  {
                qtn.onBackPressed()
                return true
            }
            else -> return qtn.callSuperOnOptionsItemSelected(item!!)
        }
    }
}