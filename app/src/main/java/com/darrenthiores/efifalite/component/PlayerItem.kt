package com.darrenthiores.efifalite.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.darrenthiores.efifalite.R
import com.darrenthiores.efifalite.ui.theme.EFifaLiteTheme

@Composable
fun PlayerCard(
    modifier: Modifier = Modifier,
    name: String,
    age: Int,
    nationality: String,
    height: String,
    weight: String,
    photo: String,
    club: String,
    clubPhoto: String,
    position: String,
    rating: Double,
    level: Int,
    button: @Composable () -> Unit
) {
    var showDialog by remember {
        mutableStateOf(false)
    }

    if(showDialog) {
        PlayerDetailDialog(
            onDismiss = { showDialog = false },
            name = name,
            age = age,
            nationality = nationality,
            height = height,
            weight = weight,
            photo = photo,
            club = club,
            clubPhoto = clubPhoto,
            position = position,
            rating = rating,
            level = level,
            button = button
        )
    }

    BaseCard(
        modifier = modifier.clickable { showDialog = true },
        rating = rating,
        position = position,
        clubPhoto = clubPhoto,
        name = name,
        photo = photo
    )
}

@Composable
fun ChangeCard(
    modifier: Modifier = Modifier,
    name: String,
    photo: String,
    clubPhoto: String,
    position: String,
    rating: Double,
) {
    BaseCard(
        modifier = modifier,
        rating = rating,
        position = position,
        clubPhoto = clubPhoto,
        name = name,
        photo = photo
    )
}

@Composable
private fun BaseCard(
    modifier: Modifier = Modifier,
    rating: Double,
    position: String,
    clubPhoto: String,
    name: String,
    photo: String
) {
    Box(
        modifier = modifier
            .height(154.dp)
            .width(128.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.card_bg),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.2f
        )

        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text =  (rating * 10).toString().substringBefore("."),
                        style = MaterialTheme.typography.caption
                    )
                    Text(
                        text = getPosition(position),
                        style = MaterialTheme.typography.caption
                    )
                    Image(
                        painter = rememberImagePainter(clubPhoto),
                        contentDescription = "$name club logo",
                        modifier = Modifier.size(28.dp)
                    )
                }
                Image(
                    painter = rememberImagePainter(photo),
                    contentDescription = "$name photo",
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = name,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.caption
            )
            Star(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 4.dp),
                rating = rating
            )
        }
    }
}

private fun getPosition(position: String): String =
    when(position) {
        "Attacker" -> "ATK"
        "Midfielder" -> "MID"
        "Defender" -> "DEF"
        "Goalkeeper" -> "GK"
        else -> "NON"
    }

@Composable
private fun Star(
    modifier: Modifier = Modifier,
    rating: Double
) {
    Row(
        modifier = modifier
    ) {
        when {
            rating > 9.0 -> {
                YellowStar()
                YellowStar()
                YellowStar()
                YellowStar()
                YellowStar()
            }
            rating > 7.5 -> {
                YellowStar()
                YellowStar()
                YellowStar()
                YellowStar()
                RegularStar()
            }
            rating > 5.0 -> {
                YellowStar()
                YellowStar()
                YellowStar()
                RegularStar()
                RegularStar()
            }
            rating > 3.0 -> {
                YellowStar()
                YellowStar()
                RegularStar()
                RegularStar()
                RegularStar()
            }
            rating > 0.0 -> {
                YellowStar()
                RegularStar()
                RegularStar()
                RegularStar()
                RegularStar()
            }
        }
    }
}

@Composable
private fun YellowStar() {
    Icon(
        imageVector = Icons.Default.Star,
        contentDescription = null,
        tint = Color.Yellow,
        modifier = Modifier.size(16.dp)
    )
}

@Composable
private fun RegularStar() {
    Icon(
        imageVector = Icons.Default.Star,
        contentDescription = null,
        modifier = Modifier.size(16.dp)
    )
}

@Composable
@Preview
private fun PlayerCardPreview() {
    EFifaLiteTheme {
        PlayerCard(
            name = "Cucurellla",
            age = 24,
            nationality = "Spain",
            height = "172 cm",
            weight = "66 kg",
            photo = "https://media.api-sports.io/football/players/47380.png",
            club = "Getafe",
            clubPhoto = "https://media.api-sports.io/football/teams/546.png",
            position = "Defender",
            rating = 6.900000,
            level = 1,
            button = {  }
        )
    }
}