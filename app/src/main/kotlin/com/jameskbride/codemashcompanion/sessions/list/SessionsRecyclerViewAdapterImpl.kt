package com.jameskbride.codemashcompanion.sessions.list

import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.Session
import com.jameskbride.codemashcompanion.sessions.SessionsFragmentPresenter
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory
import java.text.SimpleDateFormat
import java.util.*

open class SessionsRecyclerViewAdapterImpl(open val sessionsFragmentPresenter: SessionsFragmentPresenter, open val layoutInflaterFactory: LayoutInflaterFactory = LayoutInflaterFactory()) {
    var sessionsList: List<ListItem> = mutableListOf()

    fun getItemCount(): Int {
        return if (sessionsList.isNotEmpty()) { sessionsList.size } else { 1 }
    }

    fun setSessions(sessionData: SessionData, qtn: SessionsRecyclerViewAdapter) {
        val sessionsByDate = sessionData.groupSessionsByDate()
        val dateTimeSessions = groupByStartTime(sessionsByDate)
        sessionsList = populateSessionList(dateTimeSessions)

        qtn.notifyDataSetChanged()
    }

    private fun groupByStartTime(sessionsGroupedByDate: List<SessionsByDate>): Map<String, Map<Date, List<FullSession?>>> {
        return sessionsGroupedByDate.map { sessionsByDate ->
            sessionsByDate.sessionsByTime()
        }.toMap()
    }

    private fun populateSessionList(dateTimesSessions: Map<String, Map<Date, List<FullSession?>>>): List<ListItem> {
        var sessionsList:MutableList<ListItem> = mutableListOf()
        val dateFormatter = SimpleDateFormat(Session.SHORT_DATE_FORMAT)
        dateTimesSessions.keys.sortedWith(compareBy{ dateString ->
            dateFormatter.parse(dateString)
        }).forEach{ date ->
            sessionsList.add(DateHeaderListItem(date))
            dateTimesSessions[date]!!.keys.sorted().forEach { time ->
                sessionsList.add(TimeHeaderListItem(time))
                dateTimesSessions[date]!![time]!!.forEach { session ->
                    sessionsList.add(SessionListItem(session!!))
                }
            }
        }

        return sessionsList
    }

    fun onBindViewHolder(holder: SessionViewHolder, position: Int) {
        when(getItemViewType(position)) {
            ListItem.TIME_HEADER_TYPE -> bindTimeHeaderListItem(holder, position)
            ListItem.DATE_HEADER_TYPE -> bindDateHeaderListItem(holder, position)
            ListItem.SESSION_ITEM_TYPE -> bindSessionListItem(holder, position)
            else -> bindEmptyListItem(holder)
        }
    }

    fun bindEmptyListItem(holder: SessionViewHolder?) {
        var emptyViewHolder = holder as EmptyViewHolder
        emptyViewHolder.bind()
    }

    private fun bindSessionListItem(holder: SessionViewHolder, position: Int) {
        var itemViewHolder = holder as ItemViewHolder
        var sessionListItem = sessionsList[position] as SessionListItem
        itemViewHolder.bind(sessionListItem.session)
        itemViewHolder.view.setOnClickListener { view: View? ->
            sessionsFragmentPresenter.navigateToSessionDetail(sessionListItem.session)
        }
    }

    private fun bindDateHeaderListItem(holder: SessionViewHolder, position: Int) {
        var dateHeaderViewHolder = holder as DateViewHolder
        var dateHeaderListItem = sessionsList[position] as DateHeaderListItem
        dateHeaderViewHolder.bind(dateHeaderListItem.text)
    }

    private fun bindTimeHeaderListItem(holder: SessionViewHolder, position: Int) {
        var headerViewHolder = holder as TimeViewHolder
        var headerListItem = sessionsList[position] as TimeHeaderListItem
        headerViewHolder.bind(headerListItem.sessionTime)
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SessionViewHolder {
        if (ListItem.TIME_HEADER_TYPE == viewType) {
            val view = layoutInflaterFactory.inflate(parent!!.context, R.layout.sessions_time_header, parent!!)
            return TimeViewHolder(view!!)
        } else if (ListItem.DATE_HEADER_TYPE == viewType) {
            val view = layoutInflaterFactory.inflate(parent!!.context, R.layout.sessions_date_header, parent!!)
            return DateViewHolder(view!!)
        } else if (ListItem.SESSION_ITEM_TYPE == viewType){
            val view = layoutInflaterFactory.inflate(parent!!.context, R.layout.sessions_item, parent!!)
            return ItemViewHolder(view!!)
        }

        return buildEmptyViewHolder(parent)
    }

    open fun buildEmptyViewHolder(parent: ViewGroup): EmptyViewHolder {
        val view = layoutInflaterFactory.inflate(parent!!.context, R.layout.no_data, parent!!)
        return EmptyViewHolder(view!!)
    }

    fun getItemViewType(position: Int): Int {
        return if (sessionsList.isNotEmpty()) { sessionsList[position].getType() } else { ListItem.EMPTY_TYPE }
    }
}