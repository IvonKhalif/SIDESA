package com.gov.sidesa.ui.registration.keluarga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.gov.sidesa.databinding.FragmentFamilyMotherBinding
import com.gov.sidesa.utils.gone
import com.gov.sidesa.utils.isVisible
import java.text.SimpleDateFormat
import java.util.*

class FamilyMotherFragment : Fragment() {

    private lateinit var binding: FragmentFamilyMotherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFamilyMotherBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.customFamilyMother.inputLayoutStatus.gone()
        setupDateOfBirth()
        binding.customFamilyMother.checkBoxAddress.setOnCheckedChangeListener { _, isChecked ->
            binding.customFamilyAddress.containerAddress.isVisible(isChecked)
        }
    }

    private fun setupDateOfBirth() {
        binding.customFamilyMother.inputDob.setOnClickListener { view ->
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih Tanggal Lahir")
                .build()
            picker.addOnPositiveButtonClickListener {
                val date = Date(it)
                val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                binding.customFamilyMother.inputDob.setText(format.format(date))
            }
            picker.show(childFragmentManager, "DATE_PICKER")
        }
    }

}