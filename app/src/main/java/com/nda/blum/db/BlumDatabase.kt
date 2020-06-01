package com.nda.blum.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nda.blum.db.dao.UserDao
import com.nda.blum.db.entity.User
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [User::class],version = 1, exportSchema = false)
abstract class BlumDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    @InternalCoroutinesApi
    companion object{
        @Volatile
        private var INSTANCE: BlumDatabase? = null

        fun getInstance(context: Context): BlumDatabase {
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BlumDatabase::class.java,
                        "blum_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}