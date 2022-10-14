package com.darrenthiores.core.utils

import com.darrenthiores.core.model.data.response.PlayerResponse
import com.darrenthiores.core.model.domain.PlayerDomain
import com.darrenthiores.core.model.domain.StartingDomain
import com.darrenthiores.core.model.domain.UserPlayerDomain
import com.darrenthiores.core.model.presenter.Player
import com.darrenthiores.core.model.presenter.Starting
import com.darrenthiores.core.model.presenter.UserPlayer
import efifalitecore.playerdb.PlayerEntity
import efifalitecore.playerdb.StartingEntity
import efifalitecore.playerdb.UserPlayerEntity

object DataMapper {

    // map entity to domain

    fun mapPlayerEntityToDomain(input: PlayerEntity): PlayerDomain =
        PlayerDomain(
            id = input.id,
            name = input.name,
            age = input.age,
            nationality = input.nationality,
            height = input.height,
            weight = input.weight,
            photo = input.photo,
            club = input.club,
            clubPhoto = input.clubPhoto,
            league = input.league,
            position = input.position,
            rating = input.rating
        )

    fun mapUserPlayerEntityToDomain(input: List<UserPlayerEntity>): List<UserPlayerDomain> =
        input.map {
            UserPlayerDomain(
                id = it.id,
                playerId = it.playerId,
                name = it.name,
                age = it.age,
                nationality = it.nationality,
                height = it.height,
                weight = it.weight,
                photo = it.photo,
                club = it.club,
                clubPhoto = it.clubPhoto,
                league = it.league,
                position = it.position,
                rating = it.rating,
                level = it.level
            )
        }

    fun mapStartingEntityToDomain(input: StartingEntity): StartingDomain =
        StartingDomain(
            id = input.id,
            playerId = input.playerId,
            name = input.name,
            age = input.age,
            nationality = input.nationality,
            height = input.height,
            weight = input.weight,
            photo = input.photo,
            club = input.club,
            clubPhoto = input.clubPhoto,
            league = input.league,
            position = input.position,
            rating = input.rating,
            level = input.level,
            place = input.place
        )

    fun mapStartingEntitiesToDomain(input: List<StartingEntity>): List<StartingDomain> =
        input.map {
            StartingDomain(
                id = it.id,
                playerId = it.playerId,
                name = it.name,
                age = it.age,
                nationality = it.nationality,
                height = it.height,
                weight = it.weight,
                photo = it.photo,
                club = it.club,
                clubPhoto = it.clubPhoto,
                league = it.league,
                position = it.position,
                rating = it.rating,
                level = it.level,
                place = it.place
            )
        }

    // map domain to entity
//
//    fun mapPlayerToUserPlayerEntity(input: PlayerDomain): UserPlayerEntity =
//        UserPlayerEntity(
//            id = 0,
//            playerId = input.id,
//            name = input.name,
//            age = input.age,
//            nationality = input.nationality,
//            height = input.height,
//            weight = input.weight,
//            photo = input.photo,
//            club = input.club,
//            clubPhoto = input.clubPhoto,
//            league = input.league,
//            position = input.position,
//            rating = input.rating,
//            level = 1
//        )

    fun mapUserPlayerToStartingEntity(input: UserPlayerDomain, place: Int): StartingEntity =
        StartingEntity(
            id = input.id,
            playerId = input.playerId,
            name = input.name,
            age = input.age,
            nationality = input.nationality,
            height = input.height,
            weight = input.weight,
            photo = input.photo,
            club = input.club,
            clubPhoto = input.clubPhoto,
            league = input.league,
            position = input.position,
            rating = input.rating,
            level = input.level,
            place = place
        )

    fun mapStartingToUserPlayerEntity(input: StartingDomain): UserPlayerEntity =
        UserPlayerEntity(
            id = input.id,
            playerId = input.playerId,
            name = input.name,
            age = input.age,
            nationality = input.nationality,
            height = input.height,
            weight = input.weight,
            photo = input.photo,
            club = input.club,
            clubPhoto = input.clubPhoto,
            league = input.league,
            position = input.position,
            rating = input.rating,
            level = input.level
        )

    fun mapStartingToEntity(input: StartingDomain): StartingEntity =
        StartingEntity(
            id = input.id,
            playerId = input.playerId,
            name = input.name,
            age = input.age,
            nationality = input.nationality,
            height = input.height,
            weight = input.weight,
            photo = input.photo,
            club = input.club,
            clubPhoto = input.clubPhoto,
            league = input.league,
            position = input.position,
            rating = input.rating,
            level = input.level,
            place = input.place
        )

    fun mapUserPlayerToEntity(input: UserPlayerDomain): UserPlayerEntity =
        UserPlayerEntity(
            id = input.id,
            playerId = input.playerId,
            name = input.name,
            age = input.age,
            nationality = input.nationality,
            height = input.height,
            weight = input.weight,
            photo = input.photo,
            club = input.club,
            clubPhoto = input.clubPhoto,
            league = input.league,
            position = input.position,
            rating = input.rating,
            level = input.level
        )

    // map response to entity

    fun mapResponsesToEntities(input: PlayerResponse): PlayerEntity {
        val player = input.player
        val statistic = input.statistics[0]
        val rating = statistic.games.rating ?: "1.0"
        return PlayerEntity(
            remoteId = 0,
            id = player.id,
            name = player.name,
            age = player.age ?: 0,
            nationality = player.nationality ?: "null",
            height = player.height ?: "null",
            weight = player.weight ?: "null",
            photo = player.photo ?: "null",
            club = statistic.team.name ?: "null",
            clubPhoto = statistic.team.logo ?: "null",
            league = statistic.league.id,
            position = statistic.games.position ?: "null",
            rating = if(rating!="null") rating.toDouble() else 1.0
        )
    }

    // map response to domain
    fun mapResponseToDomain(input: PlayerResponse): PlayerDomain {
        val player = input.player
        val statistic = input.statistics[0]
        val rating = statistic.games.rating ?: "1.0"
        return PlayerDomain(
            id = player.id,
            name = player.name,
            age = player.age ?: 0,
            nationality = player.nationality ?: "null",
            height = player.height ?: "null",
            weight = player.weight ?: "null",
            photo = player.photo ?: "null",
            club = statistic.team.name ?: "null",
            clubPhoto = statistic.team.logo ?: "null",
            league = statistic.league.id,
            position = statistic.games.position ?: "null",
            rating = if(rating!="null") rating.toDouble() else 1.0
        )
    }

    // map domain to presenter

    fun mapPlayerToPresenter(input: PlayerDomain): Player =
        Player(
            id = input.id,
            name = input.name,
            age = input.age,
            nationality = input.nationality,
            height = input.height,
            weight = input.weight,
            photo = input.photo,
            club = input.club,
            clubPhoto = input.clubPhoto,
            league = input.league,
            position = input.position,
            rating = input.rating
        )

    fun mapUserPlayerToPresenter(input: List<UserPlayerDomain>): List<UserPlayer> =
        input.map {
            UserPlayer(
                id = it.id,
                playerId = it.playerId,
                name = it.name,
                age = it.age,
                nationality = it.nationality,
                height = it.height,
                weight = it.weight,
                photo = it.photo,
                club = it.club,
                clubPhoto = it.clubPhoto,
                league = it.league,
                position = it.position,
                rating = it.rating,
                level = it.level
            )
        }

    fun mapStartingsToPresenter(input: List<StartingDomain>): List<Starting> =
        input.map {
            Starting(
                id = it.id,
                playerId = it.playerId,
                name = it.name,
                age = it.age,
                nationality = it.nationality,
                height = it.height,
                weight = it.weight,
                photo = it.photo,
                club = it.club,
                clubPhoto = it.clubPhoto,
                league = it.league,
                position = it.position,
                rating = it.rating,
                level = it.level,
                place = it.place
            )
        }

    fun mapStartingToPresenter(input: StartingDomain): Starting =
        Starting(
            id = input.id,
            playerId = input.playerId,
            name = input.name,
            age = input.age,
            nationality = input.nationality,
            height = input.height,
            weight = input.weight,
            photo = input.photo,
            club = input.club,
            clubPhoto = input.clubPhoto,
            league = input.league,
            position = input.position,
            rating = input.rating,
            level = input.level,
            place = input.place
        )

    // map presenter to domain

//    fun mapPlayerPresenterToDomain(input: Player): PlayerDomain =
//        PlayerDomain(
//            id = input.id,
//            name = input.name,
//            age = input.age,
//            nationality = input.nationality,
//            height = input.height,
//            weight = input.weight,
//            photo = input.photo,
//            club = input.club,
//            clubPhoto = input.clubPhoto,
//            league = input.league,
//            position = input.position,
//            rating = input.rating
//        )

    fun mapUserPlayerPresenterToDomain(input: UserPlayer): UserPlayerDomain =
        UserPlayerDomain(
            id = input.id,
            playerId = input.playerId,
            name = input.name,
            age = input.age,
            nationality = input.nationality,
            height = input.height,
            weight = input.weight,
            photo = input.photo,
            club = input.club,
            clubPhoto = input.clubPhoto,
            league = input.league,
            position = input.position,
            rating = input.rating,
            level = input.level
        )

    fun mapStartingPresenterToDomain(input: Starting): StartingDomain =
        StartingDomain(
            id = input.id,
            playerId = input.playerId,
            name = input.name,
            age = input.age,
            nationality = input.nationality,
            height = input.height,
            weight = input.weight,
            photo = input.photo,
            club = input.club,
            clubPhoto = input.clubPhoto,
            league = input.league,
            position = input.position,
            rating = input.rating,
            level = input.level,
            place = input.place
        )

    // response to user player
    fun mapResponseToUserPlayerEntity(input: PlayerResponse): UserPlayerEntity {
        val player = input.player
        val statistic = input.statistics[0]
        val rating = statistic.games.rating ?: "1.0"
        return UserPlayerEntity(
            id = 0,
            playerId = player.id,
            name = player.name,
            age = player.age ?: 0,
            nationality = player.nationality ?: "null",
            height = player.height ?: "null",
            weight = player.weight ?: "null",
            photo = player.photo ?: "null",
            club = statistic.team.name ?: "null",
            clubPhoto = statistic.team.logo ?: "null",
            league = statistic.league.id,
            position = statistic.games.position ?: "null",
            rating = if(rating!="null") rating.toDouble() else 1.0,
            level = 1
        )
    }

    // map player to user player

//    fun mapPlayerToUserPlayer(input: PlayerDomain) =
//        UserPlayer(
//            id = 0,
//            playerId = input.id,
//            name = input.name,
//            age = input.age,
//            nationality = input.nationality,
//            height = input.height,
//            weight = input.weight,
//            photo = input.photo,
//            club = input.club,
//            clubPhoto = input.clubPhoto,
//            league = input.league,
//            position = input.position,
//            rating = input.rating,
//            level = 1
//        )
}