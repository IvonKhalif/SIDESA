package com.gov.sidesa.ui.profile.edit.family.adapter.view_holder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
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

        inputLayoutName.hint = context.getString(data.nameTitle)
        inputLayoutStatus.isVisible = data.inputStatusVisibilityState
        checkBoxAddress.isChecked = !data.differentAddress
        containerAddress.containerAddress.isVisible = data.differentAddress
        inputDob.setText(data.birtDateFormatted)

        with(containerAddress) {
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

        inputName.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            listener.onNameTextChanged(data.copy(name = inputName.text.toString()))
        })

        inputNik.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            listener.onKTPChanged(data.copy(ktpNumber = inputNik.text.toString()))
        })

        inputPlace.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            listener.onBirthPlace(data.copy(birthPlace = inputPlace.text.toString()))
        })

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
            inputAddress.addTextChangedListener(onTextChanged = { _, _, _, _ ->
                listener.onAddressChanged(data.copy(address = inputNik.text.toString()))
            })

            inputRt.addTextChangedListener(onTextChanged = { _, _, _, _ ->
                listener.onRTChanged(data.copy(rt = inputNik.text.toString()))
            })

            inputRw.addTextChangedListener(onTextChanged = { _, _, _, _ ->
                listener.onRWChanged(data.copy(rw = inputNik.text.toString()))
            })

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
        binding.inputStatus.addTextChangedListener {
            listener.onRelationStatusChanged(
                uiModel.copy(
                    relationFamily = RelationType.find(it.toString())
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