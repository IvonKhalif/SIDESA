package com.gov.sidesa.ui.registration.kk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.gson.Gson
import com.gov.sidesa.data.registration.kk.KkBiodataModel
import com.gov.sidesa.databinding.FragmentKkBiodataBinding
import com.gov.sidesa.ui.registration.RegistrationStackState
import com.gov.sidesa.ui.registration.ktp.RegistrationKTPViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class KkBiodataFragment : Fragment() {

    companion object {
        fun newInstance(): KkBiodataFragment {
            return KkBiodataFragment()
        }
    }

    private lateinit var binding: FragmentKkBiodataBinding
    private val viewModel: RegistrationKTPViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentKkBiodataBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
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

    private fun initListener() = with(binding.customKkBiodata) {
        inputKkNumber.doOnTextChanged { _, _, _, _ ->
            checkBiodataFilled()
        }
        inputKkKepalaKeluarga.doOnTextChanged { _, _, _, _ ->
            checkBiodataFilled()
        }
    }

    override fun onResume() {
        super.onResume()
        checkBiodataFilled()
    }

    private fun checkBiodataFilled() {
        viewModel.isBiodataKKFilled.value =
            binding.customKkBiodata.inputKkNumber.text.toString().isNotBlank() &&
                    binding.customKkBiodata.inputKkKepalaKeluarga.text.toString().isNotBlank()
    }
}