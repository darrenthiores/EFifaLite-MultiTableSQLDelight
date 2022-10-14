package com.darrenthiores.efifalite

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

enum class EFifaScreen(
    val icon: ImageVector
) {
    Overview(
        icon = Icons.Filled.PieChart
    ),
    LineUp(
        icon = Icons.Filled.Person
    ),
    Draw(
        icon = Icons.Filled.PersonAdd
    );

    companion object {
        fun fromRoute(route: String?): EFifaScreen =
            when (route?.substringBefore("/")) {
                Overview.name -> Overview
                LineUp.name -> LineUp
                Draw.name -> Draw
                null -> Overview
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}