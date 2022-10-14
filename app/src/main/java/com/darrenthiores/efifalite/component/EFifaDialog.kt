package com.darrenthiores.efifalite.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberImagePainter
import com.darrenthiores.efifalite.ui.theme.EFifaLiteDialogTheme

@Composable
fun LoginDialog(
    bodyText: String,
    buttonText: String,
    onDismiss: () -> Unit
) {
    EFifaLiteDialogTheme {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = { Text(bodyText) },
            buttons = {
                Column {
                    Divider(
                        Modifier.padding(horizontal = 12.dp),
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
                    )
                    TextButton(
                        onClick = onDismiss,
                        shape = RectangleShape,
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(buttonText)
                    }
                }
            }
        )
    }
}

@Composable
fun PlayerDetailDialog(
    onDismiss: () -> Unit,
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
    level: Int = 1,
    button: @Composable () -> Unit
) {
    EFifaLiteDialogTheme {
        Dialog(
            onDismissRequest = onDismiss
        ) {
            Surface {
                Column(
                    modifier = Modifier.height(400.dp)
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close Dialog")
                    }
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .padding(bottom = 16.dp)
                            .weight(1f),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberImagePainter(photo),
                            contentDescription = "$name Photo",
                            modifier = Modifier.size(100.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(32.dp))
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            val style = MaterialTheme.typography.caption
                            Text(
                                text = "Name: $name",
                                modifier = Modifier.padding(4.dp),
                                style = style
                            )
                            Text(
                                text = "Age: $age",
                                modifier = Modifier.padding(4.dp),
                                style = style
                            )
                            Text(
                                text = "Nationality: $nationality",
                                modifier = Modifier.padding(4.dp),
                                style = style
                            )
                            Text(
                                text = "Height: $height",
                                modifier = Modifier.padding(4.dp),
                                style = style
                            )
                            Text(
                                text = "Weight: $weight",
                                modifier = Modifier.padding(4.dp),
                                style = style
                            )
                            Row {
                                Text(
                                    text = "Club: $club",
                                    modifier = Modifier.padding(4.dp),
                                    style = style
                                )
                                Image(
                                    painter = rememberImagePainter(clubPhoto),
                                    contentDescription = "$name Photo",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Text(
                                text = "Position: $position",
                                modifier = Modifier.padding(4.dp),
                                style = style
                            )
                            Text(
                                text = "Rating: ${(rating * 10).toString().substringBefore(".")}",
                                modifier = Modifier.padding(4.dp),
                                style = style
                            )
                            Text(
                                text = "Level: $level",
                                modifier = Modifier.padding(4.dp),
                                style = style
                            )
                        }
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        contentAlignment = Alignment.Center
                    ) {
                        button()
                    }
                }
            }
        }
    }
}