package com.gov.sidesa.ui.registration.keluarga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import com.gov.sidesa.R
import com.gov.sidesa.data.registration.family.FamilyApplicantModel
import com.gov.sidesa.data.registration.ktp.AddressKtpModel
import com.gov.sidesa.databinding.FragmentFamilyApplicantBinding
import com.gov.sidesa.ui.profile.edit.family.models.RelationType
import com.gov.sidesa.ui.registration.RegistrationStackState
import com.gov.sidesa.ui.registration.ktp.RegistrationKTPViewModel
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.extension.formatFE
import com.gov.sidesa.utils.isVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class FamilyApplicantFragment : Fragment() {

    private lateinit var binding: FragmentFamilyApplicantBinding
    private val viewModel: RegistrationKTPViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFamilyApplicantBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onPause() {
        super.onPause()

        val ktpAddress = AddressKtpModel(
            binding.customFamilyAddress.inputKtpAddress.text.toString(),
            binding.customFamilyAddress.inputKtpRt.text.toString(),
            binding.customFamilyAddress.inputKtpRw.text.toString(),
            binding.customFamilyAddress.inputKtpKecamatan.text.toString(),
            binding.customFamilyAddress.inputKtpKelurahan.text.toString(),
        )
        val isSameAddress = binding.customFamilyApplicant.checkBoxAddress.isChecked
        val applicantModel = Gson().toJson(
            FamilyApplicantModel(
                binding.customFamilyApplicant.inputStatus.text.toString(),
                binding.customFamilyApplicant.inputFather.text.toString(),
                binding.customFamilyApplicant.inputNik.text.toString(),
                binding.customFamilyApplicant.inputPlace.text.toString(),
                binding.customFamilyApplicant.inputDob.text.toString(),
                isSameAddress,
                if (isSameAddress) null else ktpAddress
            )
        )
        viewModel.setPref(
            requireContext(),
            RegistrationStackState.FamilyApplicant.toString(),
            applicantModel
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        restoreUserData()
        binding.customFamilyApplicant.inputLayoutFather.hint = "Nama Lengkap"

        setDropDownStatus()
        setupDateOfBirth()

        binding.customFamilyApplicant.checkBoxAddress.setOnCheckedChangeListener { _, isChecked ->
            binding.customFamilyAddress.containerAddress.isVisible(isChecked)
        }
    }

    private fun restoreUserData() = with(binding.customFamilyApplicant) {
        val husband = RelationType.Husband.type
        val wife = RelationType.Wife.type

        PreferenceUtils.getFamily()
            ?.firstOrNull { it.relationType == husband || it.relationType == wife }
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
        binding.customFamilyApplicant.inputDob.setOnClickListener { view ->
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih Tanggal Lahir")
                .build()
            picker.addOnPositiveButtonClickListener {
                val date = Date(it)
                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                binding.customFamilyApplicant.inputDob.setText(format.format(date))
            }
            picker.show(childFragmentManager, "DATE_PICKER")
        }
    }

    private fun setDropDownStatus() {
        val kecamatanAdapter =
            ArrayAdapter(
                requireContext(),
                R.layout.item_dropdown,
                resources.getStringArray(R.array.status_applicant)
            )
        val kecamatanAutoComplete =
            binding.root.findViewById<AutoCompleteTextView>(R.id.input_status)
        kecamatanAutoComplete.setAdapter(kecamatanAdapter)
    }

}