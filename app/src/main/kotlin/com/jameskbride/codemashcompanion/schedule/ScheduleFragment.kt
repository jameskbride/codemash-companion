package com.jameskbride.codemashcompanion.schedule

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    override fun makeLinearLayoutManager(): androidx.recyclerview.widget.RecyclerView.LayoutManager? {
        return androidx.recyclerview.widget.LinearLayoutManager(view?.context)
    }
}