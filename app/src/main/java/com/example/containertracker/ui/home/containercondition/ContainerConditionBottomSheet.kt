package com.example.containertracker.ui.home.containercondition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.containertracker.R
import com.example.containertracker.data.container.models.Container
import com.example.containertracker.data.container.models.ContainerConditionModel
import com.example.containertracker.databinding.BottomSheetContainerConditionBinding
import com.example.containertracker.ui.home.salesordernumber.SalesOrderNumberBottomSheet
import com.example.containertracker.ui.home.selectcondition.SelectConditionBottomSheet
import com.example.containertracker.ui.main.MainViewModel
import com.example.containertracker.utils.enums.ConditionEnum
import com.example.containertracker.utils.enums.ContainerSidesEnum
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContainerConditionBottomSheet(
    private val container: Container,
    private val onSubmit: (ContainerConditionModel) -> Unit
) : BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetContainerConditionBinding
    private val viewModel: ContainerConditionViewModel by viewModel()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.bottom_sheet_container_condition,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = parentFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observer()
    }

    private fun observer() {
        viewModel.apply {
            backCondition.observe(viewLifecycleOwner, {
                updateSidesCondition(it, ContainerSidesEnum.BACK)
            })
            doorCondition.observe(viewLifecycleOwner, {
                updateSidesCondition(it, ContainerSidesEnum.DOOR)
            })
            upperCondition.observe(viewLifecycleOwner, {
                updateSidesCondition(it, ContainerSidesEnum.UPPER)
            })
            floorCondition.observe(viewLifecycleOwner, {
                updateSidesCondition(it, ContainerSidesEnum.FLOOR)
            })
            leftCondition.observe(viewLifecycleOwner, {
                updateSidesCondition(it, ContainerSidesEnum.LEFT)
            })
            rightCondition.observe(viewLifecycleOwner, {
                updateSidesCondition(it, ContainerSidesEnum.RIGHT)
            })
            salesOrderNumber.observe(viewLifecycleOwner, ::handleSONumber)
        }
    }

    private fun handleSONumber(soNumber: String?) {
        if (soNumber.isNullOrBlank())
            binding.soNumber.setTextForHint(getString(R.string.general_action_select))
        else
            binding.soNumber.setText(soNumber)
    }

    private fun updateSidesCondition(
        conditionEnum: ConditionEnum?,
        containerSide: ContainerSidesEnum
    ) {
        when (containerSide) {
            ContainerSidesEnum.RIGHT -> conditionEnum?.let {
                binding.rightCondition.setDrawableEnd(getDrawableIdCondition(it.type))
                binding.rightCondition.setText(it.type)
            }
            ContainerSidesEnum.LEFT -> conditionEnum?.let {
                binding.leftCondition.setDrawableEnd(getDrawableIdCondition(it.type))
                binding.leftCondition.setText(it.type)
            }
            ContainerSidesEnum.DOOR -> conditionEnum?.let {
                binding.doorCondition.setDrawableEnd(getDrawableIdCondition(it.type))
                binding.doorCondition.setText(it.type)
            }
            ContainerSidesEnum.BACK -> conditionEnum?.let {
                binding.backCondition.setDrawableEnd(getDrawableIdCondition(it.type))
                binding.backCondition.setText(it.type)
            }
            ContainerSidesEnum.UPPER -> conditionEnum?.let {
                binding.upperCondition.setDrawableEnd(getDrawableIdCondition(it.type))
                binding.upperCondition.setText(it.type)
            }
            ContainerSidesEnum.FLOOR -> conditionEnum?.let {
                binding.floorCondition.setDrawableEnd(getDrawableIdCondition(it.type))
                binding.floorCondition.setText(it.type)
            }
        }
    }

    private fun getDrawableIdCondition(type: String): Int {
        return if (type == ConditionEnum.GOOD.type) {
            R.drawable.ic_check_18dp
        } else {
            R.drawable.ic_warning_18dp
        }
    }

    private fun initView() {
        dialog?.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val bottomSheet = dialog.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet?.let { sheet ->
                dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                dialog.behavior.skipCollapsed = true
                sheet.parent.requestLayout()
            }
        }
        binding.headerBasicTextTitleSubheadline.text = container.codeContainer
        viewModel.sealNumber.value = container.sealId
        viewModel.backCondition.value =
            getConditionDefault(container.backDoorSideCondition.orEmpty())
        binding.backCondition.setTitle("Back")
        binding.backCondition.onClickDropDownListener {
            selectCondition(ContainerSidesEnum.BACK.type)
        }
        viewModel.doorCondition.value =
            getConditionDefault(container.frontDoorSideCondition.orEmpty())
        binding.doorCondition.setTitle("Door")
        binding.doorCondition.onClickDropDownListener {
            selectCondition(ContainerSidesEnum.DOOR.type)
        }
        viewModel.upperCondition.value = getConditionDefault(container.roofSideCondition.orEmpty())
        binding.upperCondition.setTitle("Roof")
        binding.upperCondition.onClickDropDownListener {
            selectCondition(ContainerSidesEnum.UPPER.type)
        }
        viewModel.floorCondition.value = getConditionDefault(container.floorSideCondition.orEmpty())
        binding.floorCondition.setTitle("Floor")
        binding.floorCondition.onClickDropDownListener {
            selectCondition(ContainerSidesEnum.FLOOR.type)
        }
        viewModel.leftCondition.value = getConditionDefault(container.leftSideCondition.orEmpty())
        binding.leftCondition.setTitle("Left")
        binding.leftCondition.onClickDropDownListener {
            selectCondition(ContainerSidesEnum.LEFT.type)
        }
        viewModel.rightCondition.value = getConditionDefault(container.rightSideCondition.orEmpty())
        binding.rightCondition.setTitle("Right")
        binding.rightCondition.onClickDropDownListener {
            selectCondition(ContainerSidesEnum.RIGHT.type)
        }

        viewModel.salesOrderNumber.value = container.salesOrderNumber
        binding.soNumber.setTitle(getString(R.string.so_number_label))
        binding.soNumber.isVisible = mainViewModel.locationLiveData.value?.type == "OUT"
        binding.soNumber.setOnClickListener {
            selectSONumber()
        }

        binding.buttonSubmit.setOnClickListener {
            onSubmit(
                ContainerConditionModel(
                    sealNumber = viewModel.sealNumber.value,
                    salesOrderNumber = viewModel.salesOrderNumber.value,
                    upperCondition = viewModel.upperCondition.value,
                    floorCondition = viewModel.floorCondition.value,
                    doorCondition = viewModel.doorCondition.value,
                    backCondition = viewModel.backCondition.value,
                    leftCondition = viewModel.leftCondition.value,
                    rightCondition = viewModel.rightCondition.value,
                )
            )
            dismiss()
        }
    }

    private fun getConditionDefault(condition: String) =
        ConditionEnum.values().find { it.type == condition } ?: ConditionEnum.GOOD

    private fun selectCondition(type: String) {
        val selectPostBottomSheet = SelectConditionBottomSheet {
            when (type) {
                ContainerSidesEnum.RIGHT.type -> viewModel.rightCondition.value = it
                ContainerSidesEnum.LEFT.type -> viewModel.leftCondition.value = it
                ContainerSidesEnum.DOOR.type -> viewModel.doorCondition.value = it
                ContainerSidesEnum.BACK.type -> viewModel.backCondition.value = it
                ContainerSidesEnum.UPPER.type -> viewModel.upperCondition.value = it
                ContainerSidesEnum.FLOOR.type -> viewModel.floorCondition.value = it
            }
        }
        selectPostBottomSheet.show(childFragmentManager, selectPostBottomSheet.tag)
    }

    private fun selectSONumber() {
        val bottomSheet = SalesOrderNumberBottomSheet{
            viewModel.salesOrderNumber.value = it
        }
        bottomSheet.show(childFragmentManager, bottomSheet.tag)
    }
}