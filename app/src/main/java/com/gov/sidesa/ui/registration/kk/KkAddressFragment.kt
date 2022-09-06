package com.gov.sidesa.ui.registration.kk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseFragment
import com.gov.sidesa.base.showImmediately
import com.gov.sidesa.data.registration.kk.KkAddressModel
import com.gov.sidesa.data.registration.ktp.AddressKtpModel
import com.gov.sidesa.databinding.CustomAddressBinding
import com.gov.sidesa.databinding.FragmentKkAddressBinding
import com.gov.sidesa.ui.regions.SelectRegionBottomSheet
import com.gov.sidesa.ui.registration.RegistrationStackState
import com.gov.sidesa.ui.registration.ktp.RegistrationKTPViewModel
import com.gov.sidesa.utils.extension.isNullOrZero
import org.koin.androidx.viewmodel.ext.android.viewModel

class KkAddressFragment : BaseFragment() {

    companion object {
        fun newInstance(): KkAddressFragment {
            return KkAddressFragment()
        }
    }

    private lateinit var binding: FragmentKkAddressBinding
    private val viewModel: RegistrationKTPViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKkAddressBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onPause() {
        super.onPause()

        val addressKkToJson = Gson().toJson(
            KkAddressModel(
                binding.customKkAddress.inputKtpAddress.text.toString(),
                binding.customKkAddress.inputKtpRt.text.toString(),
                binding.customKkAddress.inputKtpRw.text.toString(),
                binding.customKkAddress.inputKtpProvince.text.toString(),
                binding.customKkAddress.inputKtpCity.text.toString(),
                binding.customKkAddress.inputKtpKecamatan.text.toString(),
                binding.customKkAddress.inputKtpKelurahan.text.toString(),
            )
        )
        viewModel.setPref(
            requireContext(),
            RegistrationStackState.KkAddress.toString(),
            addressKkToJson
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        restoreUserData()
    }

    private fun restoreUserData() = with(binding.customKkAddress) {
        viewModel.getPref(requireContext(), RegistrationStackState.KtpAddress.toString())
            ?.let { jsonAddress ->
                val addressModel = Gson().fromJson(jsonAddress, AddressKtpModel::class.java)
                inputKtpAddress.setText(addressModel.address)
                inputKtpRw.setText(addressModel.rw)
                inputKtpRt.setText(addressModel.rt)
            }

        setDropDownProvince(this)
        setDropDownCity(this)
        setDropDownKecamatan(this)
        setDropDownKelurahan(this)
    }

    private fun setDropDownProvince(customAddressBinding: CustomAddressBinding) {
        customAddressBinding.inputKtpProvince.setOnClickListener {
            showProvinceBottomSheet()
        }
    }

    private fun setDropDownCity(customAddressBinding: CustomAddressBinding) {
        customAddressBinding.inputKtpCity.setOnClickListener {
            if (viewModel.inputKtpProvince.value == null || viewModel.inputKtpProvince.value?.id.isNullOrZero())
                showErrorMessage(getString(R.string.province_has_not_been_selected))
            else
                showCityBottomSheet()
        }
    }

    private fun setDropDownKecamatan(customAddressBinding: CustomAddressBinding) {
        customAddressBinding.inputKtpKecamatan.setOnClickListener {
            if (viewModel.inputKtpCity.value == null || viewModel.inputKtpCity.value?.id.isNullOrZero())
                showErrorMessage(getString(R.string.city_has_not_been_selected))
            else
                showDistrictBottomSheet()
        }
    }

    private fun setDropDownKelurahan(customAddressBinding: CustomAddressBinding) {
        customAddressBinding.inputKtpKelurahan.setOnClickListener {
            if (viewModel.inputKtpKecamatan.value == null || viewModel.inputKtpKecamatan.value?.id.isNullOrZero())
                showErrorMessage(getString(R.string.district_has_not_been_selected))
            else
                showVillageBottomSheet()
        }
    }

    private fun showVillageBottomSheet() {
        val tag = "select_village"
        showImmediately(parentFragmentManager, tag) {
            val sheet = SelectRegionBottomSheet.createVillage(
                districtId = viewModel.inputKtpKecamatan.value?.id ?: 0
            )
            sheet.onSelected = {
                viewModel.inputKtpKelurahan.value = it
                sheet.dismissAllowingStateLoss()
            }
            sheet
        }
    }

    private fun showDistrictBottomSheet() {
        val tag = "select_district"
        showImmediately(parentFragmentManager, tag) {
            val sheet = SelectRegionBottomSheet.createDistrict(
                cityId = viewModel.inputKtpCity.value?.id ?: 0
            )
            sheet.onSelected = {
                viewModel.inputKtpKecamatan.value = it
                sheet.dismissAllowingStateLoss()
            }
            sheet
        }
    }

    private fun showCityBottomSheet() {
        val tag = "select_city"
        showImmediately(parentFragmentManager, tag) {
            val sheet = SelectRegionBottomSheet.createCity(
                provinceId = viewModel.inputKtpProvince.value?.id ?: 0
            )
            sheet.onSelected = {
                viewModel.inputKtpCity.value = it
                sheet.dismissAllowingStateLoss()
            }
            sheet
        }
    }

    private fun showProvinceBottomSheet() {
        val tag = "select_province"
        showImmediately(parentFragmentManager, tag) {
            val sheet = SelectRegionBottomSheet.createProvince()
            sheet.onSelected = {
                viewModel.inputKtpProvince.value = it
                sheet.dismissAllowingStateLoss()
            }
            sheet
        }
    }
}