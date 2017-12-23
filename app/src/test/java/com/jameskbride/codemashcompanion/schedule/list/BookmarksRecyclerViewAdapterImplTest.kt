package com.jameskbride.codemashcompanion.schedule.list

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.sessions.SessionsFragmentPresenter
import com.jameskbride.codemashcompanion.sessions.list.EmptyViewHolder
import com.jameskbride.codemashcompanion.sessions.list.ListItem
import com.jameskbride.codemashcompanion.sessions.list.SessionData
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class BookmarksRecyclerViewAdapterImplTest {

    @Mock private lateinit var layoutInflaterFactory:LayoutInflaterFactory
    @Mock private lateinit var qtn: BookmarksRecyclerViewAdapter
    @Mock private lateinit var context:Context
    @Mock private lateinit var container:ViewGroup
    @Mock private lateinit var view:View
    @Mock private lateinit var presenter:SessionsFragmentPresenter

    private lateinit var subject:BookmarksRecyclerViewAdapterImpl

    @Before
    fun setUp() {
        initMocks(this)

        subject = BookmarksRecyclerViewAdapterImpl(presenter, layoutInflaterFactory)

        whenever(container.context).thenReturn(context)
    }

    @Test
    fun itCreatesAnEmptyViewHolderWhenThereAreNoSessions() {
        subject.setSessions(SessionData(arrayOf()), qtn)

        whenever(layoutInflaterFactory.inflate(context, R.layout.empty_schedule, container)).thenReturn(view)

        val sessionViewHolder = subject.onCreateViewHolder(container, ListItem.EMPTY_TYPE)

        verify(layoutInflaterFactory).inflate(context, R.layout.empty_schedule, container)
        assertTrue(sessionViewHolder is EmptyViewHolder)
    }
}