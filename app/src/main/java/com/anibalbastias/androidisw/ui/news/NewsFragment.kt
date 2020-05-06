package com.anibalbastias.androidisw.ui.news

import android.net.ConnectivityManager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.anibalbastias.androidisw.R
import com.anibalbastias.androidisw.domain.model.DomainNewsItem
import com.anibalbastias.androidisw.presentation.mapper.UiNewsMapper
import com.anibalbastias.androidisw.presentation.state.SearchState
import com.anibalbastias.androidisw.presentation.viewmodel.NewsViewModel

import com.anibalbastias.library.base.coroutines.Result
import com.anibalbastias.library.base.extensions.isNetworkAvailable
import com.anibalbastias.library.base.extensions.observe
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : Fragment() {

    private val connectionManager: ConnectivityManager by inject()
    private val viewModel: NewsViewModel by viewModel()
    private val uiNewsMapper: UiNewsMapper by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(rViewSearch) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
//            adapter = artistAdapter

            setHasFixedSize(true)
        }


        searching(searchView)

        with(viewModel) {
            observe(state, ::stateObserver)
            observe(newsLiveResult, ::newsObserver)
        }
    }

    private fun searching(search: SearchView) {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getNews(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return true
            }
        })

    }


    private fun stateObserver(state: SearchState?) {
        state ?: return

        pBarSearch.visibility = View.VISIBLE

        val query = state.query

        viewModel.getNews("Njedq4WpjWz4KKk")
//        if (query.isNotBlank())
//            viewModel.getNews("Njedq4WpjWz4KKk")
//        else
//            viewModel.resetSearch()
    }


    private fun newsObserver(result: Result<List<DomainNewsItem>>?) {
        when (result) {
            is Result.OnLoading -> {
                pBarSearch.visibility = View.VISIBLE
            }
            is Result.OnSuccess -> {
                pBarSearch.visibility = View.GONE

                with(uiNewsMapper) {
                    result.value.map { it.fromDomainToUi() }
                }

//                val artists = result.value
//                    .sortedBy { it.artistName }
//
//                if (artists.isNotEmpty()) {
//                    rViewSearch.visibility = View.VISIBLE
//                } else {
//                    rViewSearch.visibility = View.GONE
//                }
//
//                artistAdapter.swapItems(new = artists)
            }
            is Result.OnError -> {
                pBarSearch.visibility = View.GONE

//                if (connectionManager.isNetworkAvailable())
//                    longToast("conexion fallida al servidor")
//                else
//                    longToast("error de conexion, revisa tu internet e intenta nuevamente")
            }
            else -> {
                pBarSearch.visibility = View.GONE
            }
        }
    }
}
