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

class SessionsRecyclerViewAdapterImpl(val sessionsFragmentPresenter: SessionsFragmentPresenter, val layoutInflaterFactory: LayoutInflaterFactory = LayoutInflaterFactory()) {
    var sessionsList: List<ListItem> = mutableListOf()

    fun getItemCount(): Int {
        return if (sessionsList.isNotEmpty()) { sessionsList.size } else { 1 }
    }

    fun setSessions(sessionData: SessionData, qtn: SessionsRecyclerViewAdapter) {
        var sessionsGroupedByDate: Map<String, List<FullSession>> = groupByDate(sessionData)
        var dateTimeSessions = groupByStartTime(sessionsGroupedByDate)
        sessionsList = populateSessionList(dateTimeSessions)

        qtn.notifyDataSetChanged()
    }

    private fun groupByStartTime(sessionsGroupedByDate: Map<String, List<FullSession>>): LinkedHashMap<String, Map<Date, List<FullSession?>>> {
        var dateTimesSessions: LinkedHashMap<String, Map<Date, List<FullSession?>>> = linkedMapOf()
        sessionsGroupedByDate.forEach { keyValue ->
            dateTimesSessions[keyValue.key] = keyValue.value.groupBy { session ->
                val dateFormatter = SimpleDateFormat(Session.TIMESTAMP_FORMAT)
                dateFormatter.parse(session?.SessionStartTime)
            }
        }
        return dateTimesSessions
    }

    private fun populateSessionList(dateTimesSessions: LinkedHashMap<String, Map<Date, List<FullSession?>>>): MutableList<ListItem> {
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

    private fun groupByDate(sessionData: SessionData): Map<String, List<FullSession>> {
        val sessionsGroupedByDate = groupSessionsByDate(sessionData)

        return sessionsGroupedByDate
    }

    private fun groupSessionsByDate(sessionData: SessionData): Map<String, List<FullSession>> {
        return sessionData.sessions.groupBy { session ->
            val dateFormatter = SimpleDateFormat(Session.TIMESTAMP_FORMAT)
            val calendar = Calendar.getInstance()
            calendar.time = dateFormatter.parse(session?.SessionStartTime)
            val shortDateFormatter = SimpleDateFormat(Session.SHORT_DATE_FORMAT)
            shortDateFormatter.format(calendar.time)
        }
    }

    fun onBindViewHolder(holder: SessionViewHolder?, position: Int) {
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

    private fun bindSessionListItem(holder: SessionViewHolder?, position: Int) {
        var itemViewHolder = holder as ItemViewHolder
        var sessionListItem = sessionsList[position] as SessionListItem
        itemViewHolder.bind(sessionListItem.session)
        itemViewHolder.view.setOnClickListener { view: View? ->
            sessionsFragmentPresenter.navigateToSessionDetail(sessionListItem.session)
        }
    }

    private fun bindDateHeaderListItem(holder: SessionViewHolder?, position: Int) {
        var dateHeaderViewHolder = holder as DateViewHolder
        var dateHeaderListItem = sessionsList[position] as DateHeaderListItem
        dateHeaderViewHolder.bind(dateHeaderListItem.text)
    }

    private fun bindTimeHeaderListItem(holder: SessionViewHolder?, position: Int) {
        var headerViewHolder = holder as TimeViewHolder
        var headerListItem = sessionsList[position] as TimeHeaderListItem
        headerViewHolder.bind(headerListItem.sessionTime)
    }

    fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SessionViewHolder {
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

        val view = layoutInflaterFactory.inflate(parent!!.context, R.layout.empty_sessions, parent!!)
        return EmptyViewHolder(view!!)
    }

    fun getItemViewType(position: Int): Int {
        return if (sessionsList.isNotEmpty()) { sessionsList[position].getType() } else { ListItem.EMPTY_TYPE }
    }
}