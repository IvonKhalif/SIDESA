package com.gov.sidesa.ui.letter.input.view_holder_factory

import com.gov.sidesa.ui.letter.input.models.drop_down.DropDownWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.edit_text.EditTextWidgetUiModel

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: SIDESA
 **/

interface LetterInputViewHolderListener {

    fun onEditTextChanged(model: EditTextWidgetUiModel)

    fun onDropDownClick(model: DropDownWidgetUiModel)
}