package com.jameskbride.codemashcompanion.sessions.list.listitems

import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.sessions.list.DateViewHolder
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DateHeaderListItemTest {
    private lateinit var subject: DateHeaderListItem

    @Mock
    private lateinit var dateViewHolder: DateViewHolder

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        subject = DateHeaderListItem("some date")
    }

    @Test
    fun itHasALayout() {
        assertEquals(R.layout.sessions_date_header, subject.layout)
    }

    @Test
    fun itCanBindTheViewHolder() {
        subject.bind(dateViewHolder, 0)

        verify(dateViewHolder).bind("some date")
    }
}