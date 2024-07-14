package com.example.news.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.model.Headline
import com.example.news.data.utils.State
import com.example.news.domain.usecase.GetHeadlineUseCase
import com.example.news.domain.usecase.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    app: Application,
    private val getHeadlineUseCase: GetHeadlineUseCase,
    private val searchNewsUseCase: SearchNewsUseCase
) : AndroidViewModel(app) {

    init {
        getHeadline()
    }

    private val _headline = MutableStateFlow<State<Headline>>(State.Loading)
    val headline: StateFlow<State<Headline>> = _headline

    private fun getHeadline() {
        viewModelScope.launch {
            getHeadlineUseCase.execute()
                .catch {
                    _headline.value = State.Error(it.message.toString())
                }
                .collect {
                    _headline.value = it
                }
        }
    }

    suspend fun searchNews(newText: String) {
        _headline.value = State.Loading
        delay(5000)
        if (newText.isNotEmpty())
            viewModelScope.launch {
                searchNewsUseCase.execute(newText)
                    .catch {
                        _headline.value = State.Error(it.message.toString())
                    }
                    .collect {
                        _headline.value = it
                    }
            }
    }
}
