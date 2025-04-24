package com.achmea.domain.repository

import com.achmea.domain.model.Employer

interface EmployersRepository {
    suspend fun searchEmployers(query: String): Result<List<Employer>>
}