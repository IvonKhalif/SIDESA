package com.example.containertracker.ui.container.detail

import android.os.Bundle
import android.util.Base64
import com.bumptech.glide.Glide
import com.example.containertracker.R
import com.example.containertracker.base.BaseActivity
import com.example.containertracker.data.container.models.Container
import com.example.containertracker.data.container.models.ContainerDetail
import com.example.containertracker.databinding.ActivityContainerDetailBinding
import com.example.containertracker.utils.constants.ExtrasConstant
import com.example.containertracker.utils.extension.observeNonNull
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContainerDetailActivity : BaseActivity() {
    private lateinit var binding: ActivityContainerDetailBinding
    private val viewModel: ContainerDetailViewModel by viewModel()

    private val container by lazy {
        intent.getParcelableExtra<ContainerDetail>(ExtrasConstant.EXTRA_CONTAINER_DATA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContainerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        observer()
    }

    private fun initView() {
        supportActionBar?.title = getString(R.string.container_qr_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel.getContainerQR(container?.id.orEmpty())

        binding.codeContainer = container?.codeContainer.orEmpty()
    }

    private fun observer() {
        viewModel.qrContainerLiveData.observeNonNull(this, ::handleImage)
    }

    private fun handleImage(containerQR: ContainerDetail) {
        val imageByteArray: ByteArray = Base64.decode(containerQR.imgBase64, Base64.DEFAULT)
        Glide.with(this)
            .load(imageByteArray)
            .into(binding.imageContainerQr)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}