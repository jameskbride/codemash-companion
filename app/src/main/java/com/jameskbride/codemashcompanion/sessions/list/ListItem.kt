package com.jameskbride.codemashcompanion.sessions.list

import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.sessions.list.ListItem.Companion.DATE_HEADER_TYPE
import com.jameskbride.codemashcompanion.sessions.list.ListItem.Companion.SESSION_ITEM_TYPE
import com.jameskbride.codemashcompanion.sessions.list.ListItem.Companion.TIME_HEADER_TYPE

import java.util.*

interface ListItem {
    fun getType(): Int

    companion object {
        val TIME_HEADER_TYPE = 0
        val SESSION_ITEM_TYPE = 1
        val DATE_HEADER_TYPE = 2
    }
}

class DateHeaderListItem constructor(val text:String): ListItem {
    override fun getType(): Int {
        return DATE_HEADER_TYPE
    }

}

class TimeHeaderListItem constructor(val sessionTime: Date): ListItem {

    override fun getType(): Int {
        return TIME_HEADER_TYPE
    }
}

class SessionListItem constructor(val session: FullSession): ListItem {
    override fun getType(): Int {
        return SESSION_ITEM_TYPE
    }
}