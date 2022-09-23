package com.gov.sidesa.ui.registration.ktp

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
import com.gov.sidesa.data.registration.ktp.AddressKtpModel
import com.gov.sidesa.databinding.CustomAddressBinding
import com.gov.sidesa.databinding.FragmentAddressKtpBinding
import com.gov.sidesa.domain.letter.input.models.resource.asRegion
import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.ui.regions.SelectRegionBottomSheet
import com.gov.sidesa.ui.registration.RegistrationStackState
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.extension.isNullOrZero
import com.gov.sidesa.utils.extension.orZero
import com.gov.sidesa.utils.gone
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddressKtpFragment : BaseFragment() {

    companion object {
        fun newInstance(): AddressKtpFragment {
            return AddressKtpFragment()
        }
    }

    private lateinit var binding: FragmentAddressKtpBinding
    private val viewModel: RegistrationKTPViewModel by activityViewModels()

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
                viewModel.inputKtpProvince.value,
                viewModel.inputKtpCity.value,
                viewModel.inputKtpKecamatan.value,
                viewModel.inputKtpKelurahan.value
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
        setDropDownRw(customKtpAddress)
        setDropDownRt(customKtpAddress)
        checkAddressFilled()
        initListener()
        initObserver()
    }

    private fun initObserver() = with(viewModel) {
        inputKtpProvince.observe(viewLifecycleOwner, ::updateUIProvince)
        inputKtpCity.observe(viewLifecycleOwner, ::updateUICity)
        inputKtpKecamatan.observe(viewLifecycleOwner, ::updateUIDistrict)
        inputKtpKelurahan.observe(viewLifecycleOwner, ::updateUIVillage)
    }

    private fun initListener() = with(binding.customKtpAddress) {
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

    private fun restoreUserData() = with(binding.customKtpAddress) {
        PreferenceUtils.getAccount()?.let {
            inputKtpAddress.setText(it.address)
            inputKtpRt.setText(it.rt)
//            inputKtpRt.isEnabled = it.rt.isBlank()
            inputKtpRw.setText(it.rw)
//            inputKtpRw.isEnabled = it.rw.isBlank()
            inputKtpProvince.isEnabled = it.province.name.isBlank()
            viewModel.inputKtpProvince.value = it.province.asRegion()
            inputKtpCity.isEnabled = it.city.name.isBlank()
            viewModel.inputKtpCity.value = it.city.asRegion()
            inputKtpKecamatan.isEnabled = it.district.name.isBlank()
            viewModel.inputKtpKecamatan.value = it.district.asRegion()
            inputKtpKelurahan.isEnabled = it.village.name.isBlank()
            viewModel.inputKtpKelurahan.value = it.village.asRegion()
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

    private fun setDropDownRw(customKtpAddress: CustomAddressBinding) {
        customKtpAddress.inputKtpRw.setOnClickListener {
            if (viewModel.inputKtpKelurahan.value == null || viewModel.inputKtpKelurahan.value?.id.isNullOrZero())
                showErrorMessage(getString(R.string.village_has_not_been_selected))
            else
                showRwBottomSheet()
        }
    }

    private fun setDropDownRt(customKtpAddress: CustomAddressBinding) {
        customKtpAddress.inputKtpRt.setOnClickListener {
            if (viewModel.inputKtpRw.value == null || viewModel.inputKtpRw.value?.name.isNullOrBlank())
                showErrorMessage(getString(R.string.rw_has_not_been_selected))
            else
                showRtBottomSheet()
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
                binding.customKtpAddress.inputKtpKelurahan.setText(it.name)
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
                cityId = viewModel.inputKtpCity.value?.id ?: 0
            )
            sheet.onSelected = {
                viewModel.inputKtpKecamatan.value = it
                binding.customKtpAddress.inputKtpKecamatan.setText(it.name)
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
                provinceId = viewModel.inputKtpProvince.value?.id ?: 0
            )
            sheet.onSelected = {
                viewModel.inputKtpCity.value = it
                binding.customKtpAddress.inputKtpCity.setText(it.name)
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
                viewModel.inputKtpProvince.value = it
                binding.customKtpAddress.inputKtpProvince.setText(it.name)
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
                villageId = viewModel.inputKtpKelurahan.value?.id ?: 0
            )
            sheet.onSelected = {
                viewModel.inputKtpRw.value = it
                binding.customKtpAddress.inputKtpRw.setText(it.name)
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
                villageId = viewModel.inputKtpKelurahan.value?.id.orZero(),
                rw = viewModel.inputKtpRw.value?.name ?: ""
            )
            sheet.onSelected = {
                viewModel.inputKtpRt.value = it
                binding.customKtpAddress.inputKtpRt.setText(it.name)
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
        viewModel.isAddressKTPFilled.value =
            binding.customKtpAddress.inputKtpAddress.text.toString().isNotBlank() &&
                    binding.customKtpAddress.inputKtpRt.text.toString().isNotBlank() &&
                    binding.customKtpAddress.inputKtpRw.text.toString().isNotBlank() &&
                    binding.customKtpAddress.inputKtpProvince.text.toString().isNotBlank() &&
                    binding.customKtpAddress.inputKtpCity.text.toString().isNotBlank() &&
                    binding.customKtpAddress.inputKtpKecamatan.text.toString().isNotBlank() &&
                    binding.customKtpAddress.inputKtpKelurahan.text.toString().isNotBlank()
    }

    private fun updateUIProvince(region: Region) =
        binding.customKtpAddress.inputKtpProvince.setText(region.name)

    private fun updateUICity(region: Region) = binding.customKtpAddress.inputKtpCity.setText(region.name)
    private fun updateUIDistrict(region: Region) =
        binding.customKtpAddress.inputKtpKecamatan.setText(region.name)

    private fun updateUIVillage(region: Region) =
        binding.customKtpAddress.inputKtpKelurahan.setText(region.name)
}