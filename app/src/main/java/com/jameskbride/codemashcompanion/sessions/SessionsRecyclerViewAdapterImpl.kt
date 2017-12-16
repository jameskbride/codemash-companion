package com.jameskbride.codemashcompanion.sessions

import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.jameskbride.codemashcompanion.data.model.Session
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory
import java.text.SimpleDateFormat
import java.util.*

class SessionsRecyclerViewAdapterImpl(val layoutInflaterFactory: LayoutInflaterFactory = LayoutInflaterFactory()) {
    var sessionsList: MutableList<ListItem> = mutableListOf()

    fun getItemCount(): Int {
        return sessionsList.size
    }

    fun setSessions(sessionData: SessionData, qtn: SessionsRecyclerViewAdapter) {
        var dateTimesSessions:LinkedHashMap<Int, Map<Date, List<FullSession?>>> = linkedMapOf()
        sessionData.sessions.groupBy { session ->
            val dateFormatter = SimpleDateFormat(Session.TIMESTAMP_FORMAT)
            val calendar = Calendar.getInstance()
            calendar.time = dateFormatter.parse(session?.SessionStartTime)
            calendar.get(Calendar.DATE)
        }.forEach{keyValue ->
            dateTimesSessions[keyValue.key] = keyValue.value.groupBy { session ->
                val dateFormatter = SimpleDateFormat(Session.TIMESTAMP_FORMAT)
                dateFormatter.parse(session?.SessionStartTime)
            }
        }

        dateTimesSessions.keys.sorted().forEachIndexed{ index, date ->
            sessionsList.add(DateHeaderListItem("Day ${index + 1}"))
            dateTimesSessions[date]!!.keys.sorted().forEach {time ->
                sessionsList.add(TimeHeaderListItem(time))
                dateTimesSessions[date]!![time]!!.forEach { session ->
                    sessionsList.add(SessionListItem(session!!))
                }
            }
        }

        qtn.notifyDataSetChanged()
    }

    fun onBindViewHolder(holder: SessionViewHolder?, position: Int) {
        if (ListItem.TIME_HEADER_TYPE == getItemViewType(position)) {
            var headerViewHolder = holder as TimeViewHolder
            var headerListItem = sessionsList[position] as TimeHeaderListItem
            headerViewHolder.bind(headerListItem.sessionTime)
        } else if (ListItem.DATE_HEADER_TYPE == getItemViewType(position)) {
            var dateHeaderViewHolder = holder as DateViewHolder
            var dateHeaderListItem = sessionsList[position] as DateHeaderListItem
            dateHeaderViewHolder.bind(dateHeaderListItem.text)
        } else {
            var itemViewHolder = holder as ItemViewHolder
            var headerListItem = sessionsList[position] as SessionListItem
            itemViewHolder.bind(headerListItem.session)
        }
    }

    fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SessionViewHolder {
        if (ListItem.TIME_HEADER_TYPE == viewType) {
            val view = layoutInflaterFactory.inflate(parent!!.context, R.layout.sessions_time_header, parent!!)
            return TimeViewHolder(view!!)
        } else if (ListItem.DATE_HEADER_TYPE == viewType) {
            val view = layoutInflaterFactory.inflate(parent!!.context, R.layout.sessions_date_header, parent!!)
            return DateViewHolder(view!!)
        }

        val view = layoutInflaterFactory.inflate(parent!!.context, R.layout.sessions_item, parent!!)
        return ItemViewHolder(view!!)
    }

    fun getItemViewType(position: Int): Int {
        return sessionsList[position].getType()
    }
}