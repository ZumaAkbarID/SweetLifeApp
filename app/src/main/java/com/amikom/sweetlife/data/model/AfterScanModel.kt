package com.amikom.sweetlife.data.model

data class AfterScanModel(
    val foodList: List<FoodItem>,
    val message: String,
    val status: Boolean
)

data class FoodItem(
    val name: String,
    val unit: Int,
    val calories: Int,
    val protein: Double,
    val sugar: Double,
    val carbohydrate: Double,
    val fat: Double
)