package com.gov.sidesa.ui.registration.ktp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gov.sidesa.R

class BiodataKtpFragment : Fragment() {

    companion object {
        fun newInstance(): BiodataKtpFragment {
            return BiodataKtpFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_biodata_ktp, container, false)
    }
}