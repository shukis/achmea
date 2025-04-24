package com.achmea.core

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object EmployersList : Route
    @Serializable
    data class EmployerDetails(val id: Long) : Route
}