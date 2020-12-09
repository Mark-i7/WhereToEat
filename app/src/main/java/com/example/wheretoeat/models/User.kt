package com.example.wheretoeat.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val userName:String,
    val password:String,
    val email:String
    )
