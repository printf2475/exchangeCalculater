package com.example.exchangeapp.domain.useCase

import android.util.Log
import com.example.exchangeapp.domain.model.ExchangeRateData
import com.example.exchangeapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//클린아키텍쳐의 UseCase
class GetExchangeRate(val repository: Repository) {
    //Flow는 코루틴에서 Rx프로그래밍의 형식을 띄게 할수있다.
    suspend operator fun invoke() : Flow<ExchangeRateData> = flow {
        emit(repository.getExchangeRate().toExchangeRateData())
    }
}