package com.jameskbride.codemashcompanion.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var impl: MainActivityImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CodemashCompanionApplication.applicationComponent.inject(this)
        impl.onCreate(savedInstanceState, this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return impl.onCreateOptionsMenu(menu, this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return impl.onOptionsItemSelected(item, this)
    }

    fun onSuperOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}
