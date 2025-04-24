package com.achmea.data.model

import com.achmea.domain.model.Employer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmployerResponse(
    @SerialName("EmployerID") val id: Long,
    @SerialName("Name") val name: String?,
    @SerialName("Place") val place: String?,
    @SerialName("DiscountPercentage") val discountPercentage: Int?
) {
    fun toDomainModel() = Employer(
        id = id,
        discountPercentage = discountPercentage,
        name = name,
        place = place
    )
}