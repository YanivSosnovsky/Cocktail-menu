package com.yanivsos.mixological.repo.repositories

import com.yanivsos.mixological.domain.models.DrinkPreviewModel
import com.yanivsos.mixological.domain.models.RecentlyViewedModel
import com.yanivsos.mixological.repo.reactiveStore.DrinkPreviewParam
import com.yanivsos.mixological.repo.reactiveStore.ReactiveStore
import kotlinx.coroutines.flow.Flow

class RecentlyViewedRepository(
    private val recentlyViewedReactiveStore: ReactiveStore<String, RecentlyViewedModel, Void>,
    private val drinkReactiveStore: ReactiveStore<String, DrinkPreviewModel, DrinkPreviewParam>
) {

    fun getRecentlyViewed(): Flow<List<DrinkPreviewModel>> {
        return drinkReactiveStore
            .getByParam(DrinkPreviewParam.SearchHistory)
    }

    fun storeAll(recentlyViewed: List<RecentlyViewedModel>) {
        recentlyViewedReactiveStore.storeAll(recentlyViewed)
    }


}