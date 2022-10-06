package com.gov.sidesa.data.letter.repository

import com.gov.sidesa.data.letter.mapper.asDomain
import com.gov.sidesa.data.letter.service.LetterService
import com.gov.sidesa.domain.letter.input.models.layout.LetterLayout
import com.gov.sidesa.domain.letter.input.models.resource.Resource
import com.gov.sidesa.domain.letter.input.models.save.SaveLetter
import com.gov.sidesa.domain.letter.repository.LetterRepository
import com.gov.sidesa.domain.letter.template.models.Template
import com.gov.sidesa.utils.extension.asDomain
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitStatusResponse
import com.haroldadmin.cnradapter.NetworkResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

/**
 * Created by yovi.putra on 27/07/22"
 * Project name: SIDESA
 **/


class LetterRepositoryImpl(
    private val service: LetterService
) : LetterRepository {

    override suspend fun getResources(
        url: String,
        params: Map<String, String>
    ): NetworkResponse<List<Resource>, GenericErrorResponse> {
        val query = params.map {
            "${it.key}=${it.value}"
        }.joinToString("&")

        val request = if (query.isBlank()) url else "$url?$query"

        return service.getResources(url = request).asDomain {
            data.orEmpty().asDomain()
        }
    }

    override suspend fun getLayout(
        letterTypeId: String
    ): NetworkResponse<LetterLayout, GenericErrorResponse> {
        return service.getLetterLayout(letterTypeId = letterTypeId).asDomain {
            data.orEmpty().asDomain()
        }
    }

    override suspend fun getTemplate(): NetworkResponse<List<Template>, GenericErrorResponse> {
        return service.getTemplates().asDomain {
            data.orEmpty().asDomain()
        }
    }

    override suspend fun save(letter: SaveLetter): NetworkResponse<String, GenericErrorResponse> {
        return doLetterSave(letter = letter).asDomain {
            desc
        }
    }

    private suspend fun doLetterSave(
        letter: SaveLetter
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse> {
        val formData = createFormData(letter = letter)
        val multipart = createAttachment(letter = letter)
        val attachmentCount = MultipartBody.Part.createFormData(
            name = "item_attachment",
            value = multipart.size.toString()
        )

        return service.save(letter = formData, files = multipart, filesCount = attachmentCount)
    }

    private fun createFormData(letter: SaveLetter): MutableMap<String, String> {
        val formBody = mutableMapOf<String, String>().apply {
            put("id_account", letter.accountId.toString())
            put("id_type_surat", letter.letterTypeId.toString())
            letter.contents.forEachIndexed { index, content ->
                put("field[$index]", content.field)
                put("value[$index]", content.value)
            }
        }
        return formBody
    }

    private fun createAttachment(letter: SaveLetter) =
        letter.attachments.mapIndexed { index, file ->
            val count = index + 1
            MultipartBody.Part.createFormData(
                name = "attachment_$count",
                filename = file.name,
                body = file.asRequestBody(contentType = "image/jpg".toMediaType())
            )
        }
}
