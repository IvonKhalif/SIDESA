package com.gov.sidesa.domain.letter.detail

import com.gov.sidesa.data.letterdetail.request.DoApprovalRequest
import com.gov.sidesa.domain.letter.repository.LetterDetailRepository

class DoApprovalUseCase(
    private val repository: LetterDetailRepository
) {
    suspend operator fun invoke(
        request: DoApprovalRequest
    ) = repository.doApproval(request)
}