package com.jameskbride.codemashcompanion.rooms

import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.ConferenceRoom

class MapFinder {

    val roomsMap = mapOf(
            Pair("Cypress", R.drawable.cypress),
            Pair("Nile", R.drawable.nile),
            Pair("Orange", R.drawable.orange),
            Pair("Zambezi", R.drawable.zambezi),
            Pair("Salon A", R.drawable.salon_a),
            Pair("Salon B", R.drawable.salon_b),
            Pair("Salon C", R.drawable.salon_c),
            Pair("Salon D", R.drawable.salon_d),
            Pair("Salon E", R.drawable.salon_e),
            Pair("Salon F", R.drawable.salon_f),
            Pair("Salon G", R.drawable.salon_g),
            Pair("Salon H", R.drawable.salon_h),
            Pair("Suite 1", R.drawable.suite_1),
            Pair("Suite 2", R.drawable.suite_2),
            Pair("Suite 3", R.drawable.suite_3),
            Pair("Suite 4", R.drawable.suite_4),
            Pair("Suite 5", R.drawable.suite_5),
            Pair("Suite 6", R.drawable.suite_6),
            Pair("Indigo Bay", R.drawable.indigo_bay),
            Pair("Sagewood", R.drawable.sagewood),
            Pair("Zebrawood", R.drawable.zebrawood),
            Pair("Aloeswood", R.drawable.aloeswood),
            Pair("Leopardwood", R.drawable.leopardwood),
            Pair("Rosewood", R.drawable.rosewood),
            Pair("Acacia - Sessionz", R.drawable.acacia),
            Pair("Crown Palm", R.drawable.crown_palm),
            Pair("Ironwood", R.drawable.ironwood),
            Pair("Ironwood - Open Spacez", R.drawable.ironwood),
            Pair("Banyan", R.drawable.banyan),
            Pair("Banyan Hut", R.drawable.banyan),
            Pair("Portia", R.drawable.portia),
            Pair("Wisteria", R.drawable.wisteria),
            Pair("Empress", R.drawable.empress),
            Pair("Mangrove", R.drawable.mangrove),
            Pair("Ebony", R.drawable.ebony),
            Pair("Tamarind", R.drawable.tamarind),
            Pair("Guava", R.drawable.guava)
    )

    fun findMap(conferenceRooms: List<ConferenceRoom> = listOf()):Int {
        return if (conferenceRooms.isNotEmpty()) {
            roomsMap[conferenceRooms.first().name] ?: R.drawable.full
        } else {
            R.drawable.full
        }
    }
}