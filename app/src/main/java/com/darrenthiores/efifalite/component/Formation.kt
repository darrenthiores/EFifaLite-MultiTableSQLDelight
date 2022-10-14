package com.darrenthiores.efifalite.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.darrenthiores.core.model.presenter.Starting

/*
note: to positioning player i use their place a.k.a an integer which value will determine their place
1: GK
2: LB
3 & 4: CB
5: RB
6: DMF
7 & 8: CMF
9: ST
10: RW
11: LW

and since i sort the array then get the value one by one, to know the real position you need to
-1 of the place integer
 */

@Composable
fun Formation433(
    modifier: Modifier = Modifier,
    players: List<Starting>,
    onChange: (Starting) -> Unit,
    onLevelUp: (Starting) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        // Attacker
        OneMid(
            player = players[8],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        TwoFar(
            left = players[10],
            right = players[9],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        //Mid
        TwoMid(
            left = players[6],
            right = players[7],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        OneMid(
            player = players[5],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        // Back
        TwoFar(
            left = players[1],
            right = players[4],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        TwoMid(
            left = players[2],
            right = players[3],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        // GoalKeeper
        OneMid(
            player = players[0],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
    }
}

@Composable
fun Formation4213(
    modifier: Modifier = Modifier,
    players: List<Starting>,
    onChange: (Starting) -> Unit,
    onLevelUp: (Starting) -> Unit
) {
    val sortedPlayers = players.sortedBy { it.place }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        // Attacker
        OneMid(
            player = sortedPlayers[8],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        TwoFar(
            left = sortedPlayers[10],
            right = sortedPlayers[9],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        //Mid
        OneMid(
            player = sortedPlayers[7],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        TwoMid(
            left = sortedPlayers[6],
            right = sortedPlayers[5],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        // Back
        TwoFar(
            left = sortedPlayers[1],
            right = sortedPlayers[4],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        TwoMid(
            left = sortedPlayers[2],
            right = sortedPlayers[3],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        // GoalKeeper
        OneMid(
            player = sortedPlayers[0],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
    }
}

@Composable
fun Formation442(
    modifier: Modifier = Modifier,
    players: List<Starting>,
    onChange: (Starting) -> Unit,
    onLevelUp: (Starting) -> Unit
) {
    val sortedPlayers = players.sortedBy { it.place }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        // Attacker
        TwoMid(
            left = sortedPlayers[8],
            right = sortedPlayers[9],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        //Mid
        TwoFar(
            left = sortedPlayers[10],
            right = sortedPlayers[7],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        TwoMid(
            left = sortedPlayers[6],
            right = sortedPlayers[5],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        // Back
        TwoFar(
            left = sortedPlayers[1],
            right = sortedPlayers[4],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        TwoMid(
            left = sortedPlayers[2],
            right = sortedPlayers[3],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
        // GoalKeeper
        OneMid(
            player = sortedPlayers[0],
            onChange = onChange,
            onLevelUp = onLevelUp
        )
    }
}