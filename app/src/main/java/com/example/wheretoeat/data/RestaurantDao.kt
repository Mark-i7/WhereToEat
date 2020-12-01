package com.example.wheretoeat.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wheretoeat.models.Restaurant

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRestaurantDao(restaurant:Restaurant)

    @Delete
    suspend fun deleteRestaurant(restaurant:Restaurant)

    @Query("DELETE FROM restaurant_table")
    suspend fun deleteAll()

    @Query("SELECT *FROM restaurant_table ORDER BY name ASC")
    fun readAllData(): LiveData<List<Restaurant>>
}