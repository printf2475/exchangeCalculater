package com.example.exchangeapp.data.retrofit

import com.example.exchangeapp.data.retrofit.Dto.ExchangeRateDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateRetrofit{
    @GET("live")
    suspend fun getData(@Query("access_key") access_key: String): ExchangeRateDto

    companion object{
        const val BASE_URL = "http://api.currencylayer.com/"
        const val ACCESS_KEY = "7e62753b9d8f95bc47c9c9148335db34"
    }
}