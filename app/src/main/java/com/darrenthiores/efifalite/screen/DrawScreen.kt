package com.darrenthiores.efifalite.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.darrenthiores.core.model.presenter.Player
import com.darrenthiores.efifalite.R
import com.darrenthiores.efifalite.component.BaseCarousel
import com.darrenthiores.efifalite.component.PlayerDetailDialog
import com.darrenthiores.efifalite.component.PlayerList
import com.darrenthiores.efifalite.viewModel.DrawViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
@ExperimentalPagerApi
@ExperimentalMaterialApi
fun DrawScreen() {
    val viewModel = getViewModel<DrawViewModel>()
    val pagerState = rememberPagerState()
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val coroutineScope = rememberCoroutineScope()

    var showDialog by remember {
        mutableStateOf(false)
    }

    var league by remember {
        mutableStateOf(140)
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            val players = viewModel.getPlayers(league).collectAsLazyPagingItems()
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 8.dp)
            ) {
                Box(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(32.dp))
                ) {
                    Divider(
                        modifier = Modifier
                            .height(6.dp)
                            .width(56.dp)
                            .background(MaterialTheme.colors.onSurface)
                    )
                }
                PlayerList(players = players)
            }
        },
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = Color.White.copy(alpha = 0.12f).compositeOver(Color.Black),
        sheetPeekHeight = 180.dp
    ) {
        DrawScreen(
            paddingValues = it,
            onDraw = { coin ->
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "You need $coin more coin"
                    )
                }
            },
            viewModel = viewModel,
            pagerState = pagerState,
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onClick = { showDialog = true },
            league = league,
            onPageChange = { newLeague -> league = newLeague }
        )
    }

}

@Composable
@ExperimentalPagerApi
@ExperimentalMaterialApi
private fun DrawScreen(
    paddingValues: PaddingValues,
    onDraw: (Int) -> Unit,
    viewModel: DrawViewModel,
    pagerState: PagerState,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onClick: () -> Unit,
    league: Int,
    onPageChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .semantics { contentDescription = "Draw Screen" }
            .padding(paddingValues)
    ) {
        BaseCarousel(
            modifier = Modifier.padding(top = 16.dp),
            pagerState = pagerState,
            onClick = {  },
            count = 5,
            image = { index -> getDrawImage(index) },
            customButton = {
                val coin = viewModel.getCoin().collectAsState(initial = 0)
                val player = viewModel.obtainedPlayer.collectAsState()

                DrawButton(
                    coin = coin.value,
                    onDraw = onDraw,
                    draw = { viewModel.draw(league, -100 ) },
                    player = player.value,
                    showDialog = showDialog,
                    onDismiss = onDismiss,
                    onClick = onClick
                )
            }
        )

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                val newLeague = getLeague(page)
                onPageChange(newLeague)
            }
        }
    }
}

@Composable
private fun DrawButton(
    modifier: Modifier = Modifier,
    coin: Int,
    onDraw: (Int) -> Unit,
    draw: () -> Unit,
    player: Player,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = {
            if(coin<100) {
                onDraw(100 - coin)
            } else {
                draw()
                onClick()
        } },
        modifier = modifier.padding(top = 12.dp)
    ) {
        if(showDialog) {
            if(player.id != 0) {
                PlayerDetailDialog(
                    onDismiss = onDismiss,
                    name = player.name,
                    age = player.age,
                    nationality = player.nationality,
                    height = player.height,
                    weight = player.weight,
                    photo = player.photo,
                    club = player.club,
                    clubPhoto = player.clubPhoto,
                    position = player.position,
                    rating = player.rating,
                    button = {  }
                )
            }
        }
        Text(text = "DRAW")
        Spacer(modifier = Modifier.width(12.dp))
        Icon(imageVector = Icons.Default.Money, contentDescription = "", tint = Color.Yellow)
    }
}

private fun getDrawImage(
    index: Int
): Int =
    when(index) {
        0 -> R.drawable.laliga
        1 -> R.drawable.premierleague
        2 -> R.drawable.seriea
        3 -> R.drawable.bundesliga
        4 -> R.drawable.league1
        else -> R.drawable.laliga
    }

private fun getLeague(
    index: Int
): Int =
    when(index) {
        0 -> 140
        1 -> 39
        2 -> 135
        3 -> 78
        4 -> 61
        else -> 140
    }