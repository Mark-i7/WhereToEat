package com.example.wheretoeat.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wheretoeat.models.Favorites
import com.example.wheretoeat.models.Restaurant

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRestaurantDao(favorites: Favorites)

    @Delete
    suspend fun deleteRestaurant(favorites: Favorites)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM favorites_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Favorites>>

}