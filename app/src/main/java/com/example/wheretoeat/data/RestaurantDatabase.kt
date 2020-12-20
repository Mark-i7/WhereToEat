package com.example.wheretoeat.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wheretoeat.models.Favorites
import com.example.wheretoeat.models.User
import com.example.wheretoeat.models.UserPicture


@Database(entities = [Favorites::class, User::class, UserPicture::class], version = 5, exportSchema = false)
abstract class RestaurantDatabase : RoomDatabase() {

    abstract fun RestaurantDao(): RestaurantDao

    companion object {
        @Volatile
        private var INSTANCE: RestaurantDatabase? = null

        fun getDatabase(context: Context): RestaurantDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        RestaurantDatabase::class.java,
                        "favorites_table"
                )
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}