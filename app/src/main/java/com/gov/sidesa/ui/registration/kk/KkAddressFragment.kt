package com.gov.sidesa.ui.registration.kk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.google.gson.Gson
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseFragment
import com.gov.sidesa.base.showImmediately
import com.gov.sidesa.data.registration.kk.KkAddressModel
import com.gov.sidesa.data.registration.ktp.AddressKtpModel
import com.gov.sidesa.databinding.CustomAddressBinding
import com.gov.sidesa.databinding.FragmentKkAddressBinding
import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.ui.regions.SelectRegionBottomSheet
import com.gov.sidesa.ui.registration.RegistrationStackState
import com.gov.sidesa.ui.registration.ktp.RegistrationKTPViewModel
import com.gov.sidesa.utils.extension.isNullOrZero
import com.gov.sidesa.utils.extension.orZero
import org.koin.androidx.viewmodel.ext.android.viewModel

class KkAddressFragment : BaseFragment() {

    companion object {
        fun newInstance(): KkAddressFragment {
            return KkAddressFragment()
        }
    }

    private lateinit var binding: FragmentKkAddressBinding
    private val viewModel: RegistrationKTPViewModel by activityViewModels()

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
        initListener()
        initObserver()
        checkAddressFilled()
    }

    private fun initObserver() = with(viewModel) {
        inputKKProvince.observe(viewLifecycleOwner, ::updateUIProvince)
        inputKKCity.observe(viewLifecycleOwner, ::updateUICity)
        inputKKKecamatan.observe(viewLifecycleOwner, ::updateUIDistrict)
        inputKKKelurahan.observe(viewLifecycleOwner, ::updateUIVillage)
    }

    private fun initListener() = with(binding.customKkAddress) {
        inputKtpAddress.doOnTextChanged { _, _, _, _ ->
            checkAddressFilled()
        }
        inputKtpRt.doOnTextChanged { _, _, _, _ ->
            checkAddressFilled()
        }
        inputKtpRw.doOnTextChanged { _, _, _, _ ->
            checkAddressFilled()
        }
    }

    private fun restoreUserData() = with(binding.customKkAddress) {
        viewModel.getPref(requireContext(), RegistrationStackState.KtpAddress.toString())
            ?.let { jsonAddress ->
                val addressModel = Gson().fromJson(jsonAddress, AddressKtpModel::class.java)
                viewModel.inputKKProvince.value = addressModel.province
                viewModel.inputKKCity.value = addressModel.city
                viewModel.inputKKKecamatan.value = addressModel.kecamatan
                viewModel.inputKKKelurahan.value =  addressModel.kelurahan
                inputKtpAddress.setText(addressModel.address)
                inputKtpRw.setText(addressModel.rw)
                inputKtpRt.setText(addressModel.rt)
            }

        setDropDownProvince(this)
        setDropDownCity(this)
        setDropDownKecamatan(this)
        setDropDownKelurahan(this)
        setDropDownRw(this)
        setDropDownRt(this)
    }

    private fun setDropDownProvince(customAddressBinding: CustomAddressBinding) {
        customAddressBinding.inputKtpProvince.setOnClickListener {
            showProvinceBottomSheet()
        }
    }

    private fun setDropDownCity(customAddressBinding: CustomAddressBinding) {
        customAddressBinding.inputKtpCity.setOnClickListener {
            if (viewModel.inputKKProvince.value == null || viewModel.inputKKProvince.value?.id.isNullOrZero())
                showErrorMessage(getString(R.string.province_has_not_been_selected))
            else
                showCityBottomSheet()
        }
    }

    private fun setDropDownKecamatan(customAddressBinding: CustomAddressBinding) {
        customAddressBinding.inputKtpKecamatan.setOnClickListener {
            if (viewModel.inputKKCity.value == null || viewModel.inputKKCity.value?.id.isNullOrZero())
                showErrorMessage(getString(R.string.city_has_not_been_selected))
            else
                showDistrictBottomSheet()
        }
    }

    private fun setDropDownKelurahan(customAddressBinding: CustomAddressBinding) {
        customAddressBinding.inputKtpKelurahan.setOnClickListener {
            if (viewModel.inputKKKecamatan.value == null || viewModel.inputKKKecamatan.value?.id.isNullOrZero())
                showErrorMessage(getString(R.string.district_has_not_been_selected))
            else
                showVillageBottomSheet()
        }
    }

    private fun setDropDownRw(customAddressBinding: CustomAddressBinding) {
        customAddressBinding.inputKtpRw.setOnClickListener {
            if (viewModel.inputKKKelurahan.value == null || viewModel.inputKKKelurahan.value?.id.isNullOrZero())
                showErrorMessage(getString(R.string.village_has_not_been_selected))
            else
                showRwBottomSheet()
        }
    }

    private fun setDropDownRt(customAddressBinding: CustomAddressBinding) {
        customAddressBinding.inputKtpRt.setOnClickListener {
            if (viewModel.inputKKRw.value == null || viewModel.inputKKRw.value?.name.isNullOrBlank())
                showErrorMessage(getString(R.string.rw_has_not_been_selected))
            else
                showRtBottomSheet()
        }
    }

    private fun showVillageBottomSheet() {
        val tag = "select_village"
        showImmediately(parentFragmentManager, tag) {
            val sheet = SelectRegionBottomSheet.createVillage(
                districtId = viewModel.inputKKKecamatan.value?.id ?: 0
            )
            sheet.onSelected = {
                viewModel.inputKKKelurahan.value = it
                binding.customKkAddress.inputKtpKelurahan.setText(it.name)
                sheet.dismissAllowingStateLoss()
                checkAddressFilled()
            }
            sheet
        }
    }

    private fun showDistrictBottomSheet() {
        val tag = "select_district"
        showImmediately(parentFragmentManager, tag) {
            val sheet = SelectRegionBottomSheet.createDistrict(
                cityId = viewModel.inputKKCity.value?.id ?: 0
            )
            sheet.onSelected = {
                viewModel.inputKKKecamatan.value = it
                binding.customKkAddress.inputKtpKecamatan.setText(it.name)
                sheet.dismissAllowingStateLoss()
                checkAddressFilled()
            }
            sheet
        }
    }

    private fun showCityBottomSheet() {
        val tag = "select_city"
        showImmediately(parentFragmentManager, tag) {
            val sheet = SelectRegionBottomSheet.createCity(
                provinceId = viewModel.inputKKProvince.value?.id ?: 0
            )
            sheet.onSelected = {
                viewModel.inputKKCity.value = it
                binding.customKkAddress.inputKtpCity.setText(it.name)
                sheet.dismissAllowingStateLoss()
                checkAddressFilled()
            }
            sheet
        }
    }

    private fun showProvinceBottomSheet() {
        val tag = "select_province"
        showImmediately(parentFragmentManager, tag) {
            val sheet = SelectRegionBottomSheet.createProvince()
            sheet.onSelected = {
                viewModel.inputKKProvince.value = it
                binding.customKkAddress.inputKtpProvince.setText(it.name)
                sheet.dismissAllowingStateLoss()
                checkAddressFilled()
            }
            sheet
        }
    }

    private fun showRwBottomSheet() {
        val tag = "select_rw"
        showImmediately(parentFragmentManager, tag) {
            val sheet = SelectRegionBottomSheet.createRW(
                villageId = viewModel.inputKKKelurahan.value?.id ?: 0
            )
            sheet.onSelected = {
                viewModel.inputKKRw.value = it
                binding.customKkAddress.inputKtpRw.setText(it.name)
                sheet.dismissAllowingStateLoss()
                checkAddressFilled()
            }
            sheet
        }
    }

    private fun showRtBottomSheet() {
        val tag = "select_rt"
        showImmediately(parentFragmentManager, tag) {
            val sheet = SelectRegionBottomSheet.createRT(
                villageId = viewModel.inputKKKelurahan.value?.id.orZero(),
                rw = viewModel.inputKKRw.value?.name ?: ""
            )
            sheet.onSelected = {
                viewModel.inputKKRt.value = it
                binding.customKkAddress.inputKtpRt.setText(it.name)
                sheet.dismissAllowingStateLoss()
                checkAddressFilled()
            }
            sheet
        }
    }

    override fun onResume() {
        super.onResume()
        checkAddressFilled()
    }

    private fun checkAddressFilled() {
        viewModel.isAddressKKFilled.value =
            binding.customKkAddress.inputKtpAddress.text.toString().isNotBlank() &&
                    binding.customKkAddress.inputKtpRt.text.toString().isNotBlank() &&
                    binding.customKkAddress.inputKtpRw.text.toString().isNotBlank() &&
                    binding.customKkAddress.inputKtpProvince.text.toString().isNotBlank() &&
                    binding.customKkAddress.inputKtpCity.text.toString().isNotBlank() &&
                    binding.customKkAddress.inputKtpKecamatan.text.toString().isNotBlank() &&
                    binding.customKkAddress.inputKtpKelurahan.text.toString().isNotBlank()
    }

    private fun updateUIProvince(region: Region) =
        binding.customKkAddress.inputKtpProvince.setText(region.name)

    private fun updateUICity(region: Region) = binding.customKkAddress.inputKtpCity.setText(region.name)
    private fun updateUIDistrict(region: Region) =
        binding.customKkAddress.inputKtpKecamatan.setText(region.name)

    private fun updateUIVillage(region: Region) =
        binding.customKkAddress.inputKtpKelurahan.setText(region.name)
}