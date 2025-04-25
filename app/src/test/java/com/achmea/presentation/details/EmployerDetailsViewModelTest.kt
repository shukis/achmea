package com.achmea.presentation.details

import com.achmea.domain.model.Employer
import com.achmea.domain.usecase.GetEmployerByIdUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EmployerDetailsViewModelTest {

    private val getEmployerByIdUseCase: GetEmployerByIdUseCase = mockk()
    private lateinit var viewModel: EmployerDetailsViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = EmployerDetailsViewModel(getEmployerByIdUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN valid employer id WHEN loadEmployer is called THEN employer is emitted`() = runTest {
        val id = 1L
        val employer = Employer(id = 1, name = "Google", discountPercentage = 15, place = "Amsterdam")
        coEvery { getEmployerByIdUseCase(id) } returns Result.success(employer)

        viewModel.loadEmployer(id)
        advanceUntilIdle()

        assertEquals(employer, viewModel.employer.value)
    }

    @Test
    fun `GIVEN invalid ID WHEN loadEmployer is called THEN do not update employer state`() = runTest {
        val id = 123L
        coEvery { getEmployerByIdUseCase(id) } returns Result.failure(Exception("Not found"))

        viewModel.loadEmployer(id)
        advanceUntilIdle()

        assertNull(viewModel.employer.value)
    }
}