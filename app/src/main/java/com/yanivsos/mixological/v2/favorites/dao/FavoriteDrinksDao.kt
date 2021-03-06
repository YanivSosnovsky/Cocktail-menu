package com.yanivsos.mixological.v2.favorites.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yanivsos.mixological.database.DrinkPreviewModel
import com.yanivsos.mixological.database.WatchlistItemModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDrinksDao {

    @Query("SELECT * FROM watchlist")
    fun getAll(): Flow<List<WatchlistItemModel>>

    @Query("SELECT * FROM watchlist WHERE watchlist.id = :id")
    fun getById(id: String): Flow<WatchlistItemModel?>

    @Query("SELECT * FROM watchlist INNER JOIN drink_previews ON watchlist.id = drink_previews.id ORDER BY name")
    fun getFavoritePreviews(): Flow<List<DrinkPreviewModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun store(watchlistItemModel: WatchlistItemModel)

    @Query("DELETE FROM watchlist WHERE id = :id")
    suspend fun remove(id: String)
}
