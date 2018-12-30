package com.jameskbride.codemashcompanion.sessions.list.listitems

import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.sessions.list.TimeViewHolder
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

class TimeHeaderListItemTest {
    private lateinit var subject: TimeHeaderListItem
    private lateinit var aTime: Date

    @Mock
    private lateinit var timeViewHolder: TimeViewHolder

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
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
}