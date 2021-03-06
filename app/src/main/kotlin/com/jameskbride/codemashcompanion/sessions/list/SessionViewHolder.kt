package com.jameskbride.codemashcompanion.sessions.list

import android.view.View
import android.widget.TextView
import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.FullSession
import com.xwray.groupie.ViewHolder
import java.text.SimpleDateFormat
import java.util.*

open class SessionViewHolder constructor(itemView: View): ViewHolder(itemView)

class ItemViewHolder constructor(val view: View): SessionViewHolder(view) {
    fun bind(session: FullSession) {
        itemView.findViewById<TextView>(R.id.session_title).text = session.Title
        val rooms = session.conferenceRooms.map { it.name }.joinToString(", ")
        itemView.findViewById<TextView>(R.id.rooms).text = rooms
        itemView.findViewById<TextView>(R.id.session_category).text = session.Category
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

class EmptyViewHolder constructor(itemView: View): SessionViewHolder(itemView) {
    fun bind() {
    }
}