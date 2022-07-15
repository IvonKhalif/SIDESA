package com.example.containertracker.ui.history.detail

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.containertracker.R
import com.example.containertracker.base.BaseActivity
import com.example.containertracker.data.container.models.Container
import com.example.containertracker.data.history.model.HistoryModel
import com.example.containertracker.databinding.ActivityHistoryDetailBinding
import com.example.containertracker.ui.history.HistoryAdapter
import com.example.containertracker.utils.constants.ExtrasConstant.EXTRA_CONTAINER_DATA
import com.example.containertracker.utils.constants.ExtrasConstant.EXTRA_SO_NUMBER
import com.example.containertracker.utils.constants.ExtrasConstant.EXTRA_VOYAGE_ID_IN
import com.example.containertracker.utils.constants.ExtrasConstant.EXTRA_VOYAGE_ID_OUT
import com.example.containertracker.utils.extension.observeNonNull
import com.example.containertracker.utils.response.GenericErrorResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityHistoryDetailBinding
    private val viewModel: HistoryDetailViewModel by viewModel()
    private val historyAdapter by lazy {
        HistoryAdapter(emptyList(), true, ::onHistoryClicked)
    }

    private val containerData by lazy {
        intent.getParcelableExtra<Container>(EXTRA_CONTAINER_DATA)
    }
    private val voyageIdIn by lazy {
        intent.getStringExtra(EXTRA_VOYAGE_ID_IN) ?: "-"
    }
    private val voyageIdOut by lazy {
        intent.getStringExtra(EXTRA_VOYAGE_ID_OUT) ?: "-"
    }
    private val salesOrderNumber by lazy {
        intent.getStringExtra(EXTRA_SO_NUMBER) ?: "-"
    }

    private fun onHistoryClicked(historyModel: HistoryModel) {
        //TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initAdapter()
        observer()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initView() {
        supportActionBar?.title = getString(R.string.detail_container_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.apply {
            textVoyageIn.text = getString(R.string.history_voyage_in, voyageIdIn)
            textVoyageOut.text = getString(R.string.history_voyage_out, voyageIdOut)
            textSoNumber.text = getString(R.string.history_so_number, salesOrderNumber)
            textProductName.text = containerData?.codeContainer
        }
        viewModel.getHistory(containerData?.uniqueId.orEmpty(), containerData?.batchId.orEmpty())
    }

    private fun initAdapter() {
        binding.recyclerHistory.adapter = historyAdapter
        binding.recyclerHistory.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun observer(){
        viewModel.apply {
            historyListLiveData.observeNonNull(this@HistoryDetailActivity, ::updateHistory)
            loadingWidgetLiveData.observeNonNull(this@HistoryDetailActivity, ::handleLoadingWidget)
            genericErrorLiveData.observeNonNull(this@HistoryDetailActivity, ::handleErrorWidget)
        }
    }

    private fun handleErrorWidget(genericErrorResponse: GenericErrorResponse) {
        showErrorMessage(genericErrorResponse.status.toString())
    }

    private fun updateHistory(list: List<HistoryModel>) {
        historyAdapter.items = list
    }
}