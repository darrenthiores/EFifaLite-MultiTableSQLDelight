package com.darrenthiores.efifalite.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.darrenthiores.core.model.presenter.Player
import timber.log.Timber

private inline fun <T : Any> LazyGridScope.items(
    items: LazyPagingItems<T>,
    noinline key: ((item: T) -> Any)? = null,
    crossinline itemContent: @Composable LazyGridItemScope.(item: T?) -> Unit
) {
    items(
        key = if (key != null) { index: Int -> items[index]?.let(key) ?: index } else null,
        count = items.itemCount
    ) { index ->
        itemContent(items[index])
    }
}

@Composable
fun PlayerList(
    modifier: Modifier = Modifier,
    players: LazyPagingItems<Player>
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
        ) { _player ->
            val player = _player ?: Player(0,  "", 0, "", "", "", "", "", "",0, "", 0.0)
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
                level = 1,
                button = {  }
            )
        }

        players.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                }
                loadState.append is LoadState.Loading -> {
                }
                loadState.refresh is LoadState.Error -> {
                    val e = players.loadState.refresh as LoadState.Error
                    Timber.e(e.error)
                }
                loadState.append is LoadState.Error -> {
                    val e = players.loadState.append as LoadState.Error
                    Timber.e(e.error)
                }
            }
        }
    }
}