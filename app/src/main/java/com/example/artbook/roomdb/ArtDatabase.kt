package com.example.artbook.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.artbook.roomdb.Art
import com.example.artbook.roomdb.ArtDao

@Database(entities = [Art::class],version = 1)
abstract class ArtDatabase : RoomDatabase() {
    abstract fun artDao() : ArtDao
}