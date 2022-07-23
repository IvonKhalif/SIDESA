package com.gov.sidesa.ui.profile.edit

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.gov.sidesa.R
import com.gov.sidesa.databinding.ActivityEditProfileBinding
import java.text.SimpleDateFormat
import java.util.*

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val bindingBiodataKtp by lazy { binding.customKtpBiodata }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDatePicker()
        setDropDownGender()
        setDropDownBloodType()
    }

    private fun setDatePicker() {
        bindingBiodataKtp.inputKtpDob.setOnClickListener { view ->
            val picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Pilih Tanggal Lahir")
                .build()
            picker.addOnPositiveButtonClickListener {
                val date = Date(it)
                val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                bindingBiodataKtp.inputKtpDob.setText(format.format(date))
            }
            picker.show(supportFragmentManager, "DATE_PICKER")
        }
    }

    private fun setDropDownBloodType() {
        val bloodTypes = resources.getStringArray(R.array.blood_type)
        val bloodTypeAdapter = ArrayAdapter(this, R.layout.item_dropdown, bloodTypes)
        val inputBloodTypeAutoComplete =
            findViewById<AutoCompleteTextView>(R.id.input_ktp_blood_type)
        inputBloodTypeAutoComplete.setAdapter(bloodTypeAdapter)
    }

    private fun setDropDownGender() {
        val genders = resources.getStringArray(R.array.gender)
        val genderAdapter = ArrayAdapter(this, R.layout.item_dropdown, genders)
        val inputGenderAutoComplete = findViewById<AutoCompleteTextView>(R.id.input_ktp_gender)
        inputGenderAutoComplete.setAdapter(genderAdapter)
    }
}