package com.example.wheretoeat.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_table")
data class Favorites(
        val restId: Long,
        val userName: String,
        val name: String,
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0
)