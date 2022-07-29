package com.gov.sidesa.ui.registration.ktp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.gov.sidesa.databinding.FragmentUploadKtpBinding
import com.gov.sidesa.utils.picker.SelectImageBottomSheet
import java.io.File

class UploadKtpFragment : Fragment() {

    companion object {
        fun newInstance(): UploadKtpFragment {
            return UploadKtpFragment()
        }
    }

    private lateinit var binding: FragmentUploadKtpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUploadKtpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonPickKtp.setOnClickListener {
            showMediaDialog()
        }

        binding.buttonEditKtp.setOnClickListener {
            showMediaDialog()
        }
    }

    private fun showMediaDialog() {
        val media = SelectImageBottomSheet.newInstance()

        media.onImageSelected = {
            setImageKtp(it)
            binding.buttonPickKtp.gone()
            media.dismissAllowingStateLoss()
        }

        media.showNow(childFragmentManager, media.javaClass.canonicalName)
    }

    private fun setImageKtp(it: File) {
        binding.containerKtp.visible()
        Glide.with(requireContext())
            .load(it)
            .into(binding.imageKtp)
    }

}