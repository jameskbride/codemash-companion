package com.jameskbride.codemashcompanion.sessions.list.listitems

import android.view.View
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.sessions.list.ItemViewHolder
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SessionListItemTest {
    private lateinit var subject: SessionListItem
    private lateinit var session: FullSession
    private lateinit var clickListener: View.OnClickListener

    @Mock private lateinit var itemViewHolder: ItemViewHolder
    @Mock private lateinit var view: View

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        session = FullSession()
        clickListener = View.OnClickListener {  }
        subject = SessionListItem(session, clickListener)
    }

    @Test
    fun itHasALayout() {
        assertEquals(R.layout.sessions_item, subject.layout)
    }

    @Test
    fun itCanBindTheViewHolder() {
        whenever(itemViewHolder.view).thenReturn(view)

        subject.bind(itemViewHolder, 0)

        verify(itemViewHolder).bind(session)
        verify(view).setOnClickListener(clickListener)
    }
}