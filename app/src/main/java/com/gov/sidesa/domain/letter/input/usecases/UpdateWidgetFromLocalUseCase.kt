package com.gov.sidesa.domain.letter.input.usecases

import com.gov.sidesa.domain.letter.input.models.layout.Widget
import com.gov.sidesa.domain.profile.detail.family.models.ProfileFamily
import com.gov.sidesa.utils.extension.formatBE
import com.gov.sidesa.utils.extension.orZero

/**
 * Created by yovi.putra on 04/08/22"
 * Project name: SIDESA
 **/


class UpdateWidgetFromLocalUseCase {

    operator fun invoke(
        widget: Widget,
        profile: ProfileFamily
    ): Widget {
        val account = profile.account

        return when (widget.name) {
            "nama" -> widget.copy(
                value = account.name,
                enable = account.name.isBlank()
            )
            "alamat" -> widget.copy(
                value = account.address,
                enable = account.address.isBlank()
            )
            "pekerjaan" -> widget.copy(
                value = account.job,
                enable = account.job.isBlank()
            )
            "nik" -> widget.copy(
                value = account.nik,
                enable = account.nik.isBlank()
            )
            "nama_kepala_keluarga" -> widget.copy(
                value = account.familyHead.orEmpty(),
                enable = account.familyHead.orEmpty().isBlank()
            )
            "nama_lengkap_pelapor" -> widget.copy(
                value = account.name,
                enable = account.name.isBlank()
            )
            "nik_pelapor" -> widget.copy(
                value = account.nik,
                enable = account.nik.isBlank()
            )
            "tanggal_lahir_pelapor" -> widget.copy(
                value = account.birthDate.formatBE(),
                enable = account.birthDate.formatBE().isBlank()
            )
            "pekerjaan_pelapor" -> widget.copy(
                value = account.job,
                enable = account.job.isBlank()
            )
            "alamat_pelapor" -> widget.copy(
                value = account.fullAddress,
                enable = account.fullAddress.isBlank()
            )
            "kecamatan" -> widget.copy(
                value = account.district.id.orZero().toString(),
                selectedText = account.district.name
            )
            "kelurahan" -> widget.copy(
                value = account.village.id.orZero().toString(),
                selectedText = account.village.name
            )
            "kabupaten_kota" -> widget.copy(
                value = account.city.id.orZero().toString(),
                selectedText = account.city.name
            )
            "propinsi" -> widget.copy(
                value = account.province.id.orZero().toString(),
                selectedText = account.province.name
            )
            else -> widget
        }
    }
}