package com.ombati.guidecane.data.repositories


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


class RetroInstance {

    companion object {
        private const val BASE_URL = "https://api.thingspeak.com/"

        private fun getCustomLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor { message ->
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
                dateFormat.timeZone = TimeZone.getTimeZone("EAT") // Set to EAT
                val timestamp = dateFormat.format(Date())

                // Log with timestamp in EAT
                println("$timestamp $message")
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        fun getRetroInstance(): Retrofit {
            val client = OkHttpClient.Builder()
                .addInterceptor(getCustomLoggingInterceptor())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
