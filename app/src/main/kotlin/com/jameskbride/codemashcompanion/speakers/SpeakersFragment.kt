package com.jameskbride.codemashcompanion.speakers

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import javax.inject.Inject

class SpeakersFragment: androidx.fragment.app.Fragment() {

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

    fun makeGridLayoutManager(columnCount: Int): androidx.recyclerview.widget.RecyclerView.LayoutManager? {
        return androidx.recyclerview.widget.GridLayoutManager(context, columnCount)
    }
}