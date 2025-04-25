package com.achmea.presentation.list

import com.achmea.domain.model.Employer
import com.achmea.domain.usecase.SearchEmployersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EmployersListViewModelTest {

    private val searchEmployersUseCase: SearchEmployersUseCase = mockk()
    private lateinit var viewModel: EmployersListViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = EmployersListViewModel(searchEmployersUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN search query WHEN onSearch is called THEN employers are loaded`() = runTest {
        val query = "Google"
        val employers = listOf(Employer(id = 1, name = "Google", discountPercentage = 15, place = "Amsterdam"))
        coEvery { searchEmployersUseCase(query) } returns Result.success(employers)
        
        viewModel.onSearch(query)
        advanceTimeBy(DEBOUNCE_DELAY)
        advanceUntilIdle()
        
        assertEquals(employers, viewModel.employers.value)
        assertFalse(viewModel.showNoResults.value)
        assertFalse(viewModel.showSearchPrompt.value)
    }

    @Test
    fun `GIVEN empty result WHEN onSearch is called THEN show no results is true`() = runTest {
        val query = "Google"
        coEvery { searchEmployersUseCase(query) } returns Result.success(emptyList())
        
        viewModel.onSearch(query)
        advanceTimeBy(DEBOUNCE_DELAY)
        advanceUntilIdle()
        
        assertTrue(viewModel.showNoResults.value)
        assertEquals(emptyList<Employer>(), viewModel.employers.value)
        assertFalse(viewModel.showSearchPrompt.value)
    }

    @Test
    fun `GIVEN blank query WHEN onSearch is called THEN showSearchPrompt is true`() = runTest {
        val query = ""
        coEvery { searchEmployersUseCase(query) } returns Result.success(emptyList())
        
        viewModel.onSearch(query)
        advanceTimeBy(DEBOUNCE_DELAY)
        advanceUntilIdle()
        
        assertTrue(viewModel.showSearchPrompt.value)
        assertEquals(emptyList<Employer>(), viewModel.employers.value)
        assertFalse(viewModel.showNoResults.value)
    }
}