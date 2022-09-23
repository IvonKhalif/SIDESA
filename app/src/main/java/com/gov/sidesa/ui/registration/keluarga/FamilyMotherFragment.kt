package com.gov.sidesa.ui.registration.keluarga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import com.gov.sidesa.data.registration.family.FamilyMotherModel
import com.gov.sidesa.data.registration.ktp.AddressKtpModel
import com.gov.sidesa.databinding.FragmentFamilyMotherBinding
import com.gov.sidesa.ui.profile.edit.family.models.RelationType
import com.gov.sidesa.ui.registration.RegistrationStackState
import com.gov.sidesa.ui.registration.ktp.RegistrationKTPViewModel
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.extension.formatFE
import com.gov.sidesa.utils.gone
import com.gov.sidesa.utils.isVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class FamilyMotherFragment : Fragment() {

    private lateinit var binding: FragmentFamilyMotherBinding
    private val viewModel: RegistrationKTPViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFamilyMotherBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onPause() {
        super.onPause()

        val ktpAddress = AddressKtpModel(
            binding.customFamilyAddress.inputKtpAddress.text.toString(),
            binding.customFamilyAddress.inputKtpRt.text.toString(),
            binding.customFamilyAddress.inputKtpRw.text.toString(),
//            binding.customFamilyAddress.inputKtpKecamatan.text.toString(),
//            binding.customFamilyAddress.inputKtpKelurahan.text.toString(),
        )
        val isSameAddress = binding.customFamilyMother.checkBoxAddress.isChecked
        val familyMotherToJson = Gson().toJson(
            FamilyMotherModel(
                binding.customFamilyMother.inputFather.text.toString(),
                binding.customFamilyMother.inputNik.text.toString(),
                binding.customFamilyMother.inputPlace.text.toString(),
                binding.customFamilyMother.inputDob.text.toString(),
                isSameAddress,
                if (isSameAddress) null else ktpAddress
            )
        )
        viewModel.setPref(
            requireContext(),
            RegistrationStackState.FamilyMother.toString(),
            familyMotherToJson
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restoreUserData()
        binding.customFamilyMother.inputLayoutStatus.gone()
        binding.customFamilyMother.inputLayoutFather.hint = "Nama Lengkap Ibu"
        setupDateOfBirth()
        binding.customFamilyMother.checkBoxAddress.setOnCheckedChangeListener { _, isChecked ->
            binding.customFamilyAddress.containerAddress.isVisible(isChecked)
        }
    }

    private fun restoreUserData() = with(binding.customFamilyMother) {
        val mother = RelationType.Mother.type

        PreferenceUtils.getFamily()
            ?.firstOrNull { it.relationType == mother }
            ?.let {
                inputStatus.setText(it.relationType)
                inputFather.setText(it.name)
                inputNik.setText(it.ktpNumber)
                inputPlace.setText(it.address)
                inputDob.setText(it.birthDate.formatFE())

                binding.customFamilyAddress.apply {
                    inputKtpAddress.setText(it.address)
                    inputKtpRt.setText(it.rt)
                    inputKtpRw.setText(it.rw)
                    inputKtpProvince.setText(it.province.name)
                    inputKtpCity.setText(it.city.name)
                    inputKtpKecamatan.setText(it.district.name)
                    inputKtpKelurahan.setText(it.village.name)
                }
            }
    }

    private fun setupDateOfBirth() {
        binding.customFamilyMother.inputDob.setOnClickListener { view ->
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih Tanggal Lahir")
                .build()
            picker.addOnPositiveButtonClickListener {
                val date = Date(it)
                val format = SimpleDateFormat("yyyy-MM-DD", Locale.getDefault())
                binding.customFamilyMother.inputDob.setText(format.format(date))
            }
            picker.show(childFragmentManager, "DATE_PICKER")
        }
    }

}