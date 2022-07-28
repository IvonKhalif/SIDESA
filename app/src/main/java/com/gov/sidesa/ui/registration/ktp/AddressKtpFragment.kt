package com.gov.sidesa.ui.registration.ktp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gov.sidesa.databinding.FragmentAddressKtpBinding
import com.gov.sidesa.utils.gone

class AddressKtpFragment : Fragment() {

    companion object {
        fun newInstance(): AddressKtpFragment {
            return AddressKtpFragment()
        }
    }

    private lateinit var binding: FragmentAddressKtpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressKtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.customKtpAddress.inputLayoutKtpCity.gone()
        binding.customKtpAddress.inputLayoutKtpProvince.gone()
    }

}