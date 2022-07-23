package com.gov.sidesa.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.text.InputFilter
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.*
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import com.gov.sidesa.R
import com.gov.sidesa.databinding.InputTextViewBinding
import com.gov.sidesa.utils.Asterisk
import com.jakewharton.rxbinding4.widget.afterTextChangeEvents

class InputTextViewWidget @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    var binding: InputTextViewBinding
    var isPasswordVisible = true


    open fun value(): EditText = binding.inputValue

    open fun inputLayout(): TextInputLayout = binding.inputLayout

    fun message(): CharSequence? = binding.inputLayout.helperText

    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = InputTextViewBinding.inflate(layoutInflater, this, false)
        addView(binding.root)

        val typeArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.InputTextViewWidget,
            0,
            0
        )

        var hint = typeArray.getText(R.styleable.InputTextViewWidget_hint)
        var label = typeArray.getText(R.styleable.InputTextViewWidget_label)
        var isMandatoryLabel =
            typeArray.getBoolean(R.styleable.InputTextViewWidget_showMandatoryLabel, false)
        val inputTypeFlag = typeArray.getInt(R.styleable.InputTextViewWidget_inputType, 0)
        val isShowHidePassword = typeArray.getBoolean(R.styleable.InputTextViewWidget_isShowHidePassword, true)
        val maxLength = typeArray.getInt(R.styleable.InputTextViewWidget_maxLength, -1)

        if (hint.isNullOrEmpty()) {
            hint = "Fill your hint here"
        } else if (label.isNullOrEmpty()) {
            label = "Label"
        }

        typeArray.recycle()

        setAttribute(
            label.toString(),
            isMandatoryLabel,
            hint.toString(),
            inputTypeFlag,
            isShowHidePassword,
            maxLength
        )
    }

    private fun setAttribute(
        label: String,
        isMandatoryLabel: Boolean,
        hint: String,
        inputTypeFlag: Int,
        isShowHidePassword: Boolean,
        maxLength: Int
    ) {
        val inputFilter = mutableListOf<InputFilter>()
        if (maxLength > -1) {
            inputFilter.add(InputFilter.LengthFilter(maxLength))
        }

        value().filters = inputFilter.toTypedArray()
        binding.inputLayout.hint = hint
        value().compoundDrawablePadding = 24
        setInputType(inputTypeFlag, isShowHidePassword)

    }

    private fun setInputType(inputTypeFlag: Int, isShowHidePassword: Boolean) {
        when (inputTypeFlag) {
            0 -> value().inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
            1 -> value().inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            2 -> value().inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_NORMAL
            3 -> value().inputType =
                InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            4 -> setInputPassword(isShowHidePassword)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setInputPassword(isShowHidePassword: Boolean) {
        value().inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        value().transformationMethod = Asterisk()

        if (!isShowHidePassword) {
            value().setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                0,
                0
            )
            value().setTextAppearance(context, R.style.LargePassword)
            return
        }

        value().setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_visibility_off,
            0
        )
        value().setOnTouchListener { _, motionEvent ->
            val DRAWABLE_END = 2

            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (motionEvent.rawX >=
                    (value().right - value().compoundDrawables[DRAWABLE_END].bounds.width())
                ) {
                    if (isPasswordVisible) {
                        setVisibleOrInvisiblePassword(false, null, R.drawable.ic_visibility_on)
                    } else {
                        setVisibleOrInvisiblePassword(
                            true,
                            PasswordTransformationMethod.getInstance(),
                            R.drawable.ic_visibility_off
                        )
                    }
                    return@setOnTouchListener true
                }
            }

            return@setOnTouchListener false
        }
        value().setTextAppearance(context, R.style.LargePassword)
    }

    private fun setVisibleOrInvisiblePassword(
        isPasswordVisible: Boolean,
        transformationMethod: PasswordTransformationMethod?,
        @DrawableRes iconVisibility: Int
    ) {
        this.isPasswordVisible = isPasswordVisible
        value().transformationMethod = transformationMethod
        value().text?.length?.let { length ->
            value().setSelection(length)
        }
        value().customSelectionActionModeCallback = object : ActionMode.Callback {
            override fun onActionItemClicked(
                actionMode: ActionMode?,
                menuItem: MenuItem?
            ): Boolean {
                return false
            }

            override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onDestroyActionMode(actionMode: ActionMode?) {

            }
        }

        value().setCompoundDrawablesWithIntrinsicBounds(0, 0, iconVisibility, 0)
    }
}