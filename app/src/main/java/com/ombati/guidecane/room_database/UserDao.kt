package com.ombati.guidecane.room_database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserProfileDao {
    @Insert
    suspend fun insert(userProfile: UserProfile)

    @Query("SELECT * FROM user_profiles WHERE smartCaneID = :smartCaneID LIMIT 1")
    suspend fun getUserProfile(smartCaneID: String): UserProfile?

    @Query("SELECT * FROM user_profiles ORDER BY id DESC")
    suspend fun getAllProfiles(): List<UserProfile>

    // Change the parameter type to String
    @Query("DELETE FROM user_profiles WHERE smartCaneID = :smartCaneID")
    suspend fun deleteUserProfile(smartCaneID: String)
}
