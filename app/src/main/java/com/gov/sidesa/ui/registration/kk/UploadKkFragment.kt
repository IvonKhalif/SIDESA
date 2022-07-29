package com.gov.sidesa.ui.registration.kk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.gov.sidesa.databinding.FragmentUploadKkBinding
import com.gov.sidesa.utils.gone
import com.gov.sidesa.utils.picker.SelectImageBottomSheet
import com.gov.sidesa.utils.visible
import java.io.File

class UploadKkFragment : Fragment() {

    private lateinit var binding: FragmentUploadKkBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUploadKkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonPickKk.setOnClickListener {
            showMediaDialog()
        }

        binding.buttonEditKk.setOnClickListener {
            showMediaDialog()
        }
    }

    private fun showMediaDialog() {
        val media = SelectImageBottomSheet.newInstance()

        media.onImageSelected = {
            setImageKtp(it)
            binding.buttonPickKk.gone()
            media.dismissAllowingStateLoss()
        }

        media.showNow(childFragmentManager, media.javaClass.canonicalName)
    }

    private fun setImageKtp(it: File) {
        binding.containerKk.visible()
        Glide.with(requireContext())
            .load(it)
            .into(binding.imageKtp)
    }

}