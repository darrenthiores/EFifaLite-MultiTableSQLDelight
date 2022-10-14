package com.darrenthiores.efifalite.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.dp
import com.darrenthiores.core.model.presenter.Starting
import com.darrenthiores.efifalite.component.ChangeCard
import com.darrenthiores.efifalite.component.ChangePlayerList
import com.darrenthiores.efifalite.viewModel.ChangeViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ChangeScreen(
    id: Int,
    onBackClick: () -> Unit
) {
    val viewModel = getViewModel<ChangeViewModel>()
    val startingState = viewModel.getStartingById(id).collectAsState(
        initial = Starting(0,  0, "", 0, "", "", "", "", "", "", 0, "", 0.0, 0, 0)
    )
    val starting = startingState.value

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier.fillMaxHeight()
        ) {
            TextButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
            ) {
                Text(
                    text = "BACK"
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                ChangeCard(
                    name = starting.name,
                    photo = starting.photo, //.replace("%3A", ":").replace("%2F", "/"),
                    clubPhoto = starting.clubPhoto, // .replace("%3A", ":").replace("%2F", "/"),
                    position = starting.position,
                    rating = starting.rating
                )
            }
        }
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White.copy(alpha = 0.12f).compositeOver(Color.Black)
        ) {
            val players = viewModel.getPlayersByPosition(starting.position).collectAsState(
                initial = emptyList()
            )
            ChangePlayerList(
                players = players.value,
                onChange = {
                    if(starting.playerId != 0) {
                        val newStarting = starting.copy(
                            photo = starting.photo.replace("%3A", ":").replace("%2F", "/"),
                            clubPhoto = starting.clubPhoto.replace("%3A", ":").replace("%2F", "/")
                        )
                        viewModel.onPlayerChange(newStarting, it, newStarting.place)
                    }
                    // onBackClick()
                },
                startingId = starting.playerId,
                isExist = { playerId ->
                    viewModel.checkStartingExist(playerId)
                }
            )
        }
    }
}