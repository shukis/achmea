package com.achmea.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achmea.domain.model.Employer
import com.achmea.domain.usecase.GetEmployerByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EmployerDetailsViewModel(private val getEmployerByIdUseCase: GetEmployerByIdUseCase) :
    ViewModel() {

    private val _employer = MutableStateFlow<Employer?>(null)
    val employer = _employer.asStateFlow()

    private suspend fun getEmployer(id: Long) {
        getEmployerByIdUseCase(id).fold(
            onSuccess = { _employer.emit(it) },
            onFailure = { println("Error: $it") }
        )
    }

    fun loadEmployer(id: Long) {
        viewModelScope.launch { getEmployer(id) }
    }
}