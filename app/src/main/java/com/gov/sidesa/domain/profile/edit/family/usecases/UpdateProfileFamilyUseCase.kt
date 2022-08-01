package com.gov.sidesa.domain.profile.edit.family.usecases

import com.gov.sidesa.domain.profile.ProfileRepository
import com.gov.sidesa.domain.profile.detail.family.models.Account
import com.gov.sidesa.domain.profile.edit.family.models.SaveFamily
import com.gov.sidesa.ui.profile.edit.family.models.EditProfileFamilyUiModel
import com.gov.sidesa.ui.profile.edit.family.models.EditProfileFamilyViewType
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 01/08/22"
 * Project name: SIDESA
 **/


class UpdateProfileFamilyUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(
        account: Account,
        components: List<EditProfileFamilyUiModel>
    ): NetworkResponse<String, GenericErrorResponse> {
        val componentData = components.filter { it.type == EditProfileFamilyViewType.Form }

        val save = componentData.map { it.asData(account = account) }

        return repository.updateProfileFamily(family = save)
    }

    private fun EditProfileFamilyUiModel.asData(account: Account) = SaveFamily(
        accountId = account.id,
        relationType = relationFamily.type,
        name = name,
        ktpNumber = ktpNumber,
        kkNumber = account.kk,
        birthPlace = birthPlace,
        birthDate = birthDate,
        address = if (differentAddress) address else account.address,
        rt = if (differentAddress) rt else account.rt,
        rw = if (differentAddress) rw else account.rw,
        provinceId = if (differentAddress) province?.id else 0L,//account.province,
        cityId = if (differentAddress) city?.id else 0L,//account.city,
        districtId = if (differentAddress) district?.id else 0L,// account.district,
        villageId = if (differentAddress) village?.id else 0L,// account.village
    )
}