package com.jameskbride.codemashcompanion.schedule

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import com.jameskbride.codemashcompanion.sessions.SessionsFragment
import com.jameskbride.codemashcompanion.sessions.SessionsFragmentImpl
import javax.inject.Inject
import javax.inject.Named

class ScheduleFragment:SessionsFragment() {

    @Inject @field:Named("Bookmarked")
    override lateinit var impl: SessionsFragmentImpl

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

    override fun makeLinearLayoutManager(): RecyclerView.LayoutManager? {
        return LinearLayoutManager(view?.context)
    }
}