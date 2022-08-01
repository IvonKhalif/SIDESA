package com.gov.sidesa.domain.letter.list.usecase

import com.gov.sidesa.domain.letter.repository.LetterListRepository

class ApprovalLettersUseCase(
    private val repository: LetterListRepository
) {
    suspend operator fun invoke(
        accountId: Long
    ) = repository.getApprovalLetters(accountId)
}