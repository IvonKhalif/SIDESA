package com.gov.sidesa.ui.profile.edit.family.adapter.view_holder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.gov.sidesa.R
import com.gov.sidesa.databinding.ItemEditProfileFamilyBinding
import com.gov.sidesa.ui.profile.edit.family.adapter.EditProfileFamilyListener
import com.gov.sidesa.ui.profile.edit.family.models.EditProfileFamilyUiModel
import com.gov.sidesa.ui.profile.edit.family.models.RelationType

/**
 * Created by yovi.putra on 31/07/22"
 * Project name: SIDESA
 **/


class EditProfileFamilyViewHolder(
    private val binding: ItemEditProfileFamilyBinding,
    private val listener: EditProfileFamilyListener
) : RecyclerView.ViewHolder(binding.root) {

    private val context = binding.root.context

    fun binding(data: EditProfileFamilyUiModel) = with(binding) {
        initView(data = data)
        initEvent(data = data)
    }

    private fun initView(data: EditProfileFamilyUiModel) = with(binding) {
        textHeader.isVisible = data.titleVisibilityState
        textHeader.text = data.titleText

        inputLayoutStatus.isVisible = data.inputStatusVisibilityState

        inputLayoutName.hint = context.getString(data.nameTitle)
        inputName.setText(data.name)
        inputNik.setText(data.ktpNumber)
        inputPlace.setText(data.birthPlace)
        inputDob.setText(data.birtDateFormatted)

        checkBoxAddress.isChecked = !data.differentAddress
        containerAddress.containerAddress.isVisible = data.differentAddress

        with(containerAddress) {
            inputAddress.setText(data.address)
            inputRt.setText(data.rt)
            inputRw.setText(data.rw)
            inputProvince.setText(data.province?.name.orEmpty())
            inputCity.setText(data.city?.name.orEmpty())
            inputKecamatan.setText(data.district?.name.orEmpty())
            inputKelurahan.setText(data.village?.name.orEmpty())
        }
    }

    private fun initEvent(data: EditProfileFamilyUiModel) = with(binding) {
        if (data.inputStatusVisibilityState) {
            setRelationStatus(uiModel = data)
        }

        inputName.distinctTextChange(data.name) {
            listener.onNameTextChanged(data.copy(name = inputName.text.toString()))
        }

        inputNik.distinctTextChange(data.ktpNumber) {
            listener.onKTPChanged(data.copy(ktpNumber = inputNik.text.toString()))
        }

        inputPlace.distinctTextChange(data.birthPlace) {
            listener.onBirthPlace(data.copy(birthPlace = inputPlace.text.toString()))
        }

        inputLayoutDob.setEndIconOnClickListener {
            listener.onBirthDateClicked(uiModel = data)
        }
        inputDob.setOnClickListener {
            listener.onBirthDateClicked(uiModel = data)
        }

        checkBoxAddress.setOnClickListener {
            val state = checkBoxAddress.isChecked
            containerAddress.containerAddress.isVisible = !state
            listener.onSameAddressClicked(uiModel = data.copy(differentAddress = !state))
        }

        with(containerAddress) {
            inputAddress.distinctTextChange(data.address) {
                listener.onAddressChanged(data.copy(address = inputAddress.text.toString()))
            }

            inputRt.distinctTextChange(data.rt) {
                listener.onRTChanged(data.copy(rt = inputRt.text.toString()))
            }

            inputRw.distinctTextChange(data.rw) {
                listener.onRWChanged(data.copy(rw = inputRw.text.toString()))
            }

            inputLayoutProvince.setEndIconOnClickListener {
                listener.onProvinceClicked(uiModel = data)
            }
            inputProvince.setOnClickListener {
                listener.onProvinceClicked(uiModel = data)
            }

            inputLayoutCity.setEndIconOnClickListener {
                listener.onCityClicked(uiModel = data)
            }
            inputCity.setOnClickListener {
                listener.onCityClicked(uiModel = data)
            }

            inputLayoutKecamatan.setEndIconOnClickListener {
                listener.onDistrictClicked(uiModel = data)
            }
            inputKecamatan.setOnClickListener {
                listener.onDistrictClicked(uiModel = data)
            }

            inputLayoutKelurahan.setEndIconOnClickListener {
                listener.onVillageClicked(uiModel = data)
            }
            inputKelurahan.setOnClickListener {
                listener.onVillageClicked(uiModel = data)
            }
        }
    }

    private fun setRelationStatus(uiModel: EditProfileFamilyUiModel) {
        val relationStatus = ArrayAdapter(
            context,
            R.layout.item_dropdown,
            context.resources.getStringArray(R.array.status_applicant)
        )
        binding.inputStatus.setAdapter(relationStatus)
        binding.inputStatus.distinctTextChange(uiModel.relationFamily.type) {
            listener.onRelationStatusChanged(
                uiModel.copy(
                    relationFamily = RelationType.find(it)
                )
            )
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


private fun TextView.distinctTextChange(prev: String, onTextChanged: (String) -> Unit = {}) {
    addTextChangedListener(onTextChanged = { _, _, _, _ ->
        if (prev != text.toString()) {
            onTextChanged.invoke(this.text.toString())
        }
    })
}