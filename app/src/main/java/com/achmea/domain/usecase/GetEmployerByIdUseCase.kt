package com.achmea.domain.usecase

import com.achmea.domain.model.Employer
import com.achmea.domain.repository.EmployersRepository

class GetEmployerByIdUseCase(private val repository: EmployersRepository) {
    suspend operator fun invoke(id: Long): Result<Employer> {
        return repository.getEmployers().map { employers ->
            employers.first { employer -> employer.id == id }
        }
    }
}