package com.amikom.sweetlife.domain.usecases.dashboard

data class DashboardUseCases(
    val fetchData: FetchData,
    val scanFood: ScanFood,
    val findFood: FindFood,
    val saveFood: SaveFood
)