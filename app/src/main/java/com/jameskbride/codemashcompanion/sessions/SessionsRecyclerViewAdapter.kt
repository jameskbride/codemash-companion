package com.jameskbride.codemashcompanion.sessions

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.network.Session
import com.jameskbride.codemashcompanion.sessions.ListItem.Companion.HEADER_TYPE
import com.jameskbride.codemashcompanion.sessions.ListItem.Companion.ITEM_TYPE
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory
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

    fun setSessions(sessionData: LinkedHashMap<Date, Array<Session>>) {
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

    fun setSessions(sessions: LinkedHashMap<Date, Array<Session>>, qtn: SessionsRecyclerViewAdapter) {
        sessions.keys.sorted().forEach{key ->
            sessionsList.add(HeaderListItem(key))
            sessions[key]?.forEach { session ->
                sessionsList.add(SessionListItem(session))
            }
        }
        qtn.notifyDataSetChanged()
    }

    fun onBindViewHolder(holder: SessionViewHolder?, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SessionViewHolder {
        if (ListItem.HEADER_TYPE == viewType) {
            val view = layoutInflaterFactory.inflate(parent!!.context, R.layout.sessions_header, parent!!)
            return HeaderViewHolder(view!!)
        }

        val view = layoutInflaterFactory.inflate(parent!!.context, R.layout.sessions_item, parent!!)
        return ItemViewHolder(view!!)
    }

    fun getItemViewType(position: Int): Int {
        return sessionsList[position].getType()
    }
}

open class SessionViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView)
class ItemViewHolder constructor(itemView: View): SessionViewHolder(itemView)
class HeaderViewHolder constructor(itemView: View): SessionViewHolder(itemView)

interface ListItem {
    fun getType(): Int

    companion object {
        val HEADER_TYPE = 0
        val ITEM_TYPE = 1
    }
}

class HeaderListItem constructor(val sessionTime:Date): ListItem {

    override fun getType(): Int {
        return HEADER_TYPE
    }
}

class SessionListItem constructor(val session:Session): ListItem {
    override fun getType(): Int {
        return ITEM_TYPE
    }
}