package com.gov.sidesa.ui.registration.ktp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.gov.sidesa.data.registration.ktp.AddressKtpModel
import com.gov.sidesa.data.registration.ktp.BiodataKtpModel
import com.gov.sidesa.data.registration.ktp.GeneralKtpModel
import com.gov.sidesa.databinding.FragmentReviewKtpBinding
import com.gov.sidesa.ui.registration.RegistrationStackState
import org.koin.androidx.viewmodel.ext.android.viewModel


class ReviewKtpFragment : Fragment() {

    companion object {
        fun newInstance(): ReviewKtpFragment {
            return ReviewKtpFragment()
        }
    }

    private lateinit var binding: FragmentReviewKtpBinding
    private val viewModel: RegistrationKTPViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewKtpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadReviewDetail()
    }

    private fun loadReviewDetail() {
        val biodataPref =
            viewModel.getPref(requireContext(), RegistrationStackState.KtpBiodata.toString())
        val addressPref =
            viewModel.getPref(requireContext(), RegistrationStackState.KtpAddress.toString())
        val generalPref =
            viewModel.getPref(requireContext(), RegistrationStackState.KtpGeneral.toString())
        val uploadPref =
            viewModel.getPref(requireContext(), RegistrationStackState.KtpUpload.toString())

        val biodataModel = Gson().fromJson(biodataPref, BiodataKtpModel::class.java)
        val addressModel = Gson().fromJson(addressPref, AddressKtpModel::class.java)
        val generalModel = Gson().fromJson(generalPref, GeneralKtpModel::class.java)

        binding.textNik.text = biodataModel.nik
        binding.textName.text = biodataModel.fullName
        binding.textTtl.text = "${biodataModel.placeOdBirth}, ${biodataModel.dateOfBirth}"
        binding.textGender.text = biodataModel.gender
        binding.textBloodType.text = biodataModel.bloodType

        binding.textAddress.text = addressModel.address

        binding.textReligion.text = generalModel.religion
        binding.textMarriageStatus.text = generalModel.marriageStatus
        binding.textJob.text = generalModel.job
        binding.textNationality.text = generalModel.nationality

        Glide.with(this)
            .load(getBitmap(uploadPref))
            .into(binding.imageIdCard)
    }

    private fun getBitmap(base64: String): Bitmap? {
        val imageAsBytes: ByteArray = Base64.decode(base64.toByteArray(), Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)
    }
}