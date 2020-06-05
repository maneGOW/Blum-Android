package com.nda.blum.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "first_launch")
data class FirstLaunch (
    @PrimaryKey(autoGenerate = true)
    var fistLaunchId: Long = 0L,
    var firstLaunch: Boolean?
)