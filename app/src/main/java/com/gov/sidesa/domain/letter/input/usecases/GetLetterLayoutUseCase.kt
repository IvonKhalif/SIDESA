package com.gov.sidesa.domain.letter.input.usecases

import com.gov.sidesa.data.user.response.UserResponse
import com.gov.sidesa.domain.letter.input.models.layout.LetterLayout
import com.gov.sidesa.domain.letter.input.models.layout.Widget
import com.gov.sidesa.domain.letter.input.models.layout.WidgetType
import com.gov.sidesa.domain.letter.repository.LetterRepository
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.extension.format
import com.gov.sidesa.utils.extension.orZero
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

/**
 * Created by yovi.putra on 28/07/22"
 * Project name: SIDESA
 **/


class GetLetterLayoutUseCase(
    private val repository: LetterRepository
) {
    suspend operator fun invoke(
        letterTypeId: String,
        letterName: String
    ): NetworkResponse<LetterLayout, GenericErrorResponse> {
        return when (val result = repository.getLayout(letterTypeId = letterTypeId)) {
            is NetworkResponse.Success -> {
                val layout = assignWidgetFromLocal(letterName, result.body)

                NetworkResponse.Success(layout)
            }
            else -> result
        }
    }

    /**
     * Modify widget from be response
     */
    private fun assignWidgetFromLocal(
        letterName: String,
        layout: LetterLayout
    ): LetterLayout {
        val widgets = layout.widgets.toMutableList()

        // add header widget
        val header = createHeader(letterName = letterName)
        widgets.add(0, header)

        // assign value from local
        widgets.forEachIndexed { index, widget ->
            widgets[index] = assignValue(widget = widget)
        }

        return layout.copy(widgets = widgets)
    }

    private fun createHeader(letterName: String) = Widget(
        type = WidgetType.Header.type,
        title = letterName
    )

    private fun assignValue(widget: Widget) = when (widget.name) {
        "nama" -> widget.copy(value = userResponse?.name.orEmpty())
        "alamat" -> widget.copy(value = userResponse?.address.orEmpty())
        "pekerjaan" -> widget.copy(value = userResponse?.job.orEmpty())
        "nik" -> widget.copy(value = userResponse?.nik.orEmpty())
        "nama_kepala_keluarga" -> widget.copy(value = userResponse?.familyHead.orEmpty())
        "nama_lengkap_pelapor" -> widget.copy(value = userResponse?.name.orEmpty())
        "nik_pelapor" -> widget.copy(value = userResponse?.nik.orEmpty())
        "tanggal_lahir_pelapor" -> widget.copy(value = userResponse?.birthDate?.format().orEmpty())
        "pekerjaan_pelapor" -> widget.copy(value = userResponse?.job.orEmpty())
        "alamat_pelapor" -> widget.copy(value = userResponse?.fullAddress.orEmpty())
        "kecamatan" -> widget.copy(
            value = userResponse?.district?.id.orZero().toString(),
            selectedText = userResponse?.district?.name.orEmpty()
        )
        "kelurahan" -> widget.copy(
            value = userResponse?.village?.id.orZero().toString(),
            selectedText = userResponse?.village?.name.orEmpty()
        )
        "kabupaten_kota" -> widget.copy(
            value = userResponse?.city?.id.orZero().toString(),
            selectedText = userResponse?.city?.name.orEmpty()
        )
        "propinsi" -> widget.copy(
            value = userResponse?.province?.id.orZero().toString(),
            selectedText = userResponse?.province?.name.orEmpty()
        )
        else -> widget
    }

    private val userResponse = PreferenceUtils.get<UserResponse>(PreferenceUtils.USER_PREFERENCE)
}