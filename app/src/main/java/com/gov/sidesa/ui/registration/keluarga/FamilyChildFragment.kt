package com.gov.sidesa.ui.registration.keluarga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import com.gov.sidesa.data.registration.family.FamilyChildModel
import com.gov.sidesa.data.registration.ktp.AddressKtpModel
import com.gov.sidesa.databinding.FragmentFamilyChildBinding
import com.gov.sidesa.ui.registration.RegistrationStackState
import com.gov.sidesa.ui.registration.ktp.RegistrationKTPViewModel
import com.gov.sidesa.utils.gone
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class FamilyChildFragment : Fragment() {

    private lateinit var binding: FragmentFamilyChildBinding
    private val viewModel: RegistrationKTPViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFamilyChildBinding.inflate(layoutInflater, container, false)
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
        val isSameAddress = binding.customFamilyChild.checkBoxAddress.isChecked
        val familyChildToJson = Gson().toJson(
            FamilyChildModel(
                binding.customFamilyChild.inputFather.text.toString(),
                binding.customFamilyChild.inputNik.text.toString(),
                binding.customFamilyChild.inputPlace.text.toString(),
                binding.customFamilyChild.inputDob.text.toString(),
                isSameAddress,
                if (isSameAddress) null else ktpAddress
            )
        )
        viewModel.setPref(
            requireContext(),
            RegistrationStackState.FamilyChild.toString(),
            familyChildToJson
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.customFamilyChild.inputLayoutFather.hint = "Nama Anak"
        binding.customFamilyChild.inputLayoutStatus.gone()
        setupDateOfBirth()
        binding.buttonAddChildForm.setOnClickListener {
            Toast.makeText(requireContext(), "Sedang tahap Pengembangan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupDateOfBirth() {
        binding.customFamilyChild.inputDob.setOnClickListener { view ->
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih Tanggal Lahir")
                .build()
            picker.addOnPositiveButtonClickListener {
                val date = Date(it)
                val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                binding.customFamilyChild.inputDob.setText(format.format(date))
            }
            picker.show(childFragmentManager, "DATE_PICKER")
        }
    }

}