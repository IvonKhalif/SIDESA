package com.gov.sidesa.ui.approval.submissiondetail

import com.gov.sidesa.base.BaseViewModel
import com.gov.sidesa.ui.letter.input.models.drop_down.DropDownWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.edit_text.EditTextWidgetUiModel
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderListener

class DetailApprovalSubmissionViewModel : BaseViewModel(), LetterInputViewHolderListener {
    override fun onEditTextChanged(model: EditTextWidgetUiModel) {
        // when edittext changed on custom view
    }

    override fun onDropDownClick(model: DropDownWidgetUiModel) {
        // when DropDown Clicked on custom view
    }
}