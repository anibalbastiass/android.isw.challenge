package com.anibalbastias.androidisw.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.anibalbastias.androidisw.R
import com.anibalbastias.androidisw.presentation.model.UiNewsItem
import com.anibalbastias.androidisw.ui.list.NewsFragmentDirections
import com.anibalbastias.library.uikit.extension.getImageUri
import com.anibalbastias.library.uikit.extension.shareImageFromURI


class NewsNavigator {

    companion object {
        const val IMAGE_TYPE = "image/jpeg"
        const val KEY_ITEMS = "itemNews"
        const val SECOND_TRANSITION = "secondTransitionName"
    }

    var navController: NavController? = null

    fun init(view: View) {
        navController = Navigation.findNavController(view)
    }

    private fun getImageViewFromChild(view: View): ImageView {
        val cardView = (view as? CardView)
        val cl1 = (cardView?.getChildAt(0) as? ConstraintLayout)
        return (cl1?.getChildAt(0) as? ImageView)!!
    }

    fun navigateToNewsDetails(
        view: View,
        item: UiNewsItem
    ) {
        val extras = FragmentNavigatorExtras(
            getImageViewFromChild(view) to SECOND_TRANSITION
        )
        ViewCompat.setTransitionName(getImageViewFromChild(view), SECOND_TRANSITION)

        navController?.navigate(
            NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(item).actionId,
            bundleOf(Pair(KEY_ITEMS, item)), null, extras
        )
    }

    fun shareNewsToEmail(activity: Activity, item: UiNewsItem) {
        shareImageFromURI(item.imageUrl) { bitmap ->
            val shareBody = item.description
            val sharingIntent = Intent(Intent.ACTION_SEND)

            sharingIntent.type = IMAGE_TYPE
            sharingIntent.putExtra(
                Intent.EXTRA_SUBJECT,
                activity.getString(R.string.subject_email, item.title)
            )
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)

            bitmap?.let {
                sharingIntent.putExtra(Intent.EXTRA_STREAM, activity.getImageUri(it))
            }

            activity.startActivity(
                Intent.createChooser(
                    sharingIntent,
                    activity.getString(R.string.share_email)
                )
            )
        }
    }
}