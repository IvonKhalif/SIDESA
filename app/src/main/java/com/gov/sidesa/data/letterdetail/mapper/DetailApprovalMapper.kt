package com.gov.sidesa.data.letterdetail.mapper

import com.gov.sidesa.data.letter.mapper.asDomain
import com.gov.sidesa.data.letterdetail.models.DetailApprovalResponse
import com.gov.sidesa.data.letterdetail.models.HistoryApprovalResponse
import com.gov.sidesa.domain.letter.detail.models.DetailApprovalModel
import com.gov.sidesa.domain.letter.detail.models.HistoryApprovalModel
import com.gov.sidesa.utils.DateUtil

fun DetailApprovalResponse.asDomain() = DetailApprovalModel(
//    documentTypeId = documentTypeId,
    letterType = letterType,
    submissionLetterId = submissionLetterId,
    userName = userName,
    nik = nik,
    address = address.orEmpty(),
    createdDate = createdDate?.let { DateUtil.convertToDayAndDate(it) },
    letterNumber = letterNumber,
    status = status,
    description = description.orEmpty(),
    documentFilled = documentFilled.map { it.asDomain() },
    historyApproval = historyApproval.map { it.asDomain() }
)

fun HistoryApprovalResponse.asDomain() = HistoryApprovalModel(
    typeApproval = typeApproval,
    descriptionType = descriptionType,
    statusApproval = statusApproval,
    createdDate = createdDate?.let { DateUtil.convertToDayAndDate(it) }
)