package dev.mvrlxc.zmeumusic.data.remote.utils

suspend fun <T : Any?> apiCall(apiCall: suspend () -> T): ResultState<T> {
    return try {
        ResultState.Loading

        ResultState.Success(apiCall.invoke())
    } catch (e: Exception) {
        ResultState.Failure(exception = e)
    }
}