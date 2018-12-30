package com.jameskbride.codemashcompanion.sessions.list.listitems

import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.sessions.list.EmptyViewHolder
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class EmptyListItemTest {

    private lateinit var subject: EmptyListItem

    @Mock private lateinit var emptyViewHolder: EmptyViewHolder

    @Before
    fun setUp() {
        initMocks(this)
        subject = EmptyListItem()
    }

    @Test
    fun itHasALayout() {
        assertEquals(R.layout.no_data, subject.layout)
    }

    @Test
    fun itCanBindTheViewHolder() {
        subject.bind(emptyViewHolder, 0)

        verify(emptyViewHolder).bind()
    }
}