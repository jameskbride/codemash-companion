package com.jameskbride.codemashcompanion

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity(val impl: MainActivityImpl = MainActivityImpl()) : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        impl.onCreate(savedInstanceState, this)
    }
}
