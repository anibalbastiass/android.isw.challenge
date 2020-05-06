package com.anibalbastias.androidisw

import com.anibalbastias.androidisw.data.datasource.remote.api.NewsApi

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest


import org.koin.test.inject

class iTunesApiSearchTest : KoinTest {

    private val artistSearchQuery = "movimiento original"
    private val artistIdQuery: Long = 390239894
    private val albumIdQuery: Long = 1482414913

    private val artistSearchQueryError = ""
    private val artistIdQueryError= 0L
    private val albumIdQueryError = 0L

    val api by inject<NewsApi>()

    @Before
    fun start_koin() {
        startKoin {
            modules(
                apiTestModule
            )
        }
    }

    @Test
    fun should_get_artists_from_api() {
        val result = runBlocking {
            api.searchArtists(terms = artistSearchQuery).await()
        }

        Assert.assertNotNull(result); result ?: return

        Assert.assertTrue(result.resultCount > 0)
    }

    @Test
    fun should_get_artist_from_api_error(){
        val result = runBlocking {
            api.searchArtists(terms = artistSearchQueryError).await()
        }

        Assert.assertNotNull(result); result ?: return

        Assert.assertTrue(result.resultCount > 0)
    }

    @Test
    fun should_get_artists_album_from_api() {
        val result = runBlocking {
            api.lookupAlbums(artistId = artistIdQuery).await()
        }

        Assert.assertNotNull(result); result ?: return

        Assert.assertTrue(result.resultCount > 0)
    }

    @Test
    fun should_get_artists_album_from_api_error() {
        val result = runBlocking {
            api.lookupAlbums(artistId = artistIdQueryError).await()
        }

        Assert.assertNotNull(result); result ?: return

        Assert.assertTrue(result.resultCount ==  0)
    }

    @Test
    fun should_get_albums_from_api() {
        val result = runBlocking {
            api.lookupTracks(albumId = albumIdQuery).await()
        }

        Assert.assertNotNull(result); result ?: return

        Assert.assertTrue(result.resultCount > 0)
    }

    @Test
    fun should_get_albums_from_api_error() {
        val result = runBlocking {
            api.lookupTracks(albumId = albumIdQueryError).await()
        }

        Assert.assertNotNull(result); result ?: return

        Assert.assertTrue(result.resultCount == 0)
    }


    @After
    fun stop_koin() {
        stopKoin()
    }
}

