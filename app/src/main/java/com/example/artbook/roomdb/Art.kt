package com.example.artbook.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class Art(
        var artName : String,
        var artistName : String,
        var artYear : Int,
        var artImage : String,
        @PrimaryKey(autoGenerate = true)
        var myId : Int? = null
)