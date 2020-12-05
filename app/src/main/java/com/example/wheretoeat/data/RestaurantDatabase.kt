package com.example.wheretoeat.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.wheretoeat.models.Favorites
import com.example.wheretoeat.models.Restaurant


@Database(entities = [Favorites::class], version = 3, exportSchema = false)
abstract class RestaurantDatabase: RoomDatabase() {

    abstract fun RestaurantDao():RestaurantDao

    companion object{
        @Volatile
        private var INSTANCE: RestaurantDatabase?=null

//        val migration_1_2: Migration = object: Migration(1,2)
//        {
//            override fun migrate(database: SupportSQLiteDatabase) {
//
//                database.execSQL("CREATE TABLE IF NOT EXISTS `favorites_tabel` (`id` BIGINT NOT NULL ,`userId` BIGINT NOT NULL,`restId` BIGINT NOT NULL,`name` VARCHAR, PRIMARY KEY(`id`))")
//            }
//        }

        fun getDatabase(context: Context):RestaurantDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RestaurantDatabase::class.java,
                    "favorites_table"
                )
//                        .addMigrations(migration_1_2)
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}