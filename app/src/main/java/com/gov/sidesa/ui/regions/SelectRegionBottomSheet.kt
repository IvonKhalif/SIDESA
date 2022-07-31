package com.gov.sidesa.ui.regions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.gov.sidesa.base.BaseBottomSheet
import com.gov.sidesa.databinding.BottomSheetSelectRegionBinding
import com.gov.sidesa.domain.regions.models.Region
import com.gov.sidesa.utils.gone
import com.gov.sidesa.utils.picker.MenuIconWithHeadlineAdapter
import com.gov.sidesa.utils.picker.RecyclerViewItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

open class SelectRegionBottomSheet private constructor(): BaseBottomSheet() {

    private var _binding: BottomSheetSelectRegionBinding? = null
    protected val binding get() = _binding!!

    private val viewModel by viewModel<SelectRegionViewModel>()

    private val adapter by lazy {
        MenuIconWithHeadlineAdapter(
            setIconLeft = { 0 },
            setHeadline = { it.name },
            onClick = onSelected,
            areContentTheSame = { oldItem, newItem -> oldItem == newItem },
            areItemsTheSame = { oldItem, newItem -> oldItem.id == newItem.id },
            customUi = {
                ivIcon.gone()
            }
        )
    }

    private val argsRegionType by lazy {
        arguments?.getInt(ARGS_REGION_TYPE) ?: -1
    }
    private val argsProvinceId by lazy {
        arguments?.getLong(ARGS_PROVINCE_ID) ?: -1
    }
    private val argsCityId by lazy {
        arguments?.getLong(ARGS_CITY_ID) ?: -1
    }
    private val argsDistrictId by lazy {
        arguments?.getLong(ARGS_DISTRICT_ID) ?: -1
    }

    var onSelected: (Region) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetSelectRegionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onLoad(
            regionType = argsRegionType,
            provinceId = argsProvinceId,
            cityId = argsCityId,
            districtId = argsDistrictId
        )
        initObserver()
        initView()
        initEvent()
    }

    private fun initObserver() = with(viewModel) {
        titleText.observe(viewLifecycleOwner) {
            binding.tvTitle.text = getString(it)
        }

        loadingState.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        networkErrorState.observe(viewLifecycleOwner) {
            showErrorMessage(throwable = it)
        }

        serverErrorState.observe(viewLifecycleOwner) {
            showErrorMessage(message = it.status.orEmpty())
        }

        closeViewState.observe(viewLifecycleOwner) {
            dismissAllowingStateLoss()
        }

        regionData.observe(viewLifecycleOwner) {
            binding.menuList.post {
                adapter.submitList(it)
            }
        }
    }

    private fun initEvent() = with(binding) {
        btnClose.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }

    private fun initView() = with(binding) {
        menuList.adapter = adapter
        menuList.layoutManager = LinearLayoutManager(
            requireContext()
        )
        menuList.addItemDecoration(RecyclerViewItemDecoration())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARGS_REGION_TYPE = "args_region_type"
        private const val ARGS_PROVINCE_ID = "args_province_id"
        private const val ARGS_CITY_ID = "args_city_id"
        private const val ARGS_DISTRICT_ID = "args_district_id"

        fun createProvince(): SelectRegionBottomSheet {
            val dialog = SelectRegionBottomSheet()
            dialog.arguments = Bundle().apply {
                putInt(ARGS_REGION_TYPE, RegionType.Province.type)
            }
            return dialog
        }

        fun createCity(provinceId: Long): SelectRegionBottomSheet {
            val dialog = SelectRegionBottomSheet()
            dialog.arguments = Bundle().apply {
                putInt(ARGS_REGION_TYPE, RegionType.City.type)
                putLong(ARGS_PROVINCE_ID, provinceId)
            }
            return dialog
        }

        fun createDistrict(cityId: Long): SelectRegionBottomSheet {
            val dialog = SelectRegionBottomSheet()
            dialog.arguments = Bundle().apply {
                putInt(ARGS_REGION_TYPE, RegionType.District.type)
                putLong(ARGS_CITY_ID, cityId)
            }
            return dialog
        }

        fun createVillage(districtId: Long): SelectRegionBottomSheet {
            val dialog = SelectRegionBottomSheet()
            dialog.arguments = Bundle().apply {
                putInt(ARGS_REGION_TYPE, RegionType.Village.type)
                putLong(ARGS_DISTRICT_ID, districtId)
            }
            return dialog
        }
    }
}