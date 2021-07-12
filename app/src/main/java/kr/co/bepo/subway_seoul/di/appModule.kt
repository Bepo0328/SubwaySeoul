package kr.co.bepo.subway_seoul.di

import android.app.Activity
import com.google.firebase.ktx.BuildConfig
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kr.co.bepo.subway_seoul.data.api.StationApi
import kr.co.bepo.subway_seoul.data.api.StationArrivalsApi
import kr.co.bepo.subway_seoul.data.api.StationStorageApi
import kr.co.bepo.subway_seoul.data.api.Url
import kr.co.bepo.subway_seoul.data.db.AppDatabase
import kr.co.bepo.subway_seoul.data.preference.PreferenceManager
import kr.co.bepo.subway_seoul.data.preference.SharedPreferenceManger
import kr.co.bepo.subway_seoul.data.repository.StationRepository
import kr.co.bepo.subway_seoul.data.repository.StationRepositoryImpl
import kr.co.bepo.subway_seoul.presentation.stationarrivals.StationArrivalsContract
import kr.co.bepo.subway_seoul.presentation.stationarrivals.StationArrivalsFragment
import kr.co.bepo.subway_seoul.presentation.stationarrivals.StationArrivalsPresenter
import kr.co.bepo.subway_seoul.presentation.stations.StationsContract
import kr.co.bepo.subway_seoul.presentation.stations.StationsFragment
import kr.co.bepo.subway_seoul.presentation.stations.StationsPresenter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val appModule = module {

    single { Dispatchers.IO }

    // Database
    single { AppDatabase.build(androidApplication()) }
    single { get<AppDatabase>().stationDao() }

    // Preference
    single { androidContext().getSharedPreferences("preference", Activity.MODE_PRIVATE) }
    single<PreferenceManager> { SharedPreferenceManger(get()) }

    // Api
    single<StationApi> { StationStorageApi(Firebase.storage) }
    single {
        OkHttpClient()
            .newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            })
            .build()
    }
    single<StationArrivalsApi> {
        Retrofit.Builder().baseUrl(Url.SEOUL_DATA_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
            .create()
    }

    // Repository
    single<StationRepository> { StationRepositoryImpl(get(), get(), get(), get(), get()) }

    // Presentation
    scope<StationsFragment> {
        scoped<StationsContract.Presenter> {
            StationsPresenter(getSource(), get())
        }
    }
    scope<StationArrivalsFragment> {
        scoped<StationArrivalsContract.Presenter> {
            StationArrivalsPresenter(getSource(), get(), get())
        }
    }

}