package com.gov.sidesa.ui.registration.keluarga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.gov.sidesa.databinding.FragmentFamilyChildBinding
import com.gov.sidesa.utils.gone
import java.text.SimpleDateFormat
import java.util.*

class FamilyChildFragment : Fragment() {

    private lateinit var binding: FragmentFamilyChildBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFamilyChildBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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