package com.darrenthiores.efifalite.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.dp
import com.darrenthiores.core.model.presenter.Starting
import com.darrenthiores.efifalite.component.*
import com.darrenthiores.efifalite.viewModel.LineUpViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
@ExperimentalMaterialApi
fun LineUpScreen(
    onChange: (Starting) -> Unit
) {
    val viewModel = getViewModel<LineUpViewModel>()
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val coroutineScope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            val players = viewModel.userPlayer.collectAsState()
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(32.dp))
                ) {
                    Divider(
                        modifier = Modifier
                            .height(6.dp)
                            .width(56.dp)
                            .background(MaterialTheme.colors.onSurface)
                    )
                }
                val coin = viewModel.getCoin().collectAsState(initial = 0)
                UserPlayerList(
                    players = players.value,
                    onLevelUp = {
                        if(coin.value<10) {
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "You need ${10 - coin.value} more coin"
                                )
                            }
                        } else if(it.rating > 10.0) {
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "Max Level!"
                                )
                            }
                        } else {
                            viewModel.levelUpUserPlayer(it, -10)
                        }
                    }
                )
            }
        },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = Color.White.copy(alpha = 0.12f).compositeOver(Color.Black)
    ) {
        val players = viewModel.starting.collectAsState()
        val coin = viewModel.getCoin().collectAsState(initial = 0)
        val formation = viewModel.getFormation().collectAsState(initial = 433)
        LineUpScreen(
            paddingValues = it,
            players = players.value,
            formation = formation.value,
            updateFormation = { newFormation -> viewModel.updateFormation(newFormation) },
            onChange = onChange,
            onLevelUp = { starting ->
                if(coin.value<10) {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "You need ${10 - coin.value} more coin"
                        )
                    }
                } else if(starting.rating > 10.0){
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "Max Level!"
                        )
                    }
                } else {
                    viewModel.levelUpStarting(starting, -10)
                }
            }
        )
    }
}

@Composable
fun LineUpScreen(
    paddingValues: PaddingValues,
    players: List<Starting>,
    formation: Int,
    updateFormation: (Int) -> Unit,
    onChange: (Starting) -> Unit,
    onLevelUp: (Starting) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(12.dp),
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White.copy(alpha = 0.12f).compositeOver(Color.Black)
        ) {
            if(players.isNotEmpty()) {
                val modifier = Modifier
                    .padding(12.dp)
                when(formation) {
                    433 -> Formation433(
                        modifier = modifier,
                        players = players,
                        onChange = onChange,
                        onLevelUp = onLevelUp
                    )
                    4213 -> Formation4213(
                        modifier = modifier,
                        players = players,
                        onChange = onChange,
                        onLevelUp = onLevelUp
                    )
                    442 -> Formation442(
                        modifier = modifier,
                        players = players,
                        onChange = onChange,
                        onLevelUp = onLevelUp
                    )
                }
            }
        }
        FormationButton(
            modifier = Modifier.align(Alignment.TopEnd)
                .padding(12.dp),
            formation = formation,
            onItemClicked = updateFormation
        )
    }
}