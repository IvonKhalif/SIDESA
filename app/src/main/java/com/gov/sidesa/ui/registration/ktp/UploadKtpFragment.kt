package com.gov.sidesa.ui.registration.ktp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.gov.sidesa.databinding.FragmentUploadKtpBinding
import com.gov.sidesa.ui.registration.RegistrationStackState
import com.gov.sidesa.utils.PreferenceUtils
import com.gov.sidesa.utils.extension.load
import com.gov.sidesa.utils.gone
import com.gov.sidesa.utils.picker.SelectImageBottomSheet
import com.gov.sidesa.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream
import java.io.File


class UploadKtpFragment : Fragment() {

    companion object {
        fun newInstance(): UploadKtpFragment {
            return UploadKtpFragment()
        }
    }

    private lateinit var binding: FragmentUploadKtpBinding
    private val viewModel: RegistrationKTPViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUploadKtpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val pref = viewModel.getPref(requireContext(), RegistrationStackState.KtpUpload.toString())
        if (pref.isNotEmpty()) {
            binding.buttonPickKtp.gone()
            binding.containerKtp.visible()
            Glide.with(requireContext())
                .load(pref)
                .into(binding.imageKtp)
        }

        checkKTPUploaded()
    }

    override fun onPause() {
        super.onPause()

        viewModel.setPref(
            requireContext(),
            RegistrationStackState.KtpUpload.toString(),
            base64
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PreferenceUtils.getAccount()?.imageKTP?.let {
            binding.imageKtp.load(it)
            checkKTPUploaded()
        }

        binding.buttonPickKtp.setOnClickListener {
            showMediaDialog()
        }

        binding.buttonEditKtp.setOnClickListener {
            showMediaDialog()
        }

        checkKTPUploaded()
    }

    private fun showMediaDialog() {
        val media = SelectImageBottomSheet.newInstance()

        media.onImageSelected = {
            setImageKtp(it)
            binding.buttonPickKtp.gone()
            media.dismissAllowingStateLoss()
            checkKTPUploaded()
        }

        media.showNow(childFragmentManager, media.javaClass.canonicalName)
    }

    private fun setImageKtp(it: File) {
        base46Image(it.path)
        binding.containerKtp.visible()
        Glide.with(requireContext())
            .load(it)
            .into(binding.imageKtp)
    }

    var base64 = ""
    private fun base46Image(filePath: String): String {
        val imgFile = File(filePath)
        if (imgFile.exists() && imgFile.length() > 0) {
            val bm = BitmapFactory.decodeFile(filePath)
            val bOut = ByteArrayOutputStream()
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bOut)
            base64 = Base64.encodeToString(bOut.toByteArray(), Base64.DEFAULT)
        }

        return base64
    }

    private fun checkKTPUploaded() {
        viewModel.isUploadKTPFilled.value =
            hasImage(binding.imageKtp)
    }

    private fun hasImage(view: ImageView?): Boolean {
        val drawable: Drawable? = view?.drawable
        var hasImage = drawable == null
        if (hasImage && drawable is BitmapDrawable) {
            hasImage = drawable.bitmap != null
        }
        return hasImage
    }
}