package com.gov.sidesa.data.letterdetail.mapper

import com.gov.sidesa.data.letter.mapper.asDomain
import com.gov.sidesa.data.letterdetail.models.DetailApprovalResponse
import com.gov.sidesa.data.letterdetail.models.HistoryApprovalResponse
import com.gov.sidesa.domain.letter.detail.models.DetailApprovalModel
import com.gov.sidesa.domain.letter.detail.models.HistoryApprovalModel

fun DetailApprovalResponse.asDomain() = DetailApprovalModel(
    documentTypeId = documentTypeId,
    letterType = letterType,
    status = status,
    documentFilled = documentFilled.map { it.asDomain() },
    historyApproval = historyApproval.map { it.asDomain() }
)

fun HistoryApprovalResponse.asDomain() = HistoryApprovalModel(
    typeApproval = typeApproval,
    descriptionType = descriptionType,
    statusApproval = statusApproval,
    createdDate = createdDate
)