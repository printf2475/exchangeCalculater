package com.example.exchangeapp.domain.repository

import com.example.exchangeapp.data.retrofit.Dto.ExchangeRateDto

interface Repository {
    suspend fun getExchangeRate() : ExchangeRateDto
}