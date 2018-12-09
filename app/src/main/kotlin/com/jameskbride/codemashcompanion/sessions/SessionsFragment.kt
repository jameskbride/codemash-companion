package com.jameskbride.codemashcompanion.sessions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import javax.inject.Inject
import javax.inject.Named

open class SessionsFragment: androidx.fragment.app.Fragment() {
    @Inject @field:Named("AllSessions")
    open lateinit var impl: SessionsFragmentImpl

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

    open fun makeLinearLayoutManager(): androidx.recyclerview.widget.RecyclerView.LayoutManager? {
        return androidx.recyclerview.widget.LinearLayoutManager(view?.context)
    }
}