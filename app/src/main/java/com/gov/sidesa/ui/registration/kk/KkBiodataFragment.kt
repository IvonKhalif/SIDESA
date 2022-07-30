package com.gov.sidesa.ui.registration.kk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.gson.Gson
import com.gov.sidesa.data.registration.kk.KkBiodataModel
import com.gov.sidesa.databinding.FragmentKkBiodataBinding
import com.gov.sidesa.ui.registration.RegistrationStackState
import com.gov.sidesa.ui.registration.ktp.RegistrationKTPViewModel

class KkBiodataFragment : Fragment() {

    companion object {
        fun newInstance(): KkBiodataFragment {
            return KkBiodataFragment()
        }
    }

    private lateinit var binding: FragmentKkBiodataBinding
    private val viewModel by activityViewModels<RegistrationKTPViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentKkBiodataBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onPause() {
        super.onPause()

        val biodataKkToJson = Gson().toJson(
            KkBiodataModel(
                binding.customKkBiodata.inputKkNumber.text.toString(),
                binding.customKkBiodata.inputKkKepalaKeluarga.text.toString(),
            )
        )
        viewModel.setPref(
            requireContext(),
            RegistrationStackState.KkBiodata.toString(),
            biodataKkToJson
        )
    }
}