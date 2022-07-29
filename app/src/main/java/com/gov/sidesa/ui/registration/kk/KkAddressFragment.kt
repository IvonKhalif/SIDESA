package com.gov.sidesa.ui.registration.kk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.gov.sidesa.R
import com.gov.sidesa.databinding.FragmentKkAddressBinding
import com.gov.sidesa.ui.registration.ktp.getDummyKecamatanList
import com.gov.sidesa.ui.registration.ktp.getDummyKelurahanList

class KkAddressFragment : Fragment() {

    companion object {
        fun newInstance(): KkAddressFragment {
            return KkAddressFragment()
        }
    }

    private lateinit var binding: FragmentKkAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentKkAddressBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDropDownProvince()
        setDropDownCity()
        setDropDownKecamatan()
        setDropDownKelurahan()
    }

    private fun setDropDownProvince() {
        val provinceList = resources.getStringArray(R.array.province)
        val provinceAdapter =
            ArrayAdapter(requireContext(), R.layout.item_dropdown, provinceList)
        val provinceAutoComplete =
            binding.root.findViewById<AutoCompleteTextView>(R.id.input_ktp_province)
        provinceAutoComplete.setAdapter(provinceAdapter)
    }

    private fun setDropDownCity() {
        val provinceList = resources.getStringArray(R.array.city)
        val provinceAdapter =
            ArrayAdapter(requireContext(), R.layout.item_dropdown, provinceList)
        val provinceAutoComplete =
            binding.root.findViewById<AutoCompleteTextView>(R.id.input_ktp_city)
        provinceAutoComplete.setAdapter(provinceAdapter)
    }

    private fun setDropDownKecamatan() {
        val provinceAdapter =
            ArrayAdapter(requireContext(), R.layout.item_dropdown, getDummyKecamatanList())
        val provinceAutoComplete =
            binding.root.findViewById<AutoCompleteTextView>(R.id.input_ktp_kecamatan)
        provinceAutoComplete.setAdapter(provinceAdapter)
    }

    private fun setDropDownKelurahan() {
        val provinceAdapter =
            ArrayAdapter(requireContext(), R.layout.item_dropdown, getDummyKelurahanList())
        val provinceAutoComplete =
            binding.root.findViewById<AutoCompleteTextView>(R.id.input_ktp_kelurahan)
        provinceAutoComplete.setAdapter(provinceAdapter)
    }
}