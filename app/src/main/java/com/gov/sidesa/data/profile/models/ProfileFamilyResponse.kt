package com.gov.sidesa.data.profile.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.gov.sidesa.data.user.response.User

/**
 * Created by yovi.putra on 30/07/22"
 * Project name: SIDESA
 **/

data class ProfileFamilyResponse(
    @SerializedName("account")
    @Expose
    val account: User,
    @SerializedName("family")
    @Expose
    val family: List<FamilyResponse>
)

data class FamilyResponse(
    @SerializedName("relation_family")
    @Expose
    val relationFamily: String?,
    @SerializedName("name")
    @Expose
    val name: String?,
    @SerializedName("no_ktp")
    @Expose
    val ktpNumber: String?,
    @SerializedName("birth_place")
    @Expose
    val birthPlace: String?,
    @SerializedName("birth_date")
    @Expose
    val birthDate: String?,
    @SerializedName("address")
    @Expose
    val address: String?,
    @SerializedName("different_address")
    @Expose
    val differentAddress: Boolean?
)
