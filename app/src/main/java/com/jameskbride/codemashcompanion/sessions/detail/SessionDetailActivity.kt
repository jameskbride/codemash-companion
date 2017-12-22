package com.jameskbride.codemashcompanion.sessions.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import javax.inject.Inject

class SessionDetailActivity: AppCompatActivity() {

    @Inject
    lateinit var impl:SessionDetailActivityImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodemashCompanionApplication.applicationComponent.inject(this)
        impl.onCreate(savedInstanceState, this)
    }

    override fun onResume() {
        super.onResume()
        impl.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        impl.onPause(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return return impl.onOptionsItemSelected(item, this)
    }

    fun callSuperOnOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }
}