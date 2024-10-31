package com.ombati.guidecane.data.repositories

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ThingsSpeakAPI {

    // API to read channel feeds (with a limit on results)
    @GET("channels/2700771/feeds.json")
    suspend fun getChannelFeeds(
        @Query("results") results: Int
    ): Response<ThingsSpeakResponse>  // Directly return a Response

    // API to write a new feed to the channel
    @POST("update")
    suspend fun updateChannelFeed(
        @Query("api_key") apiKey: String,
        @Query("field1") location: String?,
        @Query("field2") securityKey: String?,
        @Query("field3") smartCaneID: String?,
        @Query("field4") emergencyStatus: String?,
        @Query("field5") userName: String?,
        @Query("field6") geoFencing: String?,
        @Query("field7") batteryLevel: String?,
        @Query("field8") caregiverNumber: String?
    ): Response<String>  // Directly return a Response
}
