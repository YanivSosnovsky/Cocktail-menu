package com.yanivsos.mixological.v2.search.repo

import com.yanivsos.mixological.database.AlcoholicFilterModel
import com.yanivsos.mixological.database.CategoryModel
import com.yanivsos.mixological.database.GlassModel
import com.yanivsos.mixological.database.IngredientModel
import com.yanivsos.mixological.network.DrinkService
import com.yanivsos.mixological.v2.search.dao.AlcoholicFilterDao
import com.yanivsos.mixological.v2.search.dao.CategoryDao
import com.yanivsos.mixological.v2.search.dao.GlassDao
import com.yanivsos.mixological.v2.ingredients.dao.IngredientDao
import com.yanivsos.mixological.v2.search.mapper.toAlcoholicsModel
import com.yanivsos.mixological.v2.search.mapper.toCategoriesModel
import com.yanivsos.mixological.v2.search.mapper.toGlassesModel
import com.yanivsos.mixological.v2.search.mapper.toIngredientsModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepository(
    private val ingredientDao: IngredientDao,
    private val alcoholicFilterDao: AlcoholicFilterDao,
    private val categoryDao: CategoryDao,
    private val glassDao: GlassDao,
    private val drinkService: DrinkService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getIngredients() = ingredientDao.getAll()

    fun getAlcoholicFilters() = alcoholicFilterDao.getAll()

    fun getCategories() = categoryDao.getAll()

    fun getGlasses() = glassDao.getAll()

    suspend fun findIngredientsBySimilarName(name: String) =
        withContext(ioDispatcher) {
            ingredientDao.findIngredientsBySimilarName(name)
        }

    suspend fun fetchIngredientsList() =
        withContext(ioDispatcher) {
            drinkService
                .getIngredientList()
                .toIngredientsModel()
        }

    suspend fun storeIngredients(ingredients: List<IngredientModel>) =
        withContext(ioDispatcher) {
            ingredientDao.store(ingredients)
        }

    suspend fun fetchCategoriesList() =
        withContext(ioDispatcher) {
            drinkService
                .getCategoryList()
                .toCategoriesModel()
        }

    suspend fun storeCategories(ingredients: List<CategoryModel>) =
        withContext(ioDispatcher) {
            categoryDao.store(ingredients)
        }

    suspend fun fetchGlassesList() =
        withContext(ioDispatcher) {
            drinkService
                .getGlassList()
                .toGlassesModel()
        }

    suspend fun storeGlasses(glasses: List<GlassModel>) =
        withContext(ioDispatcher) {
            glassDao.store(glasses)
        }

    suspend fun fetchAlcoholicsList() =
        withContext(ioDispatcher) {
            drinkService
                .getAlcoholicFilterList()
                .toAlcoholicsModel()
        }

    suspend fun storeAlcoholics(alcoholics: List<AlcoholicFilterModel>) =
        withContext(ioDispatcher) {
            alcoholicFilterDao.store(alcoholics)
        }
}
