package com.jameskbride.codemashcompanion.application

import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.injection.ApplicationComponent
import com.jameskbride.codemashcompanion.network.service.CodemashService
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class CodemashCompanionApplicationTest {

    private lateinit var codemashService: CodemashService
    private lateinit var conferenceRepository: ConferenceRepository
    private lateinit var applicationComponentFactory: ApplicationComponentFactory
    private lateinit var applicationComponent: ApplicationComponent

    private lateinit var subject: CodemashCompanionApplication

    @Before
    fun setUp() {
        codemashService = mock(CodemashService::class.java)
        conferenceRepository = mock(ConferenceRepository::class.java)
        applicationComponentFactory = mock(ApplicationComponentFactory::class.java)
        applicationComponent = mock(ApplicationComponent::class.java)


        subject = CodemashCompanionApplication(applicationComponentFactory)
        subject.conferenceRepository = conferenceRepository
        subject.codemashService = codemashService

        `when`(applicationComponentFactory.build(subject)).thenReturn(applicationComponent)
    }

    @Test
    fun onCreateItInjectsDependencies() {
        subject.configure()

        verify(applicationComponentFactory).build(subject)
        verify(applicationComponent).inject(subject)
    }

    @Test
    fun onCreateItOpensTheCodemashService() {
        subject.configure()

        verify(codemashService).open()
    }

    @Test
    fun onCreateItOpensTheConferenceRepository() {
        subject.configure()

        verify(conferenceRepository).open()
    }
}