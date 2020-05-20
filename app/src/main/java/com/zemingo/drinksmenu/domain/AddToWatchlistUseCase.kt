package com.zemingo.drinksmenu.domain

import com.zemingo.drinksmenu.domain.models.WatchlistItemModel
import com.zemingo.drinksmenu.repo.repositories.WatchlistRepository
import timber.log.Timber

class AddToWatchlistUseCase(
    private val fetchAndStoreDrinkUseCase: FetchAndStoreDrinkUseCase,
    private val repository: WatchlistRepository
) {

    suspend fun addToWatchlist(watchlistItemModel: WatchlistItemModel) {
        Timber.d("addToWatchlist: called with $watchlistItemModel")
        fetchAndStore(watchlistItemModel)
        repository.store(watchlistItemModel)
    }

    private suspend fun fetchAndStore(watchlistItemModel: WatchlistItemModel) {
        try {
            fetchAndStoreDrinkUseCase.fetchAndStore(watchlistItemModel.id)
        } catch (e: Exception) {
            Timber.e(e, "Unable to fetch and store drink[${watchlistItemModel.id}]")
        }
    }
}