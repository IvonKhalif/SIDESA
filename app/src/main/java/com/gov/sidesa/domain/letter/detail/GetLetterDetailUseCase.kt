package com.gov.sidesa.domain.letter.detail

import com.gov.sidesa.data.letterdetail.models.DetailApprovalModel
import com.gov.sidesa.domain.letter.input.models.layout.LetterLayout
import com.gov.sidesa.domain.letter.input.models.layout.Widget
import com.gov.sidesa.domain.letter.input.models.layout.WidgetType
import com.gov.sidesa.domain.letter.repository.LetterDetailRepository
import com.gov.sidesa.utils.response.GenericErrorResponse
import com.gov.sidesa.utils.response.RetrofitResponse
import com.haroldadmin.cnradapter.NetworkResponse

class GetLetterDetailUseCase(
    private val repository: LetterDetailRepository
) {
    suspend operator fun invoke(
        letterTypeId: String,
        letterName: String
    ): NetworkResponse<RetrofitResponse<DetailApprovalModel>, GenericErrorResponse> {
        return when (val result = repository.getDetail(letterTypeId = letterTypeId, "3")) {
            is NetworkResponse.Success -> {
                val layout = assignWidgetFromLocal(letterName, result.body)

                NetworkResponse.Success(layout)
            }
            else -> {
                val layout = assignWidgetFromLocal(
                    letterName, RetrofitResponse(
                        data = DetailApprovalModel(
                            0, "", "",
                            listOf(), listOf()
                        ), ""
                    )
                )
                NetworkResponse.Success(layout)
            }
        }
    }

    private fun assignWidgetFromLocal(
        letterName: String,
        body: RetrofitResponse<DetailApprovalModel>
    ): RetrofitResponse<DetailApprovalModel> {
        val widgets = body.data?.documentFilled?.toMutableList()

        // add header widget
        val header = createHeader(letterName = letterName)
        widgets?.add(0, header)
        widgets?.add(1, Widget(type = WidgetType.TextView.type,title = "nama", value = "Ivon Khalif"))
        widgets?.add(2, Widget(type = WidgetType.TextView.type,title = "alamat", value = "Jalanin aja, RT 001 RW 001, Bojong Utara, Bojong, Kabupaten Tangerang, Banten."))
        widgets?.add(3, Widget(type = WidgetType.TextView.type,title = "pekerjaan", value = "Karyawan Swasta"))
        widgets?.add(4, Widget(type = WidgetType.Divider.type))

        // assign text view value
//        widgets?.forEachIndexed { index, widget ->
//            if (widget.type == WidgetType.TextView.type) {
//                widgets[index] = assignTextView(widget = widget)
//            }
//        }

        return body.copy(
            data = DetailApprovalModel(
                documentTypeId = body.data?.documentTypeId ?: 0,
                letterType = body.data?.letterType.orEmpty(),
                status = body.data?.status.orEmpty(),
                documentFilled = widgets?.toList().orEmpty(),
                historyApproval = body.data?.historyApproval.orEmpty()
            )
        )
    }

    private fun createHeader(letterName: String) = Widget(
        type = WidgetType.Header.type,
        title = letterName
    )

    // TODO set data from local storage
    private fun assignTextView(widget: Widget) = when (widget.name) {
        "nama" -> widget.copy(value = "Ivon Khalif")
        "alamat" -> widget.copy(value = "Jalanin aja, RT 001 RW 001, Bojong Utara, Bojong, Kabupaten Tangerang, Banten.")
        "pekerjaan" -> widget.copy(value = "Swasta")
        else -> widget
    }
}