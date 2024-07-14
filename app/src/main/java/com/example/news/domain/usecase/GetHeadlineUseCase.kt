package com.example.news.domain.usecase

import com.example.news.data.model.Headline
import com.example.news.data.utils.State
import com.example.news.data.utils.StringUtils.Companion.US
import com.example.news.domain.repository.HeadlineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetHeadlineUseCase @Inject constructor(private val headlineRepository: HeadlineRepository) {
    suspend fun execute(country: String = US): Flow<State<Headline>> = flow {
        val result = headlineRepository.retrieveHeadline(country)
        emit(State.Loading)
        emit(State.Success(result))
    }.catch { e ->
        emit(State.Error(e.message ?: "An unknown error occurred"))
    }
}