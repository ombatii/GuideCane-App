package com.ombati.guidecane.data.repositories

import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val thingsSpeakAPI: ThingsSpeakAPI
) {
    suspend fun getChannelFeeds(results: Int): Response<ThingsSpeakResponse> {
        return thingsSpeakAPI.getChannelFeeds(results)
    }

    suspend fun updateUserDetails(
        apiKey: String,
        location: String?,
        securityKey: String?,
        smartCaneID: String?,
        emergencyStatus: String?,
        userName: String?,
        geoFencing: String?,
        batteryLevel: String?,
        caregiverNumber: String?
    ): Response<String> {
        return thingsSpeakAPI.updateChannelFeed(
            apiKey, location, securityKey, smartCaneID, emergencyStatus,
            userName, geoFencing, batteryLevel, caregiverNumber
        )
    }
}

