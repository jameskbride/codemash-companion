package com.jameskbride.codemashcompanion.speakers.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import javax.inject.Inject

class SpeakerDetailActivity: AppCompatActivity() {

    @Inject
    lateinit var impl: SpeakerDetailActivityImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodemashCompanionApplication.applicationComponent.inject(this)
        impl.onCreate(savedInstanceState, this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return impl.onOptionsItemSelected(item, this)
    }

    fun callSuperOnOptionsItemSelected(menuItem: MenuItem):Boolean {
        return super.onOptionsItemSelected(menuItem)
    }
}