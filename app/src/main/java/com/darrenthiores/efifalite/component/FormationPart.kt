package com.darrenthiores.efifalite.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.darrenthiores.core.model.presenter.Starting

@Composable
fun OneMid(
    modifier: Modifier = Modifier,
    player: Starting,
    onChange: (Starting) -> Unit,
    onLevelUp: (Starting) -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth()
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Item(
            starting = player,
            onChange = onChange,
            onLevelUp = onLevelUp
        )
    }
}

@Composable
fun TwoFar(
    modifier: Modifier = Modifier,
    left: Starting,
    right: Starting,
    onChange: (Starting) -> Unit,
    onLevelUp: (Starting) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Item(
            starting = left,
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        Item(
            starting = right,
            onChange = onChange,
            onLevelUp = onLevelUp
        )
    }
}

@Composable
fun TwoMid(
    modifier: Modifier = Modifier,
    left: Starting,
    right: Starting,
    onChange: (Starting) -> Unit,
    onLevelUp: (Starting) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Item(
            modifier = modifier.padding(horizontal = 24.dp),
            starting = left,
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        Item(
            modifier = modifier.padding(horizontal = 24.dp),
            starting = right,
            onChange = onChange,
            onLevelUp = onLevelUp
        )
    }
}

@Composable
private fun Item(
    modifier: Modifier = Modifier,
    starting: Starting,
    onChange: (Starting) -> Unit,
    onLevelUp: (Starting) -> Unit
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    if(showDialog) {
        PlayerDetailDialog(
            onDismiss = { showDialog = false },
            name = starting.name,
            age = starting.age,
            nationality = starting.nationality,
            height = starting.height,
            weight = starting.weight,
            photo = starting.photo,
            club = starting.club,
            clubPhoto = starting.clubPhoto,
            position = starting.position,
            rating = starting.rating,
            level = starting.level
        ) {
            Row(
                modifier = Modifier.padding(vertical = 12.dp)
            ) {
                OutlinedButton(
                    onClick = { onChange(starting) },
                    modifier = Modifier.padding(horizontal = 6.dp)
                ) {
                    Text(text = "CHANGE")
                }
                OutlinedButton(
                    onClick = { onLevelUp(starting) },
                    modifier = Modifier.padding(horizontal = 6.dp)
                ) {
                    Text(text = "LEVEL UP")
                }
            }
        }
    }

    Image(
        painter = rememberImagePainter(starting.photo),
        contentDescription = starting.name,
        modifier = modifier
            .clickable { showDialog = true }
            .width(48.dp)
            .height(48.dp)
            .clip(RoundedCornerShape(4.dp))
    )
}