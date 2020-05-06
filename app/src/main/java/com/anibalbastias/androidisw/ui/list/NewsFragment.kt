package com.anibalbastias.androidisw.ui.list

import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.anibalbastias.androidisw.R
import com.anibalbastias.androidisw.databinding.FragmentNewsBinding
import com.anibalbastias.androidisw.domain.model.DomainNewsItem
import com.anibalbastias.androidisw.presentation.mapper.UiNewsMapper
import com.anibalbastias.androidisw.presentation.model.UiNewsItem
import com.anibalbastias.androidisw.presentation.model.UiWrapperNews
import com.anibalbastias.androidisw.presentation.state.SearchState
import com.anibalbastias.androidisw.presentation.viewmodel.NewsViewModel
import com.anibalbastias.androidisw.ui.NewsNavigator
import com.anibalbastias.library.base.data.coroutines.Result
import com.anibalbastias.library.base.presentation.extensions.isNetworkAvailable
import com.anibalbastias.library.base.presentation.extensions.observe
import com.anibalbastias.library.uikit.adapter.base.BaseBindClickHandler
import com.anibalbastias.library.uikit.adapter.base.SingleLayoutBindRecyclerAdapter
import com.anibalbastias.library.uikit.adapter.filter.FilterWordListener
import com.anibalbastias.library.uikit.databinding.paginationForRecyclerScroll
import com.anibalbastias.library.uikit.extension.applyFontForToolbarTitle
import com.anibalbastias.library.uikit.extension.initSwipe
import com.anibalbastias.library.uikit.extension.setNoArrowUpToolbar
import com.anibalbastias.library.uikit.extension.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class NewsFragment : Fragment(),
    BaseBindClickHandler<UiNewsItem>,
    FilterWordListener<UiNewsItem> {

    private val connectionManager: ConnectivityManager by inject()
    private val newsViewModel: NewsViewModel by viewModel()
    private val uiNewsMapper: UiNewsMapper by inject()
    private val newsNavigator: NewsNavigator by inject()

    lateinit var binding: FragmentNewsBinding
    private var isLoading = ObservableBoolean(false)
    private var isError = ObservableBoolean(false)
    private var itemPosition: ObservableInt = ObservableInt(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = DataBindingUtil.bind<ViewDataBinding>(view) as FragmentNewsBinding
        binding.callback = this
        binding.isLoading = isLoading
        binding.isError = isError
        binding.callback = this
        binding.lifecycleOwner = this

        initToolbar()
        newsNavigator.init(view)

        with(newsViewModel) {
            observe(state, ::stateObserver)
            observe(newsLiveResult, ::newsObserver)
        }

        binding.srlNews?.initSwipe {
            newsViewModel.getNews("Njedq4WpjWz4KKk")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_menu, menu)
        val item: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = MenuItemCompat.getActionView(item) as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                val adapter = (binding.rvNews.adapter
                        as? SingleLayoutBindRecyclerAdapter<UiNewsItem>)
                adapter?.getFilter()?.filter(query)
                return false
            }
        })
    }

    private fun initToolbar() {
        binding.tbNews.run {
            applyFontForToolbarTitle(activity!!)
            setNoArrowUpToolbar(activity!!)
        }
    }

    private fun stateObserver(state: SearchState?) {
        state ?: return

        newsViewModel.run {
            (newsLiveResult.value as? Result.OnSuccess<List<DomainNewsItem>>)?.let { result ->
                newsObserver(result)
            } ?: getNews("Njedq4WpjWz4KKk")
        }
    }

    private fun newsObserver(result: Result<List<DomainNewsItem>>?) {
        when (result) {
            is Result.OnLoading -> {
                binding.isLoading?.set(true)
                binding.isError?.set(false)
            }
            is Result.OnSuccess -> {
                binding.isLoading?.set(false)
                binding.isError?.set(false)
                binding.srlNews.isRefreshing = false

                binding.rvNews.paginationForRecyclerScroll(itemPosition)

                with(uiNewsMapper) {
                    binding.news = UiWrapperNews(items = result.value.map { it.fromDomainToUi() })
                }
            }
            is Result.OnError -> {
                binding.isLoading?.set(false)
                binding.isError?.set(true)
                binding.srlNews.isRefreshing = false

                if (connectionManager.isNetworkAvailable()) {
                    activity?.toast(getString(R.string.error_connection))
                } else {
                    activity?.toast(getString(R.string.error_connection_internet))
                }
            }
        }
    }

    override fun onClickView(view: View, item: UiNewsItem) {
        newsNavigator.navigateToNewsDetails(view, item)
    }

    override fun onFilterByWord(
        word: String,
        selectedItem: UiNewsItem,
        filteredItems: ArrayList<UiNewsItem>
    ) {
        if (selectedItem.title?.contains(word, ignoreCase = true) == true) {
            filteredItems.add(selectedItem)
        }
    }
}
