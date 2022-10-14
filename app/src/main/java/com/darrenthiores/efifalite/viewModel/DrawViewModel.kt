package com.darrenthiores.efifalite.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.darrenthiores.core.domain.FootballUseCase
import com.darrenthiores.core.model.presenter.Player
import com.darrenthiores.core.utils.DataMapper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class DrawViewModel(
    private val footballUseCase: FootballUseCase
): ViewModel() {

    private val _obtainedPlayer = MutableStateFlow(
        Player(0,  "", 0, "", "", "", "", "", "", 0, "", 0.0)
    )
    val obtainedPlayer: StateFlow<Player>
        get() = _obtainedPlayer

    fun getPlayers(league: Int): Flow<PagingData<Player>> =
        footballUseCase.getPlayers(league).map { pagingData ->
            pagingData.map {
                DataMapper.mapPlayerToPresenter(it)
            }
        }.
        cachedIn(viewModelScope)

    fun getCoin(): Flow<Int> = runBlocking { footballUseCase.getCoin() }

    fun draw(league: Int, coin: Int) = viewModelScope.launch {
        footballUseCase.updateCoin(coin)
        _obtainedPlayer.value = footballUseCase.draw(league).let { DataMapper.mapPlayerToPresenter(it) }
    }

}