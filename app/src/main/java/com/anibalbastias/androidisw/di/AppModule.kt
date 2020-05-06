package com.anibalbastias.androidisw.di

import android.content.Context
import android.net.ConnectivityManager
import com.anibalbastias.androidisw.BuildConfig
import com.anibalbastias.androidisw.R
import com.anibalbastias.androidisw.data.datasource.remote.RemoteDataStore
import com.anibalbastias.androidisw.data.datasource.remote.api.NewsApi
import com.anibalbastias.androidisw.domain.mapper.NewsMapper
import com.anibalbastias.androidisw.domain.repository.RemoteRepository
import com.anibalbastias.androidisw.domain.usecase.GetNewsUseCase
import com.anibalbastias.androidisw.presentation.mapper.UiNewsMapper
import com.anibalbastias.androidisw.presentation.viewmodel.NewsViewModel
import com.anibalbastias.androidisw.ui.NewsNavigator
import com.anibalbastias.library.base.data.interceptor.FakeInterceptor
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.factory
import org.koin.experimental.builder.factoryBy
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    // Android Services
    single {
        androidApplication().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    // Retrofit
    single {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        val clientBuilder = OkHttpClient.Builder()

        if (BuildConfig.FLAVOR.equals("dummy")) {
            clientBuilder.addInterceptor(FakeInterceptor(androidApplication()))
        } else {
            if (BuildConfig.DEBUG) {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                clientBuilder.addInterceptor(httpLoggingInterceptor)
            }
        }

        clientBuilder.callTimeout(1, TimeUnit.MINUTES)
        clientBuilder.connectTimeout(1, TimeUnit.MINUTES)
        clientBuilder.writeTimeout(1, TimeUnit.MINUTES)
        clientBuilder.readTimeout(1, TimeUnit.MINUTES)
        clientBuilder.build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(androidApplication().getString(R.string.shellchallenge_endpoint))
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>()
            .create(NewsApi::class.java) as NewsApi
    }

    // Picasso
    single {
        Picasso.get()
    }

    // ViewModels
    viewModel<NewsViewModel>()

    // Factories
    factoryBy<RemoteRepository, RemoteDataStore>()

    // Use Cases
    factory<GetNewsUseCase>()

    // Mapper
    factory<NewsMapper>()
    factory<UiNewsMapper>()

    // Navigator
    factory<NewsNavigator>()
}
