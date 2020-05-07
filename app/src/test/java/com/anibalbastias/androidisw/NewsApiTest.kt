package com.anibalbastias.androidisw

import com.anibalbastias.androidisw.data.datasource.remote.api.NewsApi
import com.anibalbastias.library.base.presentation.extensions.await

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest


import org.koin.test.inject

class NewsApiTest : KoinTest {

    private val query = "Njedq4WpjWz4KKk"
    private val queryError = "asdasd"

    val api by inject<NewsApi>()

    @Before
    fun start_koin() {
        startKoin {
            modules(
                apiTestModule
            )
        }
    }

    @After
    fun stop_koin() {
        stopKoin()
    }

    @Test
    fun `should get news from api`() {
        val result = runBlocking {
            api.getLatestNews(token = query).await()
        }

        Assert.assertNotNull(result); result ?: return

        Assert.assertTrue(result.items?.isNotEmpty() == true)
    }
}

