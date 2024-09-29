package dev.mvrlxc.zmeumusic.domain.repository

import dev.mvrlxc.zmeumusic.data.remote.model.SearchSongsDTO
import dev.mvrlxc.zmeumusic.data.remote.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface YouTubeApiRepository {

    suspend fun search(q: String): Flow<ResultState<List<SearchSongsDTO>>>

}