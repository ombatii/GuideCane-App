package com.ombati.guidecane.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ombati.guidecane.data.repositories.Feed
import com.ombati.guidecane.data.repositories.UserRepository
import com.ombati.guidecane.room_database.UserProfile
import com.ombati.guidecane.room_database.UserProfileDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
    private val userProfileDao: UserProfileDao
) : ViewModel() {

    private val _userProfiles = MutableStateFlow<List<UserProfile>>(emptyList())
    val userProfiles: StateFlow<List<UserProfile>> = _userProfiles.asStateFlow()

    private val _filteredFeeds = MutableStateFlow<List<Feed>>(emptyList())
    val filteredFeeds: StateFlow<List<Feed>> = _filteredFeeds.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchUserProfiles() // Fetch profiles on initialization
    }

    // Fetch user profiles from the database
    fun fetchUserProfiles() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val profiles = userProfileDao.getAllProfiles()
                _userProfiles.value = profiles ?: emptyList()
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch profiles: ${e.message}"
                Log.e("Profile Error", e.message.toString())
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Check if a user profile already exists
    private suspend fun userProfileExists(smartCaneID: String): Boolean {
        return userProfileDao.getUserProfile(smartCaneID) != null
    }

    fun fetchFilteredFeeds(securityKey: String, smartCaneID: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                if (userProfileExists(smartCaneID)) {
                    _errorMessage.value = "User with this Smart Cane ID already exists."
                } else {
                    val response = repository.getChannelFeeds(results = 1000)
                    if (response.isSuccessful) {
                        val feeds = response.body()?.feeds ?: emptyList()

                        Log.d("Feeds Data", feeds.toString())

                        val matchedFeeds = feeds.filter { feed ->
                            feed.field2 != null && feed.field3 != null &&
                                    feed.field2.trim().equals(securityKey.trim(), ignoreCase = true) &&
                                    feed.field3.trim().equals(smartCaneID.trim(), ignoreCase = true)
                        }

                        val latestValues = getLatestNonNullValues(matchedFeeds)

                        _filteredFeeds.value = latestValues

                        _errorMessage.value = if (_filteredFeeds.value.isEmpty()) {
                            "No user details found for the provided Smart Cane ID and Security Key."
                        } else {
                            ""
                        }
                    } else {
                        _errorMessage.value = "Error: ${response.message()}"
                        Log.e("API Error", "Error: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                _errorMessage.value = "Exception: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getLatestNonNullValues(feeds: List<Feed>): List<Feed> {
        val latestValues = mutableListOf<Feed>()
        val fieldsToCheck = listOf("field4", "field5", "field6", "field7", "field8")

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())

        for (field in fieldsToCheck) {
            val latestFeed = feeds.filter { feed ->
                when (field) {
                    "field4" -> feed.field4 != null
                    "field5" -> feed.field5 != null
                    "field6" -> feed.field6 != null
                    "field7" -> feed.field7 != null
                    "field8" -> feed.field8 != null
                    else -> false
                }
            }.maxByOrNull { feed ->
                dateFormat.parse(feed.created_at) ?: Date(0)
            }

            if (latestFeed != null) {
                latestValues.add(latestFeed)
            }
        }

        return latestValues
    }

    // Save user profile to the database
    fun addUserProfile(feed: Feed) {
        viewModelScope.launch {
            val userProfile = UserProfile(
                smartCaneID = feed.field3 ?: "",
                location = feed.field1 ?: "N/A",
                batteryLevel = feed.field7 ?: "N/A",
                caregiverNumber = feed.field6 ?: "N/A",
                emergencyStatus = feed.field5 ?: "N/A",
                geoFencing = feed.field4 ?: "N/A",
                securityKey = "N/A" // Provide a default value or pass it if available
            )
            userProfileDao.insert(userProfile) // Ensure UserProfileDao has this method
            fetchUserProfiles() // Refresh the list after adding
        }
    }

    fun deleteUserProfile(smartCaneID: String) {
        viewModelScope.launch {
            try {
                userProfileDao.deleteUserProfile(smartCaneID)
                _userProfiles.value = _userProfiles.value.filterNot { it.smartCaneID == smartCaneID } // Update state
                _errorMessage.value = "" // Clear error message after deletion
            } catch (e: Exception) {
                _errorMessage.value = "Failed to delete profile: ${e.message}"
                Log.e("Delete Error", e.message.toString())
            }
        }
    }

}
