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
import com.gov.sidesa.data.registration.ktp.AddressKtpModel
import com.gov.sidesa.databinding.FragmentAddressKtpBinding
import com.gov.sidesa.ui.registration.RegistrationStackState
import com.gov.sidesa.utils.gone
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddressKtpFragment : Fragment() {

    companion object {
        fun newInstance(): AddressKtpFragment {
            return AddressKtpFragment()
        }
    }

    private lateinit var binding: FragmentAddressKtpBinding
    private val viewModel: RegistrationKTPViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressKtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onPause() {
        super.onPause()

        val addressKtpToJson = Gson().toJson(
            AddressKtpModel(
                binding.customKtpAddress.inputKtpAddress.text.toString(),
                binding.customKtpAddress.inputKtpRt.text.toString(),
                binding.customKtpAddress.inputKtpRw.text.toString(),
                binding.customKtpAddress.inputKtpKecamatan.text.toString(),
                binding.customKtpAddress.inputKtpKelurahan.text.toString()
            )
        )
        viewModel.setPref(
            requireContext(),
            RegistrationStackState.KtpAddress.toString(),
            addressKtpToJson
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.customKtpAddress.inputLayoutKtpCity.gone()
        binding.customKtpAddress.inputLayoutKtpProvince.gone()

        setDropDownKecamatan()
        setDropDownKelurahan()
    }

    private fun setDropDownKecamatan() {
        val kecamatanAdapter =
            ArrayAdapter(requireContext(), R.layout.item_dropdown, getDummyKecamatanList())
        val kecamatanAutoComplete =
            binding.root.findViewById<AutoCompleteTextView>(R.id.input_ktp_kecamatan)
        kecamatanAutoComplete.setAdapter(kecamatanAdapter)
    }

    private fun setDropDownKelurahan() {
        val genderAdapter =
            ArrayAdapter(requireContext(), R.layout.item_dropdown, getDummyKelurahanList())
        val inputGenderAutoComplete =
            binding.root.findViewById<AutoCompleteTextView>(R.id.input_ktp_kelurahan)
        inputGenderAutoComplete.setAdapter(genderAdapter)
    }

}