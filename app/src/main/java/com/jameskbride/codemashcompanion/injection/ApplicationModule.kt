package com.jameskbride.codemashcompanion.injection

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
}