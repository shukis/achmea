package com.achmea.data.remote

import com.achmea.data.model.EmployerResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://cba.kooijmans.nl/CBAEmployerservice.svc/rest/employers"

class EmployersApi(private val client: HttpClient) {
    suspend fun searchEmployers(query: String, maxRows: Int = 100): List<EmployerResponse> {
        val response =
            client.get(BASE_URL) {
                parameter("filter", query)
                parameter("maxRows", maxRows)
            }
        return response.body()
    }
}