package com.example.exchangeapp.data.repository

import com.example.exchangeapp.domain.repository.Repository
import com.example.exchangeapp.domain.repository.dataSource.remote.RemoteDataSource

class RepositoryImpl(val remoteDataSource: RemoteDataSource) : Repository {
    override suspend fun getExchangeRate() = remoteDataSource.getDataSource()
}