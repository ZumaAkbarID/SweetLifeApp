package com.amikom.sweetlife.data.model

data class FoodRequest(
    val scan: List<ScanItem>,
    val additionall: List<AdditionalItem>
)

data class ScanItem(
    val name: String, // Huruf depan kapital
    val unit: Int
)

data class AdditionalItem(
    val name: String, // Huruf depan kapital
    val weight: Int, // Gram
    val info: AdditionalInfo? = null
)

data class AdditionalInfo(
    val calories: Double,
    val protein: Double,
    val sugar: Double,
    val carbohydrates: Double,
    val fat: Double,
)
