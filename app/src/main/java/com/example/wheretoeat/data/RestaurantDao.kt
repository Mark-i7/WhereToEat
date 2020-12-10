package com.example.wheretoeat.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.wheretoeat.models.Favorites
import com.example.wheretoeat.models.Restaurant
import com.example.wheretoeat.models.User

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

    //User
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM  user_table ORDER BY id ASC")
    fun selectAllUsers(): LiveData<List<User>>

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()
}