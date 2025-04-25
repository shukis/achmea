package com.achmea.domain.usecase

import com.achmea.domain.model.Employer
import com.achmea.domain.repository.EmployersRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GetEmployerByIdUseCaseTest {

    private val repository: EmployersRepository = mockk()
    private val getEmployerByIdUseCase = GetEmployerByIdUseCase(repository)

    @Test
    fun `GIVEN existing employer id WHEN invoked THEN return matching employer`() = runTest {
        val id = 2L
        val employers = listOf(
            Employer(id = 1, name = "Google", discountPercentage = 15, place = "Amsterdam"),
            Employer(id = 2, name = "Apple", discountPercentage = 10, place = "Amsterdam")
        )
        coEvery { repository.getEmployers() } returns Result.success(employers)

        val result = getEmployerByIdUseCase(id)

        assertTrue(result.isSuccess)
        assertEquals("Apple", result.getOrNull()?.name)
    }

    @Test
    fun `GIVEN unknown employer id WHEN invoked THEN return failure`() = runTest {
        val id = 123L
        val employers = listOf(
            Employer(id = 1, name = "Google", discountPercentage = 15, place = "Amsterdam"),
            Employer(id = 2, name = "Apple", discountPercentage = 10, place = "Amsterdam")
        )
        coEvery { repository.getEmployers() } returns Result.success(employers)

        val result = getEmployerByIdUseCase(id)

        assertTrue(result.isFailure)

        result.onFailure { exception ->
            assertEquals("Employer with ID $id not found.", exception.message)
        }
    }
}