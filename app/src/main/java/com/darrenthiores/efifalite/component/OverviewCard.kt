package com.darrenthiores.efifalite.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.darrenthiores.core.model.presenter.Starting

@Composable
fun <T> OverviewCard(
    modifier: Modifier = Modifier,
    title: String,
    onClickSeeAll: () -> Unit,
    data: List<T>,
    row: @Composable (T) -> Unit
) {
    Card(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Column {
            Text(
                text = title,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.subtitle1
            )
            OverviewDivider()
            Column(Modifier.padding(start = 16.dp, top = 4.dp, end = 8.dp)) {
                data.take(3).forEach { row(it) }
                SeeAllButton(
                    modifier = Modifier.clearAndSetSemantics {
                        contentDescription = "All $title"
                    },
                    onClick = onClickSeeAll,
                )
            }
        }
    }
}

@Composable
fun StartingCard(
    modifier: Modifier = Modifier,
    data: List<Starting>,
    onClickSeeAll: () -> Unit
) {
    OverviewCard(
        modifier = modifier,
        title = "Starting LineUp",
        onClickSeeAll = onClickSeeAll,
        data = data
    ) {
        StartingRow(
            starting = it
        )
    }
}

@Composable
fun LoginCard(
    modifier: Modifier = Modifier,
    isLogin: Boolean,
    collectLogin: () -> Unit,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if(showDialog && isLogin) {
        LoginDialog(
            bodyText = "Hello! How is today? Hope you have a good day!, Don't forget claim your daily login reward mate!",
            buttonText = "DISMISS",
            onDismiss = onDismiss
        )
    }

    Card(
        modifier = modifier
    ) {
        Column {
            Text(
                text = "Daily Login",
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.subtitle1
            )
            OverviewDivider(modifier = Modifier.padding(horizontal = 12.dp))
            Row(
                modifier = Modifier.padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.weight(1f),
                    text = if(isLogin) "You can collect your login rewards! Collect it soon!" else "You have collected your login reward today, please wait until tomorrow"
                )
                IconButton(
                    onClick = { if(isLogin) collectLogin() },
                    modifier = Modifier
                        .align(Alignment.Top)
                ) {
                    val tintColor by animateColorAsState(targetValue = if(isLogin) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground)
                    Icon(
                        Icons.Filled.CalendarToday,
                        contentDescription = "Collect Login",
                        tint = tintColor
                    )
                }
            }
        }
    }
}

@Composable
fun OverviewDivider(
    modifier: Modifier = Modifier
) {
    Divider(color = MaterialTheme.colors.background, thickness = 1.dp, modifier = modifier)
}

@Composable
private fun SeeAllButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
            .height(44.dp)
            .fillMaxWidth()
    ) {
        Text("SEE ALL")
    }
}