package com.gov.sidesa.ui.profile.edit.family.models

import com.gov.sidesa.R
import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.utils.extension.formatFE
import java.math.BigInteger
import java.nio.ByteBuffer
import java.util.*

/**
 * Created by yovi.putra on 31/07/22"
 * Project name: SIDESA
 **/

data class EditProfileFamilyUiModel(
    val id: Long = generateUniqueId(),
    val type: EditProfileFamilyViewType = EditProfileFamilyViewType.Form,
    val relationFamily: RelationType = RelationType.Father,
    val name: String = "",
    val ktpNumber: String = "",
    val birthPlace: String = "",
    val birthDate: Date? = null,
    val differentAddress: Boolean = false,
    val address: String = "",
    val rt: String = "",
    val rw: String = "",
    val province: Region? = null,
    val city: Region? = null,
    val district: Region? = null,
    val village: Region? = null,
) {

    fun areItemsTheSame(
        newItem: EditProfileFamilyUiModel
    ): Boolean = id == newItem.id
            && type == newItem.type
            && province?.id == newItem.province?.id
            && city?.id == newItem.city?.id
            && district?.id == newItem.district?.id
            && village?.id == newItem.village?.id
            && differentAddress == newItem.differentAddress
            && birthDate == newItem.birthDate

    fun areContentsTheSame(
        newItem: EditProfileFamilyUiModel
    ): Boolean = areItemsTheSame(newItem = newItem)

    val titleVisibilityState
        get() = relationFamily is RelationType.Child

    val titleText: String
        get() {
            val child = (relationFamily as? RelationType.Child)
            return "Anak ${child?.count}"
        }

    val nameTitle
        get() = when (relationFamily) {
            is RelationType.Father -> {
                R.string.fullname_father
            }
            is RelationType.Mother -> {
                R.string.fullname_mother
            }
            else -> {
                R.string.fullname
            }
        }

    val inputStatusVisibilityState
        get() = relationFamily == RelationType.Husband || relationFamily == RelationType.Wife

    val birtDateFormatted: String
        get() = birthDate?.formatFE().orEmpty()
}

private fun generateUniqueId(): Long {
    var result: Long
    do {
        val uid = UUID.randomUUID()
        val buffer: ByteBuffer = ByteBuffer.wrap(ByteArray(16))
        buffer.putLong(uid.leastSignificantBits)
        buffer.putLong(uid.mostSignificantBits)
        val bi = BigInteger(buffer.array())
        result = bi.toLong()
    } while (result < 0)

    return result
}