package com.gov.sidesa.ui.widget.notification_dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.gov.sidesa.R
import com.gov.sidesa.databinding.BottomSheetNotificationBinding

open class NotificationBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetNotificationBinding? = null
    protected val binding get() = _binding!!

    private val argsTitle by lazy {
        arguments?.getString(ARGS_TITLE).orEmpty()
    }

    private val argsDescription by lazy {
        arguments?.getString(ARGS_DESCRIPTION).orEmpty()
    }

    private val argsButtonLabel by lazy {
        arguments?.getString(ARGS_BUTTON_LABEL).orEmpty()
    }

    var onButtonClicked: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetNotificationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initEvent()
    }

    private fun initEvent() = with(binding) {
        btnClose.setOnClickListener {
            dismissAllowingStateLoss()
        }

        btnOke.setOnClickListener {
            onButtonClicked?.invoke() ?: dismissAllowingStateLoss()
        }
    }

    private fun initView() = with(binding) {
        tvTitle.text = argsTitle
        tvDescription.text = argsDescription
        btnOke.text = argsButtonLabel.ifBlank { getString(R.string.general_action_ok) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARGS_TITLE = "args_title"
        private const val ARGS_DESCRIPTION = "args_description"
        private const val ARGS_BUTTON_LABEL = "args_button_label"

        fun newInstance(
            title: String,
            description: String,
            buttonLabel: String? = null
        ): NotificationBottomSheet {
            val dialog = NotificationBottomSheet()
            dialog.arguments = Bundle().apply {
                putString(ARGS_TITLE, title)
                putString(ARGS_DESCRIPTION, description)
                putString(ARGS_BUTTON_LABEL, buttonLabel)
            }
            return dialog
        }
    }
}