package com.example.containertracker.ui.home

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.containertracker.R
import com.example.containertracker.base.BaseFragment
import com.example.containertracker.data.container.models.Container
import com.example.containertracker.data.location.models.Location
import com.example.containertracker.data.salesorder.models.SalesOrderNumber
import com.example.containertracker.data.user.models.User
import com.example.containertracker.databinding.FragmentHomeBinding
import com.example.containertracker.databinding.InputVoyageDialogBinding
import com.example.containertracker.ui.history.detail.HistoryDetailActivity
import com.example.containertracker.ui.home.containercondition.ContainerConditionBottomSheet
import com.example.containertracker.ui.main.MainViewModel
import com.example.containertracker.utils.PreferenceUtils
import com.example.containertracker.utils.constants.ExtrasConstant.EXTRA_CONTAINER_DATA
import com.example.containertracker.utils.constants.ExtrasConstant.EXTRA_SO_NUMBER
import com.example.containertracker.utils.constants.ExtrasConstant.EXTRA_VOYAGE_ID_IN
import com.example.containertracker.utils.constants.ExtrasConstant.EXTRA_VOYAGE_ID_OUT
import com.example.containertracker.utils.enums.StatusResponseEnum
import com.example.containertracker.utils.extension.observeNonNull
import com.example.containertracker.utils.response.GenericErrorResponse
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(), ZXingScannerView.ResultHandler {

    private val viewModel: HomeViewModel by viewModel()
    private val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private lateinit var scannerView: ZXingScannerView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initScanner()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
    }

    private fun showVoyageIdField(container: Container, status: String = "") {
        val dialog = Dialog(activity!!)

        val bindingToast: InputVoyageDialogBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.input_voyage_dialog, null, false
        )
        bindingToast.viewModel = viewModel
        bindingToast.lifecycleOwner = activity

        viewModel.voyageId.value = if (status == "out") container.voyageIdOut
            else container.voyageIdIn
        viewModel.voyageIdIn.value = container.voyageIdIn

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(bindingToast.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        bindingToast.buttonSubmit.setOnClickListener {
            if (status == "in") {
                viewModel.voyageIdIn.value = viewModel.voyageId.value
            } else if (status == "out") {
                viewModel.voyageIdOut.value = viewModel.voyageId.value
            }
            showContainerCondition(container)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showContainerCondition(container: Container) {
        val user = PreferenceUtils.get<User>(PreferenceUtils.USER_PREFERENCE)
        val location = mainViewModel.locationLiveData.value
        val containerConditionBottomSheet = ContainerConditionBottomSheet(container) {
            viewModel.containerConditionModel.value = it
            viewModel.saveHistory(container.uniqueId, user?.id.orEmpty(), location?.id.orEmpty())
        }
        containerConditionBottomSheet.isCancelable = false
        containerConditionBottomSheet.show(parentFragmentManager, containerConditionBottomSheet.tag)
    }

    private fun observer() {
        viewModel.apply {
            containerLiveData.observe(viewLifecycleOwner, ::handleContainerScan)
            statusSaveLiveData.observeNonNull(viewLifecycleOwner, ::handleStatusSaved)
            loadingWidgetLiveData.observeNonNull(viewLifecycleOwner, { handleLoadingWidget(it) })
            genericErrorLiveData.observeNonNull(viewLifecycleOwner, ::handleErrorWidget)
            soNumberList.observeNonNull(viewLifecycleOwner, ::handleSONumber)
        }
    }

    private fun handleSONumber(list: List<SalesOrderNumber>) {
        mainViewModel.soNumberListLiveData.value = list
    }

    private fun handleErrorWidget(genericErrorResponse: GenericErrorResponse) {
        showErrorMessage(genericErrorResponse.status.toString())
        scannerView.resumeCameraPreview(this)
    }

    private fun handleStatusSaved(statusSave: String) {
        if (statusSave == StatusResponseEnum.SUCCESS.status) {
            goToHistoryDetail()
            showSuccessMessage("Success Save")
        }
    }

    private fun handleContainerScan(container: Container) {
        if (container.id.isNotBlank())
            if (mainViewModel.locationLiveData.value?.id == mainViewModel.locationListLiveData.value?.first()?.id
                && container.voyageIdIn.isNullOrBlank()) {
                showVoyageIdField(container, "in")
            } else if (mainViewModel.locationLiveData.value?.id == mainViewModel.locationListLiveData.value?.last()?.id) {
                showVoyageIdField(container, "out")
            } else {
                showContainerCondition(container)
            }
    }

    private fun initScanner() {
        scannerView = ZXingScannerView(requireContext())
        scannerView.setAutoFocus(true)
        scannerView.setResultHandler(this)
        binding.containerScanner.addView(scannerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun handleResult(value: Result?) {
        val result = value.toString()
        if (result.isNotBlank()) {
            viewModel.getContainer(result)
        } else {
            scannerView.resumeCameraPreview(this)
        }
    }

    private fun goToHistoryDetail() {
        val container = viewModel.containerLiveData.value
        val intent = Intent(activity, HistoryDetailActivity::class.java)
        intent.putExtra(EXTRA_CONTAINER_DATA, container)
        intent.putExtra(EXTRA_VOYAGE_ID_IN, viewModel.voyageIdIn.value)
        intent.putExtra(EXTRA_VOYAGE_ID_OUT, viewModel.voyageIdOut.value)
        intent.putExtra(
            EXTRA_SO_NUMBER,
            viewModel.containerConditionModel.value?.salesOrderNumber.orEmpty()
        )
        startActivity(intent)
    }

    override fun onStart() {
        scannerView.startCamera()
        super.onStart()
    }

    override fun onPause() {
        scannerView.stopCamera()
        super.onPause()
    }

    override fun onResume() {
        scannerView.resumeCameraPreview(this)
        super.onResume()
    }
}