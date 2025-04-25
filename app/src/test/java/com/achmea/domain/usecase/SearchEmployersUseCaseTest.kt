package com.achmea.domain.usecase

import com.achmea.domain.model.Employer
import com.achmea.domain.repository.EmployersRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchEmployersUseCaseTest {

    private val repository: EmployersRepository = mockk()
    private val searchEmployersUseCase = SearchEmployersUseCase(repository)

    @Test
    fun `GIVEN query WHEN invoke is called THEN return search results`() = runTest {
        val query = "Google"
        val employers =
            listOf(Employer(id = 1, name = "Google", discountPercentage = 15, place = "Amsterdam"))
        coEvery { repository.searchEmployers(query) } returns Result.success(employers)

        val result = searchEmployersUseCase(query)

        assertTrue(result.isSuccess)
        assertEquals(employers, result.getOrNull())
    }
}