package com.anibalbastias.androidisw.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.anibalbastias.androidisw.R
import com.anibalbastias.androidisw.databinding.FragmentNewsDetailBinding
import com.anibalbastias.library.uikit.extension.applyFontForToolbarTitle
import com.anibalbastias.library.uikit.extension.setArrowUpToolbar

class NewsDetailFragment : Fragment() {

    lateinit var binding: FragmentNewsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_news_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentNewsDetailBinding
        binding.lifecycleOwner = this

        initToolbar()
    }

    private fun initToolbar() {
        binding.toolbar?.run {
            applyFontForToolbarTitle(activity!!)
            setArrowUpToolbar(activity!!)
        }
    }

}
