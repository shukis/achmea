package com.achmea.domain.usecase

import com.achmea.domain.repository.EmployersRepository

class SearchEmployersUseCase(private val repository: EmployersRepository) {
    operator suspend fun invoke(query: String) = repository.searchEmployers(query)
}