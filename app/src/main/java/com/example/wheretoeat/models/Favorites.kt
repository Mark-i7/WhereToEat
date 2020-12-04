package com.example.wheretoeat.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_tabel")
data class Favorites(
        @PrimaryKey(autoGenerate = false)
        val id:Long,
        val userId:Long
) {
}