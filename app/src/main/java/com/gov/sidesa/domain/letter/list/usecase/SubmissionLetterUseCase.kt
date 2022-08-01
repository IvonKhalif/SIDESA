package com.gov.sidesa.domain.letter.list.usecase

import com.gov.sidesa.domain.letter.repository.LetterListRepository
import com.gov.sidesa.domain.letter.repository.LetterRepository

class SubmissionLetterUseCase(
    private val repository: LetterListRepository
) {
    suspend operator fun invoke(
        accountId: Long
    ) = repository.getSubmissionLetters(accountId)
}