package com.gov.sidesa.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import com.gov.sidesa.data.user.response.UserResponse
import com.gov.sidesa.domain.profile.detail.family.models.ProfileFamily

object PreferenceUtils {
    private const val EDITOR_NAME = "ContainerPreferences"
    const val USER_PREFERENCE = "USER_PREFERENCE"
    const val USER_RESPONSE_PREFERENCE = "USER_RESPONSE_PREFERENCE"
    const val LOCATION_LIST_PREFERENCE = "LOCATION_LIST_PREFERENCE"

    lateinit var preferences: SharedPreferences

    fun with(application: com.gov.sidesa.App) {
        preferences = application.getSharedPreferences(
            EDITOR_NAME, Context.MODE_PRIVATE
        )
    }

    fun <T> put(`object`: T, key: String) {
        //Convert object to JSON String.
        val jsonString = GsonBuilder().create().toJson(`object`)
        //Save that String in SharedPreferences
        preferences.edit().putString(key, jsonString).apply()
    }

    inline fun <reified T> get(key: String): T? {
        //We read JSON String which was saved.
        val value = preferences.getString(key, null)
        //JSON String was found which means object can be read.
        //We convert this JSON String to model object. Parameter "c" (of
        //type Class < T >" is used to cast.
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

    fun getProfile() = get<ProfileFamily>(USER_PREFERENCE)

    fun putProfile(user: ProfileFamily) {
        put(user, USER_PREFERENCE)
    }

    fun getAccount() = getProfile()?.account

    fun getAccountUserResponse() = get<UserResponse>(USER_RESPONSE_PREFERENCE)

    fun getFamily() = getProfile()?.family
}