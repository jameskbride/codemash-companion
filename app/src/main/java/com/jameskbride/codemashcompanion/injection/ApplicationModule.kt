package com.jameskbride.codemashcompanion.injection

import android.arch.persistence.room.Room
import com.jameskbride.codemashcompanion.BuildConfig
import com.jameskbride.codemashcompanion.application.CodemashCompanionApplication
import com.jameskbride.codemashcompanion.data.ConferenceDao
import com.jameskbride.codemashcompanion.data.ConferenceDatabase
import com.jameskbride.codemashcompanion.data.ConferenceRepository
import com.jameskbride.codemashcompanion.error.ErrorDialogImpl
import com.jameskbride.codemashcompanion.error.ErrorDialogPresenter
import com.jameskbride.codemashcompanion.main.MainActivityImpl
import com.jameskbride.codemashcompanion.network.CodemashApi
import com.jameskbride.codemashcompanion.network.service.CodemashService
import com.jameskbride.codemashcompanion.sessions.SessionsFragmentImpl
import com.jameskbride.codemashcompanion.sessions.SessionsFragmentPresenter
import com.jameskbride.codemashcompanion.sessions.detail.SessionDetailActivityImpl
import com.jameskbride.codemashcompanion.sessions.detail.SessionDetailActivityPresenter
import com.jameskbride.codemashcompanion.speakers.SpeakersFragmentImpl
import com.jameskbride.codemashcompanion.speakers.SpeakersFragmentPresenter
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailActivityImpl
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailFragmentImpl
import com.jameskbride.codemashcompanion.speakers.detail.SpeakerDetailFragmentPresenter
import com.jameskbride.codemashcompanion.splash.SplashActivityImpl
import com.jameskbride.codemashcompanion.splash.SplashActivityPresenter
import com.jameskbride.codemashcompanion.utils.IntentFactory
import com.jameskbride.codemashcompanion.utils.LayoutInflaterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.greenrobot.eventbus.EventBus
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
open class ApplicationModule(private val codemashCompanionApplication: CodemashCompanionApplication) {

    @Provides
    @Named("process")
    @Singleton
    fun makeProcessScheduler(): Scheduler {
        return Schedulers.io()
    }

    @Provides
    @Named("main")
    @Singleton
    fun makeAndroidScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    @Singleton
    fun makeOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()
    }

    @Provides
    @Singleton
    fun makeRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .build()
    }

    @Provides
    @Singleton
    fun makeCodemashApi(retrofit: Retrofit): CodemashApi {
        return retrofit.create(CodemashApi::class.java)
    }

    @Provides
    @Singleton
    fun makeCodemashService(codemashApi: CodemashApi, eventBus: EventBus,
                            @Named("process") processScheduler: Scheduler,
                            @Named("main") androidScheduler: Scheduler): CodemashService {
        return CodemashService(codemashApi, eventBus, processScheduler, androidScheduler)
    }

    @Provides
    @Singleton
    fun makeEventBus(): EventBus {
        return EventBus.builder().throwSubscriberException(true).build()
    }

    @Provides
    @Singleton
    fun makeConferenceDatabase(): ConferenceDatabase {
        return Room
                .databaseBuilder(
                        codemashCompanionApplication.applicationContext,
                        ConferenceDatabase::class.java,
                        "codemash")
                .build()
    }

    @Provides
    @Singleton
    fun makeConferenceDao(conferenceDatabase: ConferenceDatabase): ConferenceDao {
        return conferenceDatabase.conferenceDao()
    }

    @Provides
    @Singleton
    fun makeConferenceRepository(conferenceDao: ConferenceDao, eventBus: EventBus): ConferenceRepository {
        return ConferenceRepository(conferenceDao, eventBus)
    }

    @Provides
    @Singleton
    fun makeIntentFactory(): IntentFactory {
        return IntentFactory()
    }

    @Provides
    @Singleton
    fun makeLayoutInflaterFactory(): LayoutInflaterFactory {
        return LayoutInflaterFactory()
    }

    @Provides
    fun makeSplashActivityImpl(presenter: SplashActivityPresenter): SplashActivityImpl {
        return SplashActivityImpl(presenter)
    }

    @Provides
    fun makeSplashActivityPresenter(eventBus: EventBus, conferenceRepository: ConferenceRepository,
                                    @Named("process") processScheduler: Scheduler,
                                    @Named("main") androidScheduler: Scheduler): SplashActivityPresenter {
        return SplashActivityPresenter(eventBus, conferenceRepository, processScheduler, androidScheduler)
    }

    @Provides
    fun makeMainActivityImpl(): MainActivityImpl {
        return MainActivityImpl()
    }

    @Provides
    fun makeSpeakersFragmentPresenter(eventBus: EventBus, conferenceRepository: ConferenceRepository,
                                      @Named("process") processScheduler: Scheduler,
                                      @Named("main") androidScheduler: Scheduler): SpeakersFragmentPresenter {
        return SpeakersFragmentPresenter(eventBus, conferenceRepository, processScheduler, androidScheduler)
    }

    @Provides
    fun makeSpeakersFragmentImpl(speakersFragmentPresenter: SpeakersFragmentPresenter): SpeakersFragmentImpl {
        return SpeakersFragmentImpl(speakersFragmentPresenter)
    }

    @Provides
    fun makeSpeakerDetailActivityImpl():SpeakerDetailActivityImpl {
        return SpeakerDetailActivityImpl()
    }

    @Provides
    fun makeSpeakerDetailFragmentPresenter(conferenceRepository: ConferenceRepository,
                                           @Named("process") processScheduler: Scheduler,
                                           @Named("main") androidScheduler: Scheduler): SpeakerDetailFragmentPresenter {
        return SpeakerDetailFragmentPresenter(conferenceRepository, processScheduler, androidScheduler)
    }

    @Provides
    fun makeSpeakerDetailFragmentImpl(presenter: SpeakerDetailFragmentPresenter): SpeakerDetailFragmentImpl {
        return SpeakerDetailFragmentImpl(presenter)
    }

    @Provides
    fun makeSessionsFragmentPresenter(conferenceRepository: ConferenceRepository,
                                      @Named("process") processScheduler: Scheduler,
                                      @Named("main") androidScheduler: Scheduler, eventBus: EventBus): SessionsFragmentPresenter {
        return SessionsFragmentPresenter(conferenceRepository, processScheduler, androidScheduler, eventBus)
    }

    @Provides
    fun makeSessionsFragmentImpl(sessionsFragmentPresenter: SessionsFragmentPresenter): SessionsFragmentImpl {
        return SessionsFragmentImpl(sessionsFragmentPresenter)
    }

    @Provides
    fun makeSessionDetailActivityPresenter(conferenceRepository: ConferenceRepository,
                                           @Named("process") processScheduler: Scheduler,
                                           @Named("main") androidScheduler: Scheduler): SessionDetailActivityPresenter {
        return SessionDetailActivityPresenter(conferenceRepository, processScheduler, androidScheduler)
    }

    @Provides
    fun makeSessionDetailActivityImpl(sessionDetailActivityPresenter: SessionDetailActivityPresenter):SessionDetailActivityImpl {
        return SessionDetailActivityImpl(sessionDetailActivityPresenter)
    }

    @Provides
    fun makeErrorDialogPresenter(eventBus: EventBus): ErrorDialogPresenter {
        return ErrorDialogPresenter(eventBus)
    }

    @Provides
    fun makeErrorDialogImpl(errorDialogPresenter: ErrorDialogPresenter):ErrorDialogImpl {
        return ErrorDialogImpl(errorDialogPresenter)
    }

}
