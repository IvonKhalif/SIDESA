package com.gov.sidesa.ui.letter.input.models.attachment

import com.gov.sidesa.domain.letter.input.models.layout.WidgetType
import com.gov.sidesa.ui.letter.input.models.base.BaseWidgetUiModel
import com.gov.sidesa.ui.letter.input.models.base.InitialState
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactory
import java.io.File

/**
 * Created by yovi.putra on 26/07/22"
 * Project name: Container Tracker
 **/

data class AttachmentWidgetUiModel(
    override val name: String,
    override val value: String?,
    override val initialState: InitialState,
    val files: List<File>,
    val limit: Int,
    val fileType: List<String>
) : BaseWidgetUiModel(
    type = WidgetType.Attachment,
    name = name,
    value = value,
    initialState = initialState
) {

    override fun type(typeFactory: LetterInputViewHolderFactory): Int {
        return typeFactory.type(this)
    }

    override fun areItemsTheSame(newItem: BaseWidgetUiModel): Boolean {
        val attachment = newItem as? AttachmentWidgetUiModel ?: return false

        return super.areItemsTheSame(newItem = attachment)
                && files.hashCode() == attachment.files.hashCode() // ensure update when selected item
    }
}
