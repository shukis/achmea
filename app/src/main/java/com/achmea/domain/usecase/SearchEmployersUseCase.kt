package com.achmea.domain.usecase

import com.achmea.domain.model.Employer
import com.achmea.domain.repository.EmployersRepository
import kotlinx.coroutines.delay

private const val NETWORK_DELAY = 500L

class SearchEmployersUseCase(private val repository: EmployersRepository) {
    suspend operator fun invoke(query: String): Result<List<Employer>> {
        if (query.isNotBlank()) {
            delay(NETWORK_DELAY) //emulate network delay
        }
        return repository.searchEmployers(query)
    }
}