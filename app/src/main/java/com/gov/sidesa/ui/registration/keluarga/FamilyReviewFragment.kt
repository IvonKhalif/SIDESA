package com.gov.sidesa.ui.registration.keluarga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.gov.sidesa.data.registration.family.FamilyApplicantModel
import com.gov.sidesa.data.registration.family.FamilyChildModel
import com.gov.sidesa.data.registration.family.FamilyFatherModel
import com.gov.sidesa.data.registration.family.FamilyMotherModel
import com.gov.sidesa.data.registration.ktp.AddressKtpModel
import com.gov.sidesa.databinding.FragmentFamilyReviewBinding
import com.gov.sidesa.ui.registration.RegistrationStackState
import com.gov.sidesa.ui.registration.ktp.RegistrationKTPViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FamilyReviewFragment : Fragment() {

    private lateinit var binding: FragmentFamilyReviewBinding
    private val viewModel: RegistrationKTPViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFamilyReviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.customReviewFather.labelHeaderFamily.text = "Ayah"
        binding.customReviewMother.labelHeaderFamily.text = "Ibu"
        binding.customReviewApplicant.labelHeaderFamily.text = "Suami"
        binding.customReviewChild.labelHeaderFamily.text = "Anak 1"

        loadReviewDetail()
    }

    private fun loadReviewDetail() {
        val fatherPref =
            viewModel.getPref(requireContext(), RegistrationStackState.FamilyFather.toString())
        val motherPref =
            viewModel.getPref(requireContext(), RegistrationStackState.FamilyMother.toString())
        val applicantPref =
            viewModel.getPref(requireContext(), RegistrationStackState.FamilyApplicant.toString())
        val childPref =
            viewModel.getPref(requireContext(), RegistrationStackState.FamilyChild.toString())
        val addressPref =
            viewModel.getPref(requireContext(), RegistrationStackState.KtpAddress.toString())

        val fatherModel = Gson().fromJson(fatherPref, FamilyFatherModel::class.java)
        fatherModel.addressKtp = Gson().fromJson(addressPref, AddressKtpModel::class.java)
        val motherModel = Gson().fromJson(motherPref, FamilyMotherModel::class.java)
        val applicantModel = Gson().fromJson(applicantPref, FamilyApplicantModel::class.java)
        val childModel = Gson().fromJson(childPref, FamilyChildModel::class.java)

        binding.customReviewFather.textFamilyName.text = fatherModel.name
        binding.customReviewFather.textFamilyNik.text = fatherModel.nik
        binding.customReviewFather.textFamilyTtl.text =
            "${fatherModel.placeOfBirth}, ${fatherModel.dateOfBirth}"
        binding.customReviewFather.textFamilyAddress.text = fatherModel.addressKtp?.address

        binding.customReviewMother.textFamilyName.text = motherModel.name
        binding.customReviewMother.textFamilyNik.text = motherModel.nik
        binding.customReviewMother.textFamilyTtl.text =
            "${motherModel.placeOfBirth}, ${motherModel.dateOfBirth}"
        binding.customReviewMother.textFamilyAddress.text = motherModel.addressKtp?.address

        binding.customReviewApplicant.textFamilyName.text = applicantModel.name
        binding.customReviewApplicant.textFamilyName.text = applicantModel.name
        binding.customReviewApplicant.textFamilyNik.text = applicantModel.nik
        binding.customReviewApplicant.textFamilyTtl.text =
            "${applicantModel.placeOfBirth}, ${applicantModel.dateOfBirth}"
        binding.customReviewApplicant.textFamilyAddress.text = applicantModel.addressKtp?.address

        binding.customReviewChild.textFamilyName.text = childModel.name
        binding.customReviewChild.textFamilyNik.text = childModel.nik
        binding.customReviewChild.textFamilyTtl.text =
            "${childModel.placeOfBirth}, ${childModel.dateOfBirth}"
        binding.customReviewChild.textFamilyAddress.text = childModel.addressKtp?.address

    }

}