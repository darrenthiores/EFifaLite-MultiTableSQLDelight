package com.darrenthiores.efifalite.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.darrenthiores.core.utils.FormationList

@Composable
fun FormationButton(
    modifier: Modifier = Modifier,
    formation: Int,
    onItemClicked: (Int) -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    FormationButton(
        modifier = modifier,
        expanded = isExpanded,
        formation = formation,
        onDropDownClicked = { isExpanded = true },
        onDismiss = { isExpanded = false },
        onItemClicked = onItemClicked
    )
}

@Composable
private fun FormationButton(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    formation: Int,
    onDropDownClicked: () -> Unit,
    onDismiss: () -> Unit,
    onItemClicked: (Int) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        OutlinedButton(onClick = onDropDownClicked) {
            Text(text = "$formation")
            Icon(
                imageVector = if(expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = "Choose Formation",
                modifier = Modifier.padding(all = 8.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss,
            modifier = Modifier
                .width(156.dp)
                .padding(all = 4.dp)
        ) {
            FormationList.formations().forEach {
                DropdownMenuItem(onClick = {
                    onDismiss()
                    onItemClicked(it)
                }) {
                    Text(
                        text = it.toString(),
                        color = if (it == formation) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
                    )
                }
                Divider()
            }
        }
    }
}