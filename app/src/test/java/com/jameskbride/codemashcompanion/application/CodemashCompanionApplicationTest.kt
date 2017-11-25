package com.jameskbride.codemashcompanion.application

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.injection.ApplicationComponent
import com.jameskbride.codemashcompanion.network.service.CodemashService
import io.mockk.Runs
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class CodemashCompanionApplicationTest {

    private lateinit var codemashService: CodemashService
    private lateinit var conferenceRepository: ConferenceRepository
    private lateinit var applicationComponentFactory: ApplicationComponentFactory
    private lateinit var applicationComponent: ApplicationComponent

    private lateinit var subject: CodemashCompanionApplication

    @Before
    fun setUp() {
        codemashService = mockk()
        conferenceRepository = mockk()
        applicationComponentFactory = mockk()
        applicationComponent = mockk()


        subject = CodemashCompanionApplication(applicationComponentFactory)
        subject.conferenceRepository = conferenceRepository
        subject.codemashService = codemashService

        every { applicationComponent.inject(subject) } just Runs
        every{applicationComponentFactory.build(subject)} returns applicationComponent
        every { codemashService.open() } just Runs
        every { conferenceRepository.open() } just Runs
    }

    @Test
    fun onCreateItInjectsDependencies() {
        subject.configure()

        verify{applicationComponentFactory.build(subject)}
        verify{applicationComponent.inject(subject)}
    }

    @Test
    fun onCreateItOpensTheCodemashService() {
        subject.configure()

        verify{codemashService.open()}
    }

    @Test
    fun onCreateItOpensTheConferenceRepository() {
        subject.configure()

        verify{conferenceRepository.open()}
    }
}