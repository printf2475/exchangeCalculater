package com.example.exchangeapp.domain.useCase

//UseCase들을 한번에 의존성주입 해주기위한 데이터 클래스
data class GetUseCases(
    val getExchange : GetExchangeRate
    )