package com.achmea.domain.usecase

import com.achmea.domain.repository.EmployersRepository

class GetEmployerByIdUseCase(private val repository: EmployersRepository) {
    suspend operator fun invoke(id: Long) {
        repository.getEmployers().map { employers ->
            employers.firstOrNull { employer -> employer.id == id }
        }
    }
}