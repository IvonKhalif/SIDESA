package com.gov.sidesa.ui.registration

sealed class RegistrationStackState {
    object KtpBiodata : RegistrationStackState()
    object KtpAddress : RegistrationStackState()
    object KtpGeneral : RegistrationStackState()
    object KtpUpload : RegistrationStackState()
    object KtpReviewKtp : RegistrationStackState()

    object KkBiodata : RegistrationStackState()
    object KkAddress : RegistrationStackState()
    object KkUpload : RegistrationStackState()
    object KkReview : RegistrationStackState()

    object FamilyFather : RegistrationStackState()
    object FamilyMother : RegistrationStackState()
    object FamilyApplicant : RegistrationStackState()
    object FamilyChild : RegistrationStackState()
    object FamilyReview : RegistrationStackState()
}