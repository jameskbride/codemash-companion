package com.jameskbride.codemashcompanion.sessions.list.listitems

import android.view.View
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.sessions.list.DateViewHolder
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.xwray.groupie.ExpandableGroup
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks

class DateHeaderListItemTest {
    private lateinit var subject: DateHeaderListItem

    @Mock private lateinit var dateViewHolder: DateViewHolder
    @Mock private lateinit var itemView: View
    @Mock private lateinit var expandableGroup: ExpandableGroup

    @Before
    fun setUp() {
        initMocks(this)

        whenever(dateViewHolder.root).thenReturn(itemView)

        subject = DateHeaderListItem("2018-01-01")
    }

    @Test
    fun itHasALayout() {
        assertEquals(R.layout.sessions_date_header, subject.layout)
    }

    @Test
    fun itCanBindTheViewHolder() {
        subject.bind(dateViewHolder, 0)

        verify(dateViewHolder).bind("1/1/2018")
    }

    @Test
    fun givenTheExpandedGroupIsSetThenItCanToggleExpandedWhenClicked() {
        subject.setExpandableGroup(expandableGroup)
        subject.bind(dateViewHolder, 0)

        val onClickCaptor = argumentCaptor<View.OnClickListener>()
        verify(itemView).setOnClickListener(onClickCaptor.capture())

        onClickCaptor.firstValue.onClick(null)

        verify(expandableGroup).onToggleExpanded()
    }
}