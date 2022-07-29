package com.gov.sidesa.ui.registration.keluarga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.gov.sidesa.R
import com.gov.sidesa.databinding.FragmentFamilyApplicantBinding
import com.gov.sidesa.utils.isVisible
import java.text.SimpleDateFormat
import java.util.*

class FamilyApplicantFragment : Fragment() {

    private lateinit var binding: FragmentFamilyApplicantBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFamilyApplicantBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDropDownStatus()
        setupDateOfBirth()

        binding.customFamilyApplicant.checkBoxAddress.setOnCheckedChangeListener { _, isChecked ->
            binding.customFamilyAddress.containerAddress.isVisible(isChecked)
        }
    }

    private fun setupDateOfBirth() {
        binding.customFamilyApplicant.inputDob.setOnClickListener { view ->
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih Tanggal Lahir")
                .build()
            picker.addOnPositiveButtonClickListener {
                val date = Date(it)
                val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
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