package com.darrenthiores.efifalite.viewModel

import androidx.lifecycle.ViewModel
import com.darrenthiores.core.domain.FootballUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking

class AppViewModel(
    private val footballUseCase: FootballUseCase
): ViewModel() {

    private val _showDialog = MutableStateFlow(true)
    val showDialog: StateFlow<Boolean>
        get() = _showDialog

    fun closeDialog() {
        _showDialog.value = false
    }

    fun getCoin(): Flow<Int> = runBlocking { footballUseCase.getCoin() }

}