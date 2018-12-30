package com.jameskbride.codemashcompanion.sessions.list

import android.view.View
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.Item
import java.util.*

class DateHeaderListItem constructor(val text:String): Item<DateViewHolder>(), ExpandableItem {
    private lateinit var onToggleListener: ExpandableGroup

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.onToggleListener = onToggleListener
    }

    override fun getLayout(): Int {
        return R.layout.sessions_date_header
    }

    override fun bind(viewHolder: DateViewHolder, position: Int) {
        viewHolder.bind(text)
    }

    override fun createViewHolder(itemView: View): DateViewHolder {
        return DateViewHolder(itemView)
    }
}

class TimeHeaderListItem constructor(val sessionTime: Date): Item<TimeViewHolder>(), ExpandableItem {
    private lateinit var onToggleListener: ExpandableGroup

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.onToggleListener = onToggleListener
    }

    override fun bind(viewHolder: TimeViewHolder, position: Int) {
        viewHolder.bind(sessionTime)
    }

    override fun getLayout(): Int {
        return R.layout.sessions_time_header
    }

    override fun createViewHolder(itemView: View): TimeViewHolder {
        return TimeViewHolder(itemView)
    }
}

class SessionListItem(val session: FullSession, val onClick: View.OnClickListener): Item<ItemViewHolder>() {
    override fun bind(viewHolder: ItemViewHolder, position: Int) {
        viewHolder.bind(session)
        viewHolder.view.setOnClickListener(onClick)
    }

    override fun getLayout(): Int {
        return R.layout.sessions_item
    }

    override fun createViewHolder(itemView: View): ItemViewHolder {
        return ItemViewHolder(itemView)
    }
}

class EmptyListItem: Item<EmptyViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.no_data
    }

    override fun bind(viewHolder: EmptyViewHolder, position: Int) {
        viewHolder.bind()
    }

    override fun createViewHolder(itemView: View): EmptyViewHolder {
        return EmptyViewHolder(itemView)
    }
}