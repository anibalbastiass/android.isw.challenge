package com.anibalbastias.androidisw.ui

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.anibalbastias.androidisw.presentation.model.UiNewsItem
import com.anibalbastias.androidisw.ui.list.NewsFragmentDirections

class NewsNavigator {

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
            getImageViewFromChild(view) to "secondTransitionName"
        )
        ViewCompat.setTransitionName(getImageViewFromChild(view), "secondTransitionName")

        navController?.navigate(
            NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment().actionId,
            bundleOf(Pair("itemNews", item)), null, extras
        )
    }
}