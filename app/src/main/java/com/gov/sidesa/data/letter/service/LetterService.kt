package com.gov.sidesa.data.letter.service

import com.gov.sidesa.data.letter.models.LetterTemplateResponse
import com.gov.sidesa.data.letter.models.ResourceResponse
import com.gov.sidesa.data.letter.models.WidgetResponse
import com.gov.sidesa.domain.letter.input.models.save.SaveLetter
import com.gov.sidesa.utils.constants.ContentTypeConstant
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitResponse
import com.gov.sidesa.utils.response.RetrofitStatusResponse
import com.haroldadmin.cnradapter.NetworkResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.http.*

interface LetterService {

    @GET
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun getResources(
        @Url url: String
    ): NetworkResponse<RetrofitResponse<List<ResourceResponse>>, GenericErrorResponse>

    @GET("master/surat/template")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun getLetterLayout(
        @Query("id_type_surat") letterTypeId: String
    ): NetworkResponse<RetrofitResponse<List<WidgetResponse>>, GenericErrorResponse>

    @GET("master/surat")
    @Headers(ContentTypeConstant.CONTENT_TYPE_JSON)
    suspend fun getTemplates()
            : NetworkResponse<RetrofitResponse<List<LetterTemplateResponse>>, GenericErrorResponse>

    @Multipart
    @POST("pengajuan-surat/save")
    suspend fun save(
        @PartMap letter: Map<String, String>,
        @Part files: List<MultipartBody.Part>,
        @Part filesCount: MultipartBody.Part
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse>

    suspend fun save(
        letter: SaveLetter
    ): NetworkResponse<RetrofitStatusResponse, GenericErrorResponse> {
        val formData = createFormData(letter = letter)
        val multipart = createAttachment(letter = letter)
        val attachmentCount = MultipartBody.Part.createFormData(
            name = "item_attachment",
            value = multipart.size.toString()
        )

        return save(letter = formData, files = multipart, filesCount = attachmentCount)
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