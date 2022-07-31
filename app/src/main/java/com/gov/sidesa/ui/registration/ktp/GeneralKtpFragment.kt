package com.gov.sidesa.ui.registration.ktp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.gov.sidesa.R
import com.gov.sidesa.data.registration.ktp.GeneralKtpModel
import com.gov.sidesa.databinding.FragmentGeneralKtpBinding
import com.gov.sidesa.ui.registration.RegistrationStackState
import org.koin.androidx.viewmodel.ext.android.viewModel

class GeneralKtpFragment : Fragment() {

    companion object {
        fun newInstance(): GeneralKtpFragment {
            return GeneralKtpFragment()
        }
    }

    private lateinit var binding: FragmentGeneralKtpBinding
    private val viewModel: RegistrationKTPViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGeneralKtpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onPause() {
        super.onPause()

        val generalKtpToJson = Gson().toJson(
            GeneralKtpModel(
                binding.customKtpGeneral.inputKtpReligion.text.toString(),
                binding.customKtpGeneral.inputKtpMarriage.text.toString(),
                binding.customKtpGeneral.inputKtpJob.text.toString(),
                binding.customKtpGeneral.inputKtpNationality.text.toString(),
            )
        )
        viewModel.setPref(
            requireContext(),
            RegistrationStackState.KtpGeneral.toString(),
            generalKtpToJson
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDropDownReligion()
        setDropDownMarriageStatus()
        setDropDownJob()
        setDropDownNationality()
    }

    private fun setDropDownReligion() {
        val religionList = resources.getStringArray(R.array.religion)
        val religionAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, religionList)
        val religionAutoComplete =
            binding.root.findViewById<AutoCompleteTextView>(R.id.input_ktp_religion)
        religionAutoComplete.setAdapter(religionAdapter)
    }

    private fun setDropDownMarriageStatus() {
        val marriageStatusList = resources.getStringArray(R.array.marriage_status)
        val marriageStatusAdapter =
            ArrayAdapter(requireContext(), R.layout.item_dropdown, marriageStatusList)
        val marriageStatusAutoComplete =
            binding.root.findViewById<AutoCompleteTextView>(R.id.input_ktp_marriage)
        marriageStatusAutoComplete.setAdapter(marriageStatusAdapter)
    }

    private fun setDropDownJob() {
        val jobList = resources.getStringArray(R.array.job)
        val jobAdapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, jobList)
        val jobAutoComplete = binding.root.findViewById<AutoCompleteTextView>(R.id.input_ktp_job)
        jobAutoComplete.setAdapter(jobAdapter)
    }

    private fun setDropDownNationality() {
        val nationalityList = resources.getStringArray(R.array.nationality)
        val nationalityAdapter =
            ArrayAdapter(requireContext(), R.layout.item_dropdown, nationalityList)
        val nationalityAutoComplete =
            binding.root.findViewById<AutoCompleteTextView>(R.id.input_ktp_nationality)
        nationalityAutoComplete.setAdapter(nationalityAdapter)
    }
}