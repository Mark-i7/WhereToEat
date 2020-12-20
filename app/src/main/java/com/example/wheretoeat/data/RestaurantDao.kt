package com.example.wheretoeat.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.wheretoeat.models.Favorites
import com.example.wheretoeat.models.User
import com.example.wheretoeat.models.UserPicture

@Dao
interface RestaurantDao {
    //Favorites
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavRest(favorites: Favorites)

    @Query("DELETE FROM favorites_table WHERE restId=:rId")
    suspend fun deleteFavRest(rId: Long)

    @Query("DELETE FROM favorites_table")
    suspend fun deleteAllFav()

    @Query("SELECT * FROM favorites_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Favorites>>

    @Query("SELECT restId FROM favorites_table WHERE UserName = :userName")
    fun getUserFavorites(userName: String): LiveData<List<Long>>

    //User
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM  user_table ORDER BY id ASC")
    fun selectAllUsers(): LiveData<List<User>>

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    //Pictures
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserPic(userPicture: UserPicture)

    @Query("SELECT * from userPic ")
    fun selectUserPics(): LiveData<List<UserPicture>>


}