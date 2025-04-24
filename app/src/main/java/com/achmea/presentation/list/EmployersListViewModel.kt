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

    init {
        viewModelScope.launch {
            query.debounce(DEBOUNCE_DELAY).collect { searchQuery ->
                searchQuery?.let {
                    searchEmployersUseCase(searchQuery).fold(
                        onSuccess = { _employers.emit(it) },
                        onFailure = { println("Error: ${it.message}") }
                    )
                }
            }
        }
    }

    fun onSearch(searchQuery: String) {
        viewModelScope.launch {
            _query.emit(searchQuery)
        }
    }
}