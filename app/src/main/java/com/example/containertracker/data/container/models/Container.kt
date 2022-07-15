package com.example.containertracker.data.container.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Container(
    @SerializedName("id_tracking_container") var id: String,
    @SerializedName("code_container") var codeContainer: String,
    @SerializedName("unique_id_container") var uniqueId: String,
    @SerializedName("color_container") var color: String?,
    @SerializedName("long_container") var long: String?,
    @SerializedName("wide_container") var wide: String?,
    @SerializedName("tall_container") var tall: String?,
    @SerializedName("origin") var origin: String?,
    @SerializedName("destination") var destination: String?,
    @SerializedName("status") var status: String?,
    @SerializedName("batch_id") var batchId: String?,
    @SerializedName("seal_id") var sealId: String?,
    @SerializedName("voyage_id_in") var voyageIdIn: String?,
    @SerializedName("voyage_id_out") var voyageIdOut: String?,
    @SerializedName("so_number") var salesOrderNumber: String?,
    @SerializedName("rightside_condition") var rightSideCondition: String?,
    @SerializedName("leftside_condition") var leftSideCondition: String?,
    @SerializedName("roofside_condition") var roofSideCondition: String?,
    @SerializedName("floorside_condition") var floorSideCondition: String?,
    @SerializedName("frontdoor_condition") var frontDoorSideCondition: String?,
    @SerializedName("backdoor_condition") var backDoorSideCondition: String?,
    @SerializedName("history") var history: List<ContainerHistory>?,
): Parcelable