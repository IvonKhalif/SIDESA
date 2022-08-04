package com.gov.sidesa.domain.letter.input.usecases

import com.gov.sidesa.data.user.response.UserResponse
import com.gov.sidesa.domain.letter.input.models.layout.Widget
import com.gov.sidesa.utils.extension.format
import com.gov.sidesa.utils.extension.orZero

/**
 * Created by yovi.putra on 04/08/22"
 * Project name: SIDESA
 **/


class UpdateWidgetFromLocalUseCase {

    operator fun invoke(widget: Widget, user: UserResponse) = when (widget.name) {
        "nama" -> widget.copy(
            value = user.name.orEmpty(),
            enable = user.name.orEmpty().isBlank()
        )
        "alamat" -> widget.copy(
            value = user.address.orEmpty(),
            enable = user.address.orEmpty().isBlank()
        )
        "pekerjaan" -> widget.copy(
            value = user.job.orEmpty(),
            enable = user.job.orEmpty().isBlank()
        )
        "nik" -> widget.copy(
            value = user.nik.orEmpty(),
            enable = user.nik.orEmpty().isBlank()
        )
        "nama_kepala_keluarga" -> widget.copy(
            value = user.familyHead.orEmpty(),
            enable = user.familyHead.orEmpty().isBlank()
        )
        "nama_lengkap_pelapor" -> widget.copy(
            value = user.name.orEmpty(),
            enable = user.name.orEmpty().isBlank()
        )
        "nik_pelapor" -> widget.copy(
            value = user.nik.orEmpty(),
            enable = user.nik.orEmpty().isBlank()
        )
        "tanggal_lahir_pelapor" -> widget.copy(
            value = user.birthDate?.format().orEmpty(),
            enable = user.birthDate?.format().orEmpty().isBlank()
        )
        "pekerjaan_pelapor" -> widget.copy(
            value = user.job.orEmpty(),
            enable = user.job.orEmpty().isBlank()
        )
        "alamat_pelapor" -> widget.copy(
            value = user.fullAddress,
            enable = user.fullAddress.isBlank()
        )
        "kecamatan" -> widget.copy(
            value = user.district?.id.orZero().toString(),
            selectedText = user.district?.name.orEmpty()
        )
        "kelurahan" -> widget.copy(
            value = user.village?.id.orZero().toString(),
            selectedText = user.village?.name.orEmpty()
        )
        "kabupaten_kota" -> widget.copy(
            value = user.city?.id.orZero().toString(),
            selectedText = user.city?.name.orEmpty()
        )
        "propinsi" -> widget.copy(
            value = user.province?.id.orZero().toString(),
            selectedText = user.province?.name.orEmpty()
        )
        else -> widget
    }
}