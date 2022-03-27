package com.example.exchangeapp.domain.model

import com.example.exchangeapp.data.retrofit.Dto.Quotes

data class ExchangeRateData(
    val timestamp: Int?,
    val quotes: Quotes?
    )
