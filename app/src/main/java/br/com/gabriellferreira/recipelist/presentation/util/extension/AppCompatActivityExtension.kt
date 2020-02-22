package br.com.gabriellferreira.recipelist.presentation.util.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.res.ResourcesCompat
import br.com.gabriellferreira.recipelist.domain.model.Retryable
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.showRetrySnackbar(
    parentView: CoordinatorLayout?,
    text: String,
    retryable: Retryable
) {
    parentView?.let {
        val actionColor =
            ResourcesCompat.getColor(resources, android.R.color.holo_orange_light, theme)
        Snackbar.make(parentView, text, Snackbar.LENGTH_INDEFINITE)
            .setActionTextColor(actionColor)
            .setAction("RETRY") {
                retryable.retry()
            }
            .show()
    }
}