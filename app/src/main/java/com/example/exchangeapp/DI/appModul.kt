package com.example.exchangeapp.DI

import androidx.databinding.ktx.BuildConfig
import com.example.exchangeapp.data.repository.RepositoryImpl
import com.example.exchangeapp.data.repository.dataSource.remote.RemoteDataSourceImpl
import com.example.exchangeapp.data.retrofit.ExchangeRateRetrofit
import com.example.exchangeapp.domain.repository.Repository
import com.example.exchangeapp.domain.repository.dataSource.remote.RemoteDataSource
import com.example.exchangeapp.domain.useCase.GetExchangeRate
import com.example.exchangeapp.domain.useCase.GetUseCases
import com.example.exchangeapp.presentation.exchangeCalculate.ExchangeCalculateViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    single<Repository> { RepositoryImpl(get()) }
    single { GetExchangeRate(get()) }
    single { GetUseCases(get()) }

}

// 레트로핏 모듈
val retrofitModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }


    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single {
        Retrofit.Builder()
            .baseUrl(ExchangeRateRetrofit.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeRateRetrofit::class.java)
    }

}

// ViewModel 에 대한 모듈
val viewModelModule = module {
    viewModel { ExchangeCalculateViewModel(get()) }
}