package com.gov.sidesa.ui.profile.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gov.sidesa.R
import com.gov.sidesa.base.BaseActivity
import com.gov.sidesa.databinding.ActivityDetailProfileFamilyBinding
import com.gov.sidesa.ui.profile.edit.EditProfileFamilyActivity

class DetailProfileFamilyActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailProfileFamilyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailProfileFamilyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.buttonEditFamily.setOnClickListener {
            val intent = Intent(this, EditProfileFamilyActivity::class.java)
            resultLauncher.launch(intent)
        }
    }
}