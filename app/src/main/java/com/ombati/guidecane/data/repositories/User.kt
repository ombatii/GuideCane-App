package com.ombati.guidecane.data.repositories

data class Channel(
    val id: Int,
    val name: String,
    val description: String,
    val latitude: String,
    val longitude: String,
    val field1: String,
    val field2: String,
    val field3: String,
    val field4: String,
    val field5: String,
    val field6: String,
    val field7: String,
    val field8: String,
    val created_at: String,
    val updated_at: String,
    val last_entry_id: Int
)

data class Feed(
    val created_at: String,
    val entry_id: Int,
    val field1: String?,
    val field2: String?,
    val field3: String?,
    val field4: String?,
    val field5: String?,
    val field6: String?,
    val field7: String?,
    val field8: String?
)

data class ThingsSpeakResponse(
    val channel: Channel,
    val feeds: List<Feed>
)
