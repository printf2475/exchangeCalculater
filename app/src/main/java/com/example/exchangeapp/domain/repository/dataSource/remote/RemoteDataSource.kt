package com.example.exchangeapp.domain.repository.dataSource.remote

import com.example.exchangeapp.data.retrofit.Dto.ExchangeRateDto

interface RemoteDataSource {
    suspend fun getDataSource() : ExchangeRateDto
}