package com.gov.sidesa.ui.profile.edit.family.adapter.view_holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.databinding.ItemEditProfileFamilyBinding
import com.gov.sidesa.ui.profile.edit.family.adapter.EditProfileFamilyListener
import com.gov.sidesa.ui.profile.edit.family.models.EditProfileFamilyUiModel

/**
 * Created by yovi.putra on 31/07/22"
 * Project name: SIDESA
 **/


class EditProfileFamilyViewHolder(
    private val binding: ItemEditProfileFamilyBinding,
    private val listener: EditProfileFamilyListener
) : RecyclerView.ViewHolder(binding.root) {

    fun binding(data: EditProfileFamilyUiModel) = with(binding) {
        initView(data = data)
        initEvent(data = data)
    }

    private fun initView(data: EditProfileFamilyUiModel) = with(binding) {
        inputLayoutStatus.isVisible = data.inputStatusVisibilityState
        checkBoxAddress.isChecked = !data.differentAddress
        containerAddress.containerAddress.isVisible = data.differentAddress

        with(containerAddress) {
            inputProvince.setText(data.province?.name.orEmpty())
            inputCity.setText(data.city?.name.orEmpty())
            inputKecamatan.setText(data.district?.name.orEmpty())
            inputKelurahan.setText(data.village?.name.orEmpty())
        }
    }

    private fun initEvent(data: EditProfileFamilyUiModel) = with(binding) {
        inputStatus.setOnClickListener {
            listener.onRelationStatusClicked(uiModel = data)
        }

        inputName.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            listener.onNameTextChanged(data.copy(name = inputName.text.toString()))
        })

        inputNik.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            listener.onKTPChanged(data.copy(name = inputNik.text.toString()))
        })

        inputPlace.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            listener.onBirthPlace(data.copy(name = inputNik.text.toString()))
        })

        inputDob.setOnClickListener {
            listener.onBirthDateClicked(uiModel = data)
        }

        checkBoxAddress.setOnClickListener {
            val state = checkBoxAddress.isChecked
            containerAddress.containerAddress.isVisible = !state
            listener.onSameAddressClicked(uiModel = data.copy(differentAddress = state))
        }

        with(containerAddress) {
            inputAddress.addTextChangedListener(onTextChanged = { _, _, _, _ ->
                listener.onAddressChanged(data.copy(name = inputNik.text.toString()))
            })

            inputRt.addTextChangedListener(onTextChanged = { _, _, _, _ ->
                listener.onRTChanged(data.copy(name = inputNik.text.toString()))
            })

            inputRw.addTextChangedListener(onTextChanged = { _, _, _, _ ->
                listener.onRWChanged(data.copy(name = inputNik.text.toString()))
            })

            inputProvince.setOnClickListener {
                listener.onProvinceClicked(uiModel = data)
            }

            inputCity.setOnClickListener {
                listener.onCityClicked(uiModel = data)
            }

            inputKecamatan.setOnClickListener {
                listener.onDistrictClicked(uiModel = data)
            }

            inputKelurahan.setOnClickListener {
                listener.onVillageClicked(uiModel = data)
            }
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            listener: EditProfileFamilyListener
        ): EditProfileFamilyViewHolder {
            val inflate = LayoutInflater.from(parent.context)
            val binding = ItemEditProfileFamilyBinding.inflate(inflate, parent, false)
            return EditProfileFamilyViewHolder(binding = binding, listener = listener)
        }
    }
}