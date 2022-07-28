package com.gov.sidesa.ui.registration.ktp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.gov.sidesa.R
import com.gov.sidesa.databinding.FragmentBiodataKtpBinding
import java.text.SimpleDateFormat
import java.util.*

class BiodataKtpFragment : Fragment() {

    companion object {
        fun newInstance(): BiodataKtpFragment {
            return BiodataKtpFragment()
        }
    }

    private lateinit var binding: FragmentBiodataKtpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBiodataKtpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() = with(binding) {
        customKtpBiodata.inputKtpDob.setOnClickListener { view ->
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih Tanggal Lahir")
                .build()
            picker.addOnPositiveButtonClickListener {
                val date = Date(it)
                val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                customKtpBiodata.inputKtpDob.setText(format.format(date))
            }
            picker.show(childFragmentManager, "DATE_PICKER")
        }
        setDropDownGender()
        setDropDownBloodType()
    }

    private fun setDropDownBloodType() {
        val bloodTypes = resources.getStringArray(R.array.blood_type)
        val bloodTypeAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, bloodTypes)
        val inputBloodTypeAutoComplete =
            binding.root.findViewById<AutoCompleteTextView>(R.id.input_ktp_blood_type)
        inputBloodTypeAutoComplete.setAdapter(bloodTypeAdapter)
    }

    private fun setDropDownGender() {
        val genders = resources.getStringArray(R.array.gender)
        val genderAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, genders)
        val inputGenderAutoComplete =
            binding.root.findViewById<AutoCompleteTextView>(R.id.input_ktp_gender)
        inputGenderAutoComplete.setAdapter(genderAdapter)
    }
}