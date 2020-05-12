package com.zemingo.drinksmenu.repo.reactive_store

import com.zemingo.drinksmenu.domain.models.AlcoholicFilterModel
import com.zemingo.drinksmenu.domain.models.GlassModel
import com.zemingo.drinksmenu.repo.room.AlcoholicFilterDao
import kotlinx.coroutines.flow.Flow

class AlcoholicFiltersReactiveStore(
    private val alcoholicFilterDao: AlcoholicFilterDao
): ReactiveStore<String, AlcoholicFilterModel, Void> {

    override fun getAll(key: List<String>?): Flow<List<AlcoholicFilterModel>> {
        return alcoholicFilterDao.getAll()
    }

    override fun getByParam(param: Void): Flow<List<AlcoholicFilterModel>> {
        TODO("Not yet implemented")
    }

    override fun storeAll(data: List<AlcoholicFilterModel>) {
        alcoholicFilterDao.storeAll(data)
    }
}