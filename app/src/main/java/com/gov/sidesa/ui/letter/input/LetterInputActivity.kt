package com.gov.sidesa.ui.letter.input

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityLetterInputBinding
import com.gov.sidesa.ui.letter.input.adapter.LetterInputAdapter
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactory
import com.gov.sidesa.ui.letter.input.view_holder_factory.LetterInputViewHolderFactoryImpl

class LetterInputActivity : BaseActivity() {

    private var _binding: ActivityLetterInputBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LetterInputViewModel>()

    private val viewHolderFactory: LetterInputViewHolderFactory by lazy {
        LetterInputViewHolderFactoryImpl()
    }

    private val adapter by lazy {
        LetterInputAdapter(viewHolderFactory = viewHolderFactory, listener = viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLetterInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initObserver()
        initView()
        initEvent()
    }

    /**
     * set listener for handling input from user
     */
    private fun initEvent() = with (binding){
        viewModel.onLoad()

        customToolbar.toolbarDetailProfile.setNavigationOnClickListener {
            finish()
        }

        buttonSave.setOnClickListener {
            viewModel.onSubmit()
        }
    }

    /**
     * observing view-model data
     */
    private fun initObserver() = with(viewModel) {
        componentData.observe(this@LetterInputActivity) {
            binding.rvComponents.post {
                adapter.submitList(it)
            }
        }
    }

    /**
     * prepare content view
     */
    private fun initView() = with (binding) {
        customToolbar.apply {
            toolbarDetailProfile.title = getString(R.string.letter_input_submission)
            toolbarDetailProfile.setNavigationIcon(R.drawable.ic_arrow_left)
        }

        rvComponents.apply {
            rvComponents.adapter = this@LetterInputActivity.adapter
            rvComponents.layoutManager = LinearLayoutManager(this@LetterInputActivity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}