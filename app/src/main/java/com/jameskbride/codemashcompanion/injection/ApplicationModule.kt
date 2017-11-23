package com.jameskbride.codemashcompanion.injection

import com.jameskbride.codemashcompanion.main.MainActivityImpl
import com.jameskbride.codemashcompanion.main.MainActivityPresenter
import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus
import javax.inject.Singleton

@Module
open class ApplicationModule {

    @Provides
    @Singleton
    fun makeEventBus(): EventBus {
        return EventBus.builder().throwSubscriberException(true).build()
    }

    @Provides
    fun makeMainActivityPresenter(eventBus: EventBus): MainActivityPresenter {
        return MainActivityPresenter(eventBus)
    }

    @Provides
    fun makeMainActivityImpl(presenter: MainActivityPresenter): MainActivityImpl {
        return MainActivityImpl(presenter)
    }
}