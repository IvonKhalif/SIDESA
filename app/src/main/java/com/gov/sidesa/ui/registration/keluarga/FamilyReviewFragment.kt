package com.gov.sidesa.ui.registration.keluarga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gov.sidesa.databinding.FragmentFamilyReviewBinding

class FamilyReviewFragment : Fragment() {

    private lateinit var binding: FragmentFamilyReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFamilyReviewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.customReviewMother.labelHeaderFamily.text = "Ibu"
        binding.customReviewApplicant.labelHeaderFamily.text = "Suami"
        binding.customReviewChild.labelHeaderFamily.text = "Anak 1"
    }
}