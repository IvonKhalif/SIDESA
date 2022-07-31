package com.gov.sidesa.data.letterlist.mapper

import com.gov.sidesa.data.letter.mapper.asDomain
import com.gov.sidesa.data.letter.models.LetterTemplateResponse
import com.gov.sidesa.data.letter.models.WidgetResponse
import com.gov.sidesa.data.letterlist.response.LetterApprovalResponse
import com.gov.sidesa.data.letterlist.response.LetterSubmissionResponse
import com.gov.sidesa.data.letterlist.response.ProfileLetterApprovalResponse
import com.gov.sidesa.domain.letter.input.models.layout.LetterLayout
import com.gov.sidesa.domain.letter.input.models.layout.Widget
import com.gov.sidesa.domain.letter.list.models.LetterApprovalModel
import com.gov.sidesa.domain.letter.list.models.LetterListApprovalModel
import com.gov.sidesa.domain.letter.list.models.LetterSubmissionModel
import com.gov.sidesa.domain.letter.list.models.ProfileLetterApprovalModel
import com.gov.sidesa.utils.response.RetrofitLetterApprovalResponse

fun LetterSubmissionResponse.asDomain() = LetterSubmissionModel(
    letterId = letterId,
    letterType = letterType,
    letterStatus = letterStatus,
    letterNumber = letterNumber
)

fun ProfileLetterApprovalResponse.asDomain() = ProfileLetterApprovalModel(
    accountId = accountId,
    nik = nik,
    villageId = villageId,
    rt = rt.orEmpty(),
    rw = rw.orEmpty(),
    isRt = isRt,
    isRw = isRw
)

fun LetterApprovalResponse.asDomain() = LetterListApprovalModel(
    letterId = letterId,
    letterType = letterType,
    submitDate = submitDate.orEmpty(),
    letterNumber = letterNumber.orEmpty(),
    letterDetail = letterDetail.orEmpty(),
)

fun RetrofitLetterApprovalResponse<List<LetterApprovalResponse>>.asDomain() = profile?.let {
    data?.let { data ->
        LetterApprovalModel(
            profile = it.asDomain(),
            letters = data.map {
                it.asDomain()
            }
        )
    }
}