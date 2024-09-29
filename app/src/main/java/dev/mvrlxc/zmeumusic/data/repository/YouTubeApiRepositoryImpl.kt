package dev.mvrlxc.zmeumusic.data.repository

import dev.mvrlxc.zmeumusic.data.remote.model.SearchSongsDTO
import dev.mvrlxc.zmeumusic.data.remote.utils.ResultState
import dev.mvrlxc.zmeumusic.data.remote.utils.apiCall
import dev.mvrlxc.zmeumusic.domain.repository.YouTubeApiRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class YouTubeApiRepositoryImpl(
    private val httpClient: HttpClient,
) : YouTubeApiRepository {


    override suspend fun search(q: String): Flow<ResultState<List<SearchSongsDTO>>> {
        return flowOf(
            apiCall {
                httpClient.get(urlString = "/search") {
                    parameter("q", q)
                    parameter("filter", "songs")
                }.body<List<SearchSongsDTO>>()
            }
        )
    }
}