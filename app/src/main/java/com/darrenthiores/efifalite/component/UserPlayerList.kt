package com.darrenthiores.efifalite.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.darrenthiores.core.model.presenter.UserPlayer

@Composable
fun UserPlayerList(
    modifier: Modifier = Modifier,
    players: List<UserPlayer>,
    onLevelUp: (UserPlayer) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        items(
            items = players,
            key = { player -> player.id }
        ) { player ->
            PlayerCard(
                modifier = Modifier.semantics {
                    contentDescription = player.name
                },
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
                level = player.level
            ) {
                OutlinedButton(
                    onClick = { onLevelUp(player) },
                    modifier = modifier.padding(vertical = 12.dp)
                ) {
                    Text(text = "LEVEL UP")
                }
            }
        }
    }
}

@Composable
fun ChangePlayerList(
    modifier: Modifier = Modifier,
    players: List<UserPlayer>,
    onChange: (UserPlayer) -> Unit,
    startingId: Int,
    isExist: (Int) -> Boolean
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        items(
            items = players,
            key = { player -> player.id }
        ) { player ->
            val exist = isExist(player.playerId)
            ChangeCard(
                modifier = Modifier
                    .semantics {
                        contentDescription = player.name
                    }
                    .clickable {
                        if (!exist) {
                            onChange(player)
                        }
                        else {
                            if(player.playerId==startingId) {
                                onChange(player)
                            }
                        }
                    }
                    .background(
                        if (!exist) {
                            Color(0x00FFFFFF)
                        } else {
                            if(player.playerId==startingId) {
                                Color(0x00FFFFFF)
                            } else {
                                Color.Black
                            }
                        }
                    ),
                name = player.name,
                photo = player.photo,
                clubPhoto = player.clubPhoto,
                position = player.position,
                rating = player.rating,
            )
        }
    }
}