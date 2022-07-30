package com.gov.sidesa.ui.registration.kk

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.gov.sidesa.data.registration.kk.KkAddressModel
import com.gov.sidesa.data.registration.kk.KkBiodataModel
import com.gov.sidesa.databinding.FragmentReviewKkBinding
import com.gov.sidesa.ui.registration.RegistrationStackState
import com.gov.sidesa.ui.registration.ktp.RegistrationKTPViewModel

class ReviewKkFragment : Fragment() {

    private lateinit var binding: FragmentReviewKkBinding
    private val viewModel by activityViewModels<RegistrationKTPViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewKkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadReviewDetail()
    }

    private fun loadReviewDetail() {
        val biodataPref =
            viewModel.getPref(requireContext(), RegistrationStackState.KkBiodata.toString())
        val addressPref =
            viewModel.getPref(requireContext(), RegistrationStackState.KkAddress.toString())
        val uploadPref =
            viewModel.getPref(requireContext(), RegistrationStackState.KkUpload.toString())

        val biodataModel = Gson().fromJson(biodataPref, KkBiodataModel::class.java)
        val addressModel = Gson().fromJson(addressPref, KkAddressModel::class.java)

        binding.textKk.text = biodataModel.kkNumber
        binding.textKepalaKeluaraga.text = biodataModel.kepalaKeluargaName
        binding.textAlamat.text = addressModel.address

        Glide.with(this)
            .load(getBitmap(uploadPref))
            .into(binding.imageKk)
    }

    private fun getBitmap(base64: String): Bitmap? {
        val imageAsBytes: ByteArray = Base64.decode(base64.toByteArray(), Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)
    }

}