package com.darrenthiores.efifalite.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darrenthiores.core.domain.FootballUseCase
import com.darrenthiores.core.model.presenter.Starting
import com.darrenthiores.core.model.presenter.UserPlayer
import com.darrenthiores.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LineUpViewModel(
    private val footballUseCase: FootballUseCase
):ViewModel() {

    private val _starting = MutableStateFlow<List<Starting>>(emptyList())
    val starting: StateFlow<List<Starting>>
        get() = _starting

    private val _userPlayer = MutableStateFlow<List<UserPlayer>>(emptyList())
    val userPlayer: StateFlow<List<UserPlayer>>
        get() = _userPlayer

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
        viewModelScope.launch {
            footballUseCase.getUserPlayers().collect { userPlayer ->
                _userPlayer.value = if(userPlayer.isNotEmpty()) {
                    DataMapper.mapUserPlayerToPresenter(userPlayer)
                } else {
                    emptyList()
                }
            }
        }
    }

    fun getCoin(): Flow<Int> = runBlocking { footballUseCase.getCoin() }

    fun getFormation(): Flow<Int> = runBlocking { footballUseCase.getFormation() }

    fun updateFormation(formation: Int) = viewModelScope.launch {
        footballUseCase.updateFormation(formation)
    }

    fun levelUpStarting(starting: Starting, coin: Int) = viewModelScope.launch {
        footballUseCase.updateCoin(coin)
        footballUseCase.updateUserStarting(DataMapper.mapStartingPresenterToDomain(starting))
    }

    fun levelUpUserPlayer(userPlayer: UserPlayer, coin: Int) = viewModelScope.launch {
        footballUseCase.updateCoin(coin)
        footballUseCase.updateUserPlayer(DataMapper.mapUserPlayerPresenterToDomain(userPlayer))
    }

}