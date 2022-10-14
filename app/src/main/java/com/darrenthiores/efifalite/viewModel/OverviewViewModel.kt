package com.darrenthiores.efifalite.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darrenthiores.core.domain.FootballUseCase
import com.darrenthiores.core.model.presenter.Starting
import com.darrenthiores.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OverviewViewModel(
    private val footballUseCase: FootballUseCase
): ViewModel() {

    private val _starting = MutableStateFlow<List<Starting>>(emptyList())
    val starting: StateFlow<List<Starting>>
        get() = _starting

    init {
        viewModelScope.launch {
            footballUseCase.getStarting().collect { starting ->
                _starting.value = if(starting.isNotEmpty()) {
                    DataMapper.mapStartingsToPresenter(starting)
                } else {
                    emptyList()
                }
            }
        }
    }

    fun getLogin(): Flow<Boolean> = footballUseCase.getLogin()

    fun updateLogin(state: Boolean) = viewModelScope.launch { footballUseCase.updateLogin(state) }

    fun updateCoin(coin: Int) = viewModelScope.launch { footballUseCase.updateCoin(coin) }

}