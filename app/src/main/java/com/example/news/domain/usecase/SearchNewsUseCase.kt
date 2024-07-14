package com.example.news.domain.usecase

import com.example.news.data.model.Headline
import com.example.news.data.utils.State
import com.example.news.domain.repository.SearchNewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(private val searchNewsRepository: SearchNewsRepository) {
    suspend fun execute(quote: String = ""): Flow<State<Headline>> = flow {
        val result = searchNewsRepository.searchNews(quote)
        emit(State.Loading)
        emit(State.Success(result))
    }.catch { e ->
        emit(State.Error(e.message ?: "An unknown error occurred"))
    }
}