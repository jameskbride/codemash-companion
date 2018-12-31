package com.jameskbride.codemashcompanion.sessions.list.listitems

import android.view.View
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.Session
import com.jameskbride.codemashcompanion.sessions.list.DateViewHolder
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.Item
import org.threeten.bp.format.DateTimeFormatter

class DateHeaderListItem constructor(val dateString:String): Item<DateViewHolder>(), ExpandableItem {
    private lateinit var onToggleListener: ExpandableGroup

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.onToggleListener = onToggleListener
    }

    override fun getLayout(): Int {
        return R.layout.sessions_date_header
    }

    override fun bind(viewHolder: DateViewHolder, position: Int) {
        val displayDate = DateTimeFormatter.ISO_DATE.parse(dateString)
        viewHolder.bind(DateTimeFormatter.ofPattern(Session.SHORT_DATE_FORMAT).format(displayDate))
        viewHolder.root.setOnClickListener {
            onToggleListener.onToggleExpanded()
        }
    }

    override fun createViewHolder(itemView: View): DateViewHolder {
        return DateViewHolder(itemView)
    }
}