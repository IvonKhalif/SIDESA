package com.gov.sidesa.utils.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.gov.sidesa.R

/**
 * Created by yovi.putra on 05/08/22"
 * Project name: SIDESA
 **/

fun ImageView.load(
    source: Any?,
    builder: RequestBuilder<Drawable>.() -> Unit = {},
    onSuccess: () -> Unit = {},
    onFailed: (GlideException?) -> Unit = {},
) {
    val circularProgressDrawable = CircularProgressDrawable(context).also {
        it.strokeWidth = 5f
        it.centerRadius = 30f
        it.setColorSchemeColors(R.color.lightGreen)
        it.start()
    }

    Glide.with(context)
        .load(source)
        .placeholder(circularProgressDrawable)
        .error(R.drawable.ic_baseline_sync_24)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .apply {
            builder.invoke(this)
        }
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onFailed.invoke(e)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onSuccess.invoke()
                return false
            }
        }).into(this)
}

fun String.downloadImage(contex: Context)= Glide.with(contex)
    .downloadOnly()
    .load(this)
    .diskCacheStrategy(DiskCacheStrategy.NONE)
    .skipMemoryCache(true)
    .submit()
    .get()