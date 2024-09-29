package dev.mvrlxc.zmeumusic.ui.screen.search

import androidx.lifecycle.viewModelScope
import dev.mvrlxc.zmeumusic.data.remote.utils.ResultState
import dev.mvrlxc.zmeumusic.domain.repository.YouTubeApiRepository
import dev.mvrlxc.zmeumusic.ui.base.BaseViewModel
import dev.mvrlxc.zmeumusic.ui.base.ViewEvent
import dev.mvrlxc.zmeumusic.utils.Constants.BASE_URL
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(
    private val youTubeApiRepositoryImpl: YouTubeApiRepository,
) : BaseViewModel<SearchViewState, SearchViewEvent>() {

    private var searchJob: Job? = null

    override fun createInitialState(): SearchViewState = SearchViewState()

    override fun onTriggerEvent(event: SearchViewEvent) {
        when (event) {
            is SearchViewEvent.OnTextChange -> {
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    setState {
                        currentState.copy(
                            textFieldValue = event.text,
                            isLoading = true,
                            activeSongIndex = -1
                        )
                    }
                    if (currentState.textFieldValue.isNotBlank()) {
                        delay(500)
                        search(q = currentState.textFieldValue)
                    }
                }
            }

            is SearchViewEvent.OnCardClick -> {
                onCardClick(event.index)
            }
        }

    }

    private suspend fun search(q: String) {
        youTubeApiRepositoryImpl.search(currentState.textFieldValue).collect {
            when (it) {
                is ResultState.Loading -> {
                    setState {
                        currentState.copy(isLoading = true)
                    }
                }

                is ResultState.Failure -> {
                    setState {
                        currentState.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = it.exception.toString()
                        )
                    }
                }

                is ResultState.Success -> {
                    setState {
                        currentState.copy(
                            isLoading = false,
                            isError = false,
                            songs = it.data,
                        )
                    }
                }
            }
        }
    }

    private fun onCardClick(index: Int) {
        setState { currentState.copy(activeSongIndex = index) }
    }


}

sealed class SearchViewEvent : ViewEvent {
    class OnTextChange(val text: String) : SearchViewEvent()
    class OnCardClick(val index: Int) : SearchViewEvent()
}