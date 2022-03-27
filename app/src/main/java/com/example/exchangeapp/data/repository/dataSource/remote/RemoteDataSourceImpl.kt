package com.example.exchangeapp.data.repository.dataSource.remote

import android.util.Log
import com.example.exchangeapp.data.retrofit.Dto.ExchangeRateDto
import com.example.exchangeapp.data.retrofit.ExchangeRateRetrofit
import com.example.exchangeapp.domain.repository.dataSource.remote.RemoteDataSource

class RemoteDataSourceImpl(val api : ExchangeRateRetrofit) : RemoteDataSource {
    override suspend fun getDataSource() : ExchangeRateDto = api.getData(ExchangeRateRetrofit.ACCESS_KEY)

}