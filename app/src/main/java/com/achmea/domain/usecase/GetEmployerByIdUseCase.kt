package com.achmea.domain.usecase

import com.achmea.domain.model.Employer
import com.achmea.domain.repository.EmployersRepository

class GetEmployerByIdUseCase(private val repository: EmployersRepository) {
    suspend operator fun invoke(id: Long): Result<Employer> {
        val result = repository.getEmployers()

        return if (result.isSuccess) {
            result.getOrNull()?.find { it.id == id }?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Employer with ID $id not found."))
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Unknown error"))
        }
    }
}