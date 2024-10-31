package com.ombati.guidecane.room_database


import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [UserProfile::class], version = 1, exportSchema = false) // Set exportSchema as needed
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "user_profile_database"
                )
                    .fallbackToDestructiveMigration() // This will drop and recreate the database
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
