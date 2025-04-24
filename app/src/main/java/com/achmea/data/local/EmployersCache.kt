package com.achmea.data.local

import com.achmea.data.model.EmployerResponse

class EmployersCache {

    private var employers: List<EmployerResponse>? = null

    fun invalidate() {
        employers = null
    }

    fun setEmployers(launches: List<EmployerResponse>) {
        this.employers = launches
    }

    fun getEmployers(): List<EmployerResponse>? {
        return employers
    }
}