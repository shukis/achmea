package com.achmea.domain.usecase

import com.achmea.domain.repository.EmployersRepository

class SearchEmployersUseCase(private val repository: EmployersRepository) {
    suspend operator fun invoke(query: String) = repository.searchEmployers(query)
}