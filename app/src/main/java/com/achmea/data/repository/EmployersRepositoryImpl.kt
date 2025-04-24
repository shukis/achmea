package com.achmea.data.repository

import com.achmea.data.local.EmployersCache
import com.achmea.data.remote.EmployersApi
import com.achmea.domain.model.Employer
import com.achmea.domain.repository.EmployersRepository

class EmployerRepositoryImpl(
    private val api: EmployersApi,
    private val cache: EmployersCache
) : EmployersRepository {

    override suspend fun searchEmployers(query: String): Result<List<Employer>> {
        return try {
            val result = api.searchEmployers(query).also {
                cache.setEmployers(it)
            }.map { it.toDomainModel() }
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getEmployers(): Result<List<Employer>> {
        return cache.getEmployers()?.let { employers ->
            Result.success(employers.map { it.toDomainModel() })
        } ?: Result.failure(Exception("Employers not found"))
    }
}