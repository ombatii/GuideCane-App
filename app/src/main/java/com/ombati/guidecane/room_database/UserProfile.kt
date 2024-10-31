package com.ombati.guidecane.room_database



import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val smartCaneID: String,
    val location: String,
    val batteryLevel: String,
    val geoFencing: String,
    val caregiverNumber: String,
    val emergencyStatus: String,
    val securityKey: String // Ensure this field is included
)
