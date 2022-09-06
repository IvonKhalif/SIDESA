package com.gov.sidesa.ui.registration.ktp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseFragment
import com.gov.sidesa.base.showImmediately
import com.gov.sidesa.data.registration.ktp.AddressKtpModel
import com.gov.sidesa.databinding.CustomAddressBinding
import com.gov.sidesa.databinding.FragmentAddressKtpBinding
import com.gov.sidesa.ui.regions.SelectRegionBottomSheet
import com.gov.sidesa.ui.registration.RegistrationStackState
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.extension.isNullOrZero
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddressKtpFragment : BaseFragment() {

    companion object {
        fun newInstance(): AddressKtpFragment {
            return AddressKtpFragment()
        }
    }

    private lateinit var binding: FragmentAddressKtpBinding
    private val viewModel: RegistrationKTPViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddressKtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onPause() {
        super.onPause()

        val addressKtpToJson = Gson().toJson(
            AddressKtpModel(
                binding.customKtpAddress.inputKtpAddress.text.toString(),
                binding.customKtpAddress.inputKtpRt.text.toString(),
                binding.customKtpAddress.inputKtpRw.text.toString(),
                binding.customKtpAddress.inputKtpKecamatan.text.toString(),
                binding.customKtpAddress.inputKtpKelurahan.text.toString()
            )
        )
        viewModel.setPref(
            requireContext(),
            RegistrationStackState.KtpAddress.toString(),
            addressKtpToJson
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        restoreUserData()
        setDropDownProvince(customKtpAddress)
        setDropDownCity(customKtpAddress)
        setDropDownKecamatan(customKtpAddress)
        setDropDownKelurahan(customKtpAddress)
    }

    private fun restoreUserData() = with(binding.customKtpAddress) {
        PreferenceUtils.getAccount()?.let {
            inputKtpAddress.setText(it.address)
            inputKtpRt.setText(it.rt)
            inputKtpRw.setText(it.rw)
            inputKtpKecamatan.setText(it.district.name)
            inputKtpKelurahan.setText(it.village.name)
        }
    }

    private fun setDropDownProvince(customKtpAddress: CustomAddressBinding) {
        customKtpAddress.inputKtpProvince.setOnClickListener {
            showProvinceBottomSheet()
        }
    }

    private fun setDropDownCity(customKtpAddress: CustomAddressBinding) {
        customKtpAddress.inputKtpCity.setOnClickListener {
            if (viewModel.inputKtpProvince.value == null || viewModel.inputKtpProvince.value?.id.isNullOrZero())
                showErrorMessage(getString(R.string.province_has_not_been_selected))
            else
                showCityBottomSheet()
        }
    }

    private fun setDropDownKecamatan(customKtpAddress: CustomAddressBinding) {
        customKtpAddress.inputKtpKecamatan.setOnClickListener {
            if (viewModel.inputKtpCity.value == null || viewModel.inputKtpCity.value?.id.isNullOrZero())
                showErrorMessage(getString(R.string.city_has_not_been_selected))
            else
                showDistrictBottomSheet()
        }
    }

    private fun setDropDownKelurahan(customKtpAddress: CustomAddressBinding) {
        customKtpAddress.inputKtpKelurahan.setOnClickListener {
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