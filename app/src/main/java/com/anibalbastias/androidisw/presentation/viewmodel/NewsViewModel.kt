package com.anibalbastias.androidisw.presentation.viewmodel

import com.anibalbastias.androidisw.domain.model.DomainNewsItem
import com.anibalbastias.androidisw.domain.usecase.GetNewsUseCase
import com.anibalbastias.androidisw.presentation.state.SearchState
import com.anibalbastias.library.base.presentation.extensions.LiveResult
import com.anibalbastias.library.base.presentation.viewmodel.BaseViewModel

open class NewsViewModel(
    private val getNewsUseCase: GetNewsUseCase
) : BaseViewModel<SearchState>(initState = SearchState()) {

    val newsLiveResult = LiveResult<List<DomainNewsItem>>()

    fun getNews(token: String) {
        getNewsUseCase.execute(liveData = newsLiveResult, params = token)
    }

}