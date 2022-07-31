package com.gov.sidesa.domain.letter.detail

import com.gov.sidesa.data.user.response.User
import com.gov.sidesa.domain.letter.detail.models.DetailApprovalModel
import com.gov.sidesa.domain.letter.input.models.layout.Widget
import com.gov.sidesa.domain.letter.input.models.layout.WidgetType
import com.gov.sidesa.domain.letter.repository.LetterDetailRepository
import com.gov.sidesa.utils.DateUtil
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.PreferenceUtils.USER_PREFERENCE
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse

class GetLetterDetailUseCase(
    private val repository: LetterDetailRepository
) {
    suspend operator fun invoke(
        letterId: String
    ): NetworkResponse<DetailApprovalModel, GenericErrorResponse> {
        val user = PreferenceUtils.getUser()
        return when (val result = repository.getDetail(letterId = letterId, accountId = user?.id.orEmpty())) {
            is NetworkResponse.Success -> {
                val layout = result.body.letterType?.let { assignWidgetFromLocal(it, result.body) }

                NetworkResponse.Success(layout!!)
            }
            else -> result
        }
    }

    private fun assignWidgetFromLocal(
        letterName: String,
        body: DetailApprovalModel
    ): DetailApprovalModel {
        val widgets = body.documentFilled.toMutableList()

        // add header widget
        val header = createHeader(letterName = letterName)
        widgets.add(0, header)

        // assign text view value
        widgets.forEachIndexed { index, widget ->
            if (index > 0)
                if (widget.value == WidgetType.Divider.type) {
                    widgets[index] = widget.copy(type = WidgetType.Divider.type)
                } else {
                    widgets[index] = widget.copy(type = WidgetType.TextView.type)
                }
        }

        return DetailApprovalModel(
//            documentTypeId = body.documentTypeId,
            letterType = body.letterType,
            submissionLetterId = body.submissionLetterId,
            userName = body.userName,
            nik = body.nik,
            address = body.address,
            createdDate = body.createdDate,
            letterNumber = body.letterNumber,
            status = body.status,
            description = body.description,
            documentFilled = widgets,
            historyApproval = body.historyApproval
        )
    }

    private fun createHeader(letterName: String) = Widget(
        type = WidgetType.Header.type,
        title = letterName
    )

    // TODO set data from local storage
    private fun assignTextView(widget: Widget) = when (widget.title) {
        "Nama" -> widget.copy(value = user?.name.orEmpty())
        "Alamat" -> widget.copy(value = user?.addres.orEmpty())
        "Pekerjaan" -> widget.copy(value = user?.job.orEmpty())
        else -> widget
    }

    private val user = PreferenceUtils.get<User>(USER_PREFERENCE)
}