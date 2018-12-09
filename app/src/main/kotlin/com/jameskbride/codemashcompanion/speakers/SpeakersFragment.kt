package com.jameskbride.codemashcompanion.speakers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import javax.inject.Inject

class SpeakersFragment: Fragment() {

    @Inject
    lateinit var impl: SpeakersFragmentImpl

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        CodemashCompanionApplication.applicationComponent.inject(this)
        return impl.onCreateView(inflater, container, savedInstanceState, this)
    }

    override fun onResume() {
        super.onResume()
        impl.onResume()
    }

    override fun onPause() {
        super.onPause()
        impl.onPause()
    }

    fun makeGridLayoutManager(columnCount: Int): RecyclerView.LayoutManager? {
        return GridLayoutManager(context, columnCount)
    }
}