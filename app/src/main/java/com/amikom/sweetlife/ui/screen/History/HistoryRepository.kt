package com.amikom.sweetlife.ui.screen.History

import kotlinx.coroutines.delay
import javax.inject.Inject

class HistoryRepository @Inject constructor() {
    suspend fun fetchEntries(): List<EntriesItem> {
        return fetchHistory().data?.foodLogs.orEmpty()
            .flatMap { it?.entries.orEmpty().filterNotNull() }
    }


    suspend fun fetchHistory(): HistoryModel {
        delay(2000)
        return HistoryModel(
            status = "success",
            data = Data(
                pagination = Pagination(
                    totalItems = 100,
                    itemsPerPage = 10,
                    totalPages = 10,
                    currentPage = 1
                ),
                foodLogs = listOf(
                    FoodLogsItem(
                        date = "2023-10-01",
                        entries = listOf(
                            EntriesItem(
                                foodName = "Nasi Goreng",
                                editable = false,
                                icon = Icon(
                                    backgroundColor = "#FF0000",
                                    url = "https://icons8.com/icon/qRqBSN5tASTC/taco"
                                ),
                                id = "1",
                                calories = 100,
                                time = "2023-10-01T12:00:00Z"
                            ),
                            EntriesItem(
                                foodName = "Ayam Penyet",
                                editable = false,
                                icon = Icon(
                                    backgroundColor = "#00FF00",
                                    url = "https://www.example.com/icon2.png"
                                ),
                                id = "2",
                                calories = 200,
                                time = "2023-10-01T13:00:00Z"
                            )
                        ),
                        totalCalories = 300
                    ),
                    FoodLogsItem(
                        date = "2023-10-02",
                        entries = listOf(
                            EntriesItem(
                                foodName = "Sate",
                                editable = false,
                                icon = Icon(
                                    backgroundColor = "#0000FF",
                                    url = "https://www.example.com/icon3.png"
                                ),
                                id = "3",
                                calories = 150,
                                time = "2023-10-02T12:00:00Z"
                            )
                        ),
                        totalCalories = 150
                    )
                )
            )
        )
    }
}
