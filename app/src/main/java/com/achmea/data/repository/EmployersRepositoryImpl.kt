package com.achmea.data.repository

import com.achmea.data.remote.EmployersApi
import com.achmea.domain.model.Employer
import com.achmea.domain.repository.EmployersRepository

class EmployerRepositoryImpl(
    private val api: EmployersApi
) : EmployersRepository {

    override suspend fun searchEmployers(query: String): Result<List<Employer>> {
        return try {
            val result = api.searchEmployers(query).map { it.toDomainModel() }
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}