package com.darrenthiores.efifalite.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.darrenthiores.core.model.presenter.Starting

@Composable
fun StartingRow(
    modifier: Modifier = Modifier,
    starting: Starting,
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
            button = {  }
        )
    }

    BaseRow(
        modifier = modifier
            .padding(vertical = 8.dp)
            .clickable { showDialog = true },
        title = starting.name.substringAfter(". ").substringBefore(" "),
        subtitle = starting.position,
        playerPhoto = starting.photo,
        clubPhoto = starting.clubPhoto
    )
}

@Composable
private fun BaseRow(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    playerPhoto: String,
    clubPhoto: String
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .clearAndSetSemantics {
                contentDescription =
                    "$title is in your lineup as a $subtitle"
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        val typography = MaterialTheme.typography
        Image(
            painter = rememberImagePainter(playerPhoto),
            contentDescription =  "$title Photo",
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.primary, CircleShape)
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier) {
            Text(text = title, style = typography.subtitle2)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = subtitle, style = typography.caption)
            }
        }
        Spacer(Modifier.weight(1f))

        Image(
            painter = rememberImagePainter(clubPhoto),
            contentDescription = "$title's Club",
            modifier = Modifier.size(28.dp)
        )

        Spacer(Modifier.width(16.dp))

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp)
            )
        }
    }
    OverviewDivider()
}