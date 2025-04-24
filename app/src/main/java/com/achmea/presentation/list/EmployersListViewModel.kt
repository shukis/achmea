package com.achmea.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achmea.domain.model.Employer
import com.achmea.domain.usecase.SearchEmployersUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

private const val DEBOUNCE_DELAY = 500L

@OptIn(FlowPreview::class)
class EmployersListViewModel(
    private val searchEmployersUseCase: SearchEmployersUseCase
) : ViewModel() {

    private val _query = MutableStateFlow<String?>(null)
    val query = _query.asStateFlow()

    private val _employers = MutableStateFlow<List<Employer>>(emptyList())
    val employers = _employers.asStateFlow()

    private val _showSearchPrompt = MutableStateFlow(true)
    val showSearchPrompt = _showSearchPrompt.asStateFlow()

    private val _showNoResults = MutableStateFlow(false)
    val showNoResults = _showNoResults.asStateFlow()

    private val _showLoader = MutableStateFlow(false)
    val showLoader = _showLoader.asStateFlow()

    init {
        viewModelScope.launch {
            _query.debounce(DEBOUNCE_DELAY).collect { searchQuery ->
                searchQuery?.let { getEmployers(it) }
            }
        }
    }

    private suspend fun getEmployers(searchQuery: String) {
        _showLoader.emit(searchQuery.isNotBlank())
        searchEmployersUseCase(searchQuery).fold(
            onSuccess = {
                _showSearchPrompt.emit(it.isEmpty() && searchQuery.isBlank())
                _showNoResults.emit(it.isEmpty() && searchQuery.isNotBlank())
                _employers.emit(it)
            },
            onFailure = { println("Error: ${it.message}") }
        )
        _showLoader.emit(false)
    }

    fun onSearch(searchQuery: String) {
        viewModelScope.launch {
            _showLoader.emit(false)
            _query.emit(searchQuery)
        }
    }
}