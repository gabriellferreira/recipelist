package br.com.gabriellferreira.recipelist.presentation.util.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadCenterCrop(url: String) {
    Glide.with(this.context)
        .load(url)
        .apply(RequestOptions().centerCrop())
        .into(this)
}