package com.jameskbride.codemashcompanion.sessions

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.network.Session
import com.jameskbride.codemashcompanion.sessions.ListItem.Companion.DATE_HEADER_TYPE
import com.jameskbride.codemashcompanion.sessions.ListItem.Companion.TIME_HEADER_TYPE
import com.jameskbride.codemashcompanion.sessions.ListItem.Companion.SESSION_ITEM_TYPE
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.LinkedHashMap

class SessionsRecyclerViewAdapter constructor(
        val impl: SessionsRecyclerViewAdapterImpl = SessionsRecyclerViewAdapterImpl())
    : RecyclerView.Adapter<SessionViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return impl.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: SessionViewHolder?, position: Int) {
        impl.onBindViewHolder(holder, position)
    }

    override fun getItemCount(): Int {
        return impl.getItemCount()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SessionViewHolder {
        return impl.onCreateViewHolder(parent, viewType)
    }

    fun setSessions(sessionData: SessionData) {
        impl.setSessions(sessionData, this)
    }
}

class SessionsViewAdapterFactory {
    fun make(): SessionsRecyclerViewAdapter {
        return SessionsRecyclerViewAdapter()
    }
}

class SessionsRecyclerViewAdapterImpl(val layoutInflaterFactory: LayoutInflaterFactory = LayoutInflaterFactory()) {
    var sessionsList: MutableList<ListItem> = mutableListOf()

    fun getItemCount(): Int {
        return sessionsList.size
    }

    fun setSessions(sessionData: SessionData, qtn: SessionsRecyclerViewAdapter) {
        var dateTimesSessions:LinkedHashMap<Int, Map<Date, List<Session>>> = linkedMapOf()
        sessionData.sessions.groupBy { session ->
            val dateFormatter = SimpleDateFormat(Session.TIMESTAMP_FORMAT)
            val calendar = Calendar.getInstance()
            calendar.time = dateFormatter.parse(session.SessionStartTime)
            calendar.get(Calendar.DATE)
        }.forEach{keyValue ->
            dateTimesSessions[keyValue.key] = keyValue.value.groupBy { session ->
                val dateFormatter = SimpleDateFormat(Session.TIMESTAMP_FORMAT)
                dateFormatter.parse(session.SessionStartTime)
            }
        }

        dateTimesSessions.keys.sorted().forEachIndexed{ index, date ->
            sessionsList.add(DateHeaderListItem("Day ${index + 1}"))
            dateTimesSessions[date]!!.keys.sorted().forEach {time ->
                sessionsList.add(TimeHeaderListItem(time))
                dateTimesSessions[date]!![time]!!.forEach { session ->
                    sessionsList.add(SessionListItem(session))
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

open class SessionViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView)
class ItemViewHolder constructor(itemView: View): SessionViewHolder(itemView) {
    fun bind(session: Session) {
        itemView.findViewById<TextView>(R.id.session_title).text = session.Title
    }
}

class TimeViewHolder constructor(itemView: View): SessionViewHolder(itemView) {
    fun bind(firstDate: Date) {
        val simpleDateFormatter = SimpleDateFormat("h:mm a")
        itemView.findViewById<TextView>(R.id.session_time).text = simpleDateFormatter.format(firstDate)
    }
}

class DateViewHolder constructor(itemView: View): SessionViewHolder(itemView) {
    fun bind(text: String) {
        itemView.findViewById<TextView>(R.id.session_date).text = text
    }

}

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

class TimeHeaderListItem constructor(val sessionTime:Date): ListItem {

    override fun getType(): Int {
        return TIME_HEADER_TYPE
    }
}

class SessionListItem constructor(val session:Session): ListItem {
    override fun getType(): Int {
        return SESSION_ITEM_TYPE
    }
}