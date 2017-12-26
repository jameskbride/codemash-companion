package com.jameskbride.codemashcompanion.rooms

import com.jameskbride.codemashcompanion.R
import com.jameskbride.codemashcompanion.data.model.ConferenceRoom
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MapFinderTest {

    private lateinit var subject:MapFinder

    @Before
    fun setUp() {
        subject = MapFinder()
    }

    @Test
    fun itCanFindCypress() {
        val name = "Cypress"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.cypress, subject.findMap(room))
    }

    @Test
    fun itCanFindNile() {
        val name = "Nile"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.nile, subject.findMap(room))
    }

    @Test
    fun itCanFindOrange() {
        val name = "Orange"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.orange, subject.findMap(room))
    }

    @Test
    fun itCanFindMangrove() {
        val name = "Mangrove"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.mangrove, subject.findMap(room))
    }

    @Test
    fun itCanFindSalonA() {
        val name = "Salon A"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.salon_a, subject.findMap(room))
    }
    @Test
    fun itCanFindSalonB() {
        val name = "Salon B"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.salon_b, subject.findMap(room))
    }
    @Test
    fun itCanFindSalonC() {
        val name = "Salon C"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.salon_c, subject.findMap(room))
    }
    @Test
    fun itCanFindSalonD() {
        val name = "Salon D"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.salon_d, subject.findMap(room))
    }

    @Test
    fun itCanFindSalonE() {
        val name = "Salon E"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.salon_e, subject.findMap(room))
    }

    @Test
    fun itCanFindSalonF() {
        val name = "Salon F"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.salon_f, subject.findMap(room))
    }

    @Test
    fun itCanFindSalonG() {
        val name = "Salon G"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.salon_g, subject.findMap(room))
    }

    @Test
    fun itCanFindSalonH() {
        val name = "Salon H"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.salon_h, subject.findMap(room))
    }

    @Test
    fun itCanFindSuite1() {
        val name = "Suite 1"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.suite_1, subject.findMap(room))
    }

    @Test
    fun itCanFindSuite2() {
        val name = "Suite 2"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.suite_2, subject.findMap(room))
    }

    @Test
    fun itCanFindSuite3() {
        val name = "Suite 3"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.suite_3, subject.findMap(room))
    }

    @Test
    fun itCanFindSuite4() {
        val name = "Suite 4"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.suite_4, subject.findMap(room))
    }

    @Test
    fun itCanFindSuite5() {
        val name = "Suite 5"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.suite_5, subject.findMap(room))
    }

    @Test
    fun itCanFindSuite6() {
        val name = "Suite 6"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.suite_6, subject.findMap(room))
    }

    @Test
    fun itCanFindIndigoBay() {
        val name = "Indigo Bay"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.indigo_bay, subject.findMap(room))
    }

    @Test
    fun itCanFindZambezi() {
        val name = "Zambezi"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.zambezi, subject.findMap(room))
    }

    @Test
    fun itCanFindSagewood() {
        val name = "Sagewood"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.sagewood, subject.findMap(room))
    }

    @Test
    fun itCanFindZebrawood() {
        val name = "Zebrawood"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.zebrawood, subject.findMap(room))
    }

    @Test
    fun itCanFindAloeswood() {
        val name = "Aloeswood"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.aloeswood, subject.findMap(room))
    }

    @Test
    fun itCanFindLeopardwood() {
        val name = "Leopardwood"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.leopardwood, subject.findMap(room))
    }

    @Test
    fun itCanFindAcaciaSessionz() {
        val name = "Acacia - Sessionz"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.acacia, subject.findMap(room))
    }

    @Test
    fun itCanFindCrownPalm() {
        val name = "Crown Palm"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.crown_palm, subject.findMap(room))
    }

    @Test
    fun itCanFindPortia() {
        val name = "Portia"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.portia, subject.findMap(room))
    }

    @Test
    fun itCanFindWisteria() {
        val name = "Wisteria"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.wisteria, subject.findMap(room))
    }

    @Test
    fun itCanFindBanyan() {
        val name = "Banyan"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.banyan, subject.findMap(room))
    }

    @Test
    fun itCanFindIronwood() {
        val name = "Ironwood"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.ironwood, subject.findMap(room))
    }

    @Test
    fun itCanFindRosewood() {
        val name = "Rosewood"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.rosewood, subject.findMap(room))
    }

    @Test
    fun itCanFindGuava() {
        val name = "Guava"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.guava, subject.findMap(room))
    }
    @Test
    fun itCanFindEmpress() {
        val name = "Empress"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.empress, subject.findMap(room))
    }
    @Test
    fun itCanFindTamarind() {
        val name = "Tamarind"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.tamarind, subject.findMap(room))
    }

    @Test
    fun itCanFindEbony() {
        val name = "Ebony"
        val room = buildOneRoomList(name)

        assertEquals(R.drawable.ebony, subject.findMap(room))
    }

    @Test
    fun itReturnsTheFullMapThereThereAreNoRooms() {
        assertEquals(R.drawable.full, subject.findMap(listOf()))
    }

    @Test
    fun itReturnsTheFullMapIfThereAreNoMatches() {
        assertEquals(R.drawable.full, subject.findMap(buildOneRoomList("doesn't exist")))
    }

    @Test
    fun itReturnsTheMatchFromTheFirstRoomIfMultipleRoomsArePassed() {
        val rooms = listOf(
                ConferenceRoom(name = "Nile", sessionId = ""),
                ConferenceRoom(name = "Orange", sessionId = "")
        )

        assertEquals(R.drawable.nile, subject.findMap(rooms))
    }

    private fun buildOneRoomList(name: String): List<ConferenceRoom> {
        val cypress = listOf(ConferenceRoom(name = name, sessionId = ""))
        return cypress
    }
}