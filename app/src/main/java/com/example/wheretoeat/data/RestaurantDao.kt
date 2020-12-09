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


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("select * from user_table where email = :email and password = :password")
    fun getUser(email: String,password : String)

    @Delete
    suspend fun deleteUser(user: User)
}