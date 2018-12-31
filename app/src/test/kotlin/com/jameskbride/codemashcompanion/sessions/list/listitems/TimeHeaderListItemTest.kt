package com.jameskbride.codemashcompanion.sessions.list.listitems

import android.view.View
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.sessions.list.TimeViewHolder
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.xwray.groupie.ExpandableGroup
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import java.util.*

class TimeHeaderListItemTest {
    private lateinit var subject: TimeHeaderListItem
    private lateinit var aTime: Date

    @Mock private lateinit var timeViewHolder: TimeViewHolder
    @Mock private lateinit var expandableGroup: ExpandableGroup
    @Mock private lateinit var itemView: View

    @Before
    fun setUp() {
        initMocks(this)

        whenever(timeViewHolder.root).thenReturn(itemView)
        aTime = Date()
        subject = TimeHeaderListItem(aTime)
    }

    @Test
    fun itHasALayout() {
        assertEquals(R.layout.sessions_time_header, subject.layout)
    }

    @Test
    fun itCanBindTheViewHolder() {
        subject.bind(timeViewHolder, 0)

        verify(timeViewHolder).bind(aTime)
    }

    @Test
    fun givenTheExpandedGroupIsSetThenItCanToggleExpandedWhenClicked() {
        subject.setExpandableGroup(expandableGroup)
        subject.bind(timeViewHolder, 0)

        val onClickCaptor = argumentCaptor<View.OnClickListener>()
        verify(itemView).setOnClickListener(onClickCaptor.capture())

        onClickCaptor.firstValue.onClick(null)

        verify(expandableGroup).onToggleExpanded()
    }
}