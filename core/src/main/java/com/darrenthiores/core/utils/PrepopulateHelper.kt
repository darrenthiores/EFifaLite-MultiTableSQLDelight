package com.darrenthiores.core.utils

import android.content.ContentValues
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.transaction
import efifalitecore.playerdb.StartingEntity
import efifalitecore.playerdb.UserPlayerEntity
import net.sqlcipher.database.SQLiteDatabase
import java.util.concurrent.Executors

object PrepopulateHelper {

    fun fillWithStartingData(db: SupportSQLiteDatabase) {
        Executors.newSingleThreadExecutor().execute {
            db.transaction {
                startingList().forEach { starting ->
                    db.insert(
                        "StartingEntity",
                        SQLiteDatabase.CONFLICT_REPLACE,
                        ContentValues()
                            .apply {
                                put("id", starting.id)
                                put("playerId", starting.playerId)
                                put("name", starting.name)
                                put("age", starting.age)
                                put("nationality", starting.nationality)
                                put("height", starting.height)
                                put("weight", starting.weight)
                                put("photo", starting.photo)
                                put("club", starting.club)
                                put("clubPhoto", starting.clubPhoto)
                                put("league", starting.league)
                                put("position", starting.position)
                                put("rating", starting.rating)
                                put("level", starting.level)
                                put("place", starting.place)
                            }
                    )
                }
            }
        }
    }

    private fun startingList(): List<StartingEntity> {
        return listOf(
            StartingEntity(
                id = 1,
                playerId = 353865,
                name = "Victor Wehbi Aznar Ussen",
                age = 20,
                nationality = "Spain",
                height = "189 cm",
                weight = "null",
                photo = "https://media.api-sports.io/football/players/353865.png",
                club = "Cadiz",
                clubPhoto = "https://media.api-sports.io/football/teams/724.png",
                league = 140,
                position = "Goalkeeper",
                rating = 1.0,
                level = 1,
                place = 1
            ),
            StartingEntity(
                id = 2,
                playerId = 47380,
                name = "Cucurella",
                age = 24,
                nationality = "Spain",
                height = "172 cm",
                weight = "66 kg",
                photo = "https://media.api-sports.io/football/players/47380.png",
                club = "Getafe",
                clubPhoto = "https://media.api-sports.io/football/teams/546.png",
                league = 140,
                position = "Defender",
                rating = 6.900000,
                level = 1,
                place = 2
            ),
            StartingEntity(
                id = 3,
                playerId = 37,
                name = "N. Pérez",
                age = 22,
                nationality = "Argentina",
                height = "185 cm",
                weight = "75 kg",
                photo = "https://media.api-sports.io/football/players/37.png",
                club = "Atletico Madrid",
                clubPhoto = "https://media.api-sports.io/football/teams/530.png",
                league = 140,
                position = "Defender",
                rating = 1.0,
                level = 1,
                place = 3
            ),
            StartingEntity(
                id = 4,
                playerId = 2041,
                name = "J. Gnagnon",
                age = 25,
                nationality = "France",
                height = "182 cm",
                weight = "89 kg",
                photo = "https://media.api-sports.io/football/players/2041.png",
                club = "Sevilla",
                clubPhoto = "https://media.api-sports.io/football/teams/536.png",
                league = 140,
                position = "Defender",
                rating = 1.0,
                level = 1,
                place = 4
            ),
            StartingEntity(
                id = 5,
                playerId = 169,
                name = "Kieran Trippier",
                age = 32,
                nationality = "England",
                height = "178 cm",
                weight = "71 kg",
                photo = "https://media.api-sports.io/football/players/169.png",
                club = "Atletico Madrid",
                clubPhoto ="https://media.api-sports.io/football/teams/530.png" ,
                league = 140,
                position = "Defender",
                rating = 6.857142,
                level = 1,
                place = 5
            ),
            StartingEntity(
                id = 6,
                playerId = 48,
                name = "Saúl",
                age = 28,
                nationality = "Spain",
                height = "184 cm",
                weight = "76 kg",
                photo = "https://media.api-sports.io/football/players/48.png",
                club = "Atletico Madrid",
                clubPhoto = "https://media.api-sports.io/football/teams/530.png",
                league = 140,
                position = "Midfielder",
                rating = 7.233333,
                level = 1,
                place = 6
            ),
            StartingEntity(
                id = 7,
                playerId = 46685,
                name = "R. Azeez",
                age = 29,
                nationality = "Nigeria",
                height = "169 cm",
                weight = "67 kg",
                photo = "https://media.api-sports.io/football/players/46685.png",
                club = "Granada CF",
                clubPhoto = "https://media.api-sports.io/football/teams/715.png",
                league = 140,
                position = "Midfielder",
                rating = 1.0,
                level = 1,
                place = 7
            ),
            StartingEntity(
                id = 8,
                playerId = 47061,
                name = "Jorge Pombo",
                age = 28,
                nationality = "Spain",
                height = "177 cm",
                weight = "76 kg",
                photo = "https://media.api-sports.io/football/players/47061.png",
                club = "Cadiz",
                clubPhoto = "https://media.api-sports.io/football/teams/724.png",
                league = 140,
                position = "Midfielder",
                rating = 1.0,
                level = 1,
                place = 8
            ),
            StartingEntity(
                id = 9,
                playerId = 642,
                name = "S. Agüero",
                age = 33,
                nationality = "Argentina",
                height = "173 cm",
                weight = "70 kg",
                photo = "https://media.api-sports.io/football/players/642.png",
                club = "Barcelona",
                clubPhoto = "https://media.api-sports.io/football/teams/529.png",
                league = 140,
                position = "Attacker",
                rating = 6.900000,
                level = 1,
                place = 9
            ),
            StartingEntity(
                id = 10,
                playerId = 32871,
                name = "Taichi Hara",
                age = 23,
                nationality = "Japan",
                height = "191 cm",
                weight = "84 kg",
                photo = "https://media.api-sports.io/football/players/32871.png",
                club = "Alaves",
                clubPhoto = "https://media.api-sports.io/football/teams/542.png",
                league = 140,
                position = "Attacker",
                rating = 1.0,
                level = 1,
                place = 10
            ),
            StartingEntity(
                id = 11,
                playerId = 31563,
                name = "Aleksandar Trajkovski",
                age = 30,
                nationality = "North Macedonia",
                height = "179 cm",
                weight = "72 kg",
                photo = "https://media.api-sports.io/football/players/31563.png",
                club = "Mallorca",
                clubPhoto = "https://media.api-sports.io/football/teams/798.png",
                league = 140,
                position = "Attacker",
                rating = 1.0,
                level = 1,
                place = 11
            )
        )
    }

    fun fillWithUserPlayer(db: SupportSQLiteDatabase) {
        Executors.newSingleThreadExecutor().execute {
            db.transaction {
                userPlayerList().forEach { player ->
                    db.insert(
                        "UserPlayerEntity",
                        SQLiteDatabase.CONFLICT_REPLACE,
                        ContentValues()
                            .apply {
                                putNull("id")
                                put("playerId", player.playerId)
                                put("name", player.name)
                                put("age", player.age)
                                put("nationality", player.nationality)
                                put("height", player.height)
                                put("weight", player.weight)
                                put("photo", player.photo)
                                put("club", player.club)
                                put("clubPhoto", player.clubPhoto)
                                put("league", player.league)
                                put("position", player.position)
                                put("rating", player.rating)
                                put("level", player.level)
                            }
                    )
                }
            }
        }
    }

    private fun userPlayerList(): List<UserPlayerEntity> {
        return listOf(
            UserPlayerEntity(
                id = 0,
                playerId = 1438,
                name = "Bernd Leno",
                age = 30,
                nationality = "Germany",
                height = "190 cm",
                weight = "83 kg",
                photo = "https://media.api-sports.io/football/players/1438.png",
                club = "Arsenal",
                clubPhoto = "https://media.api-sports.io/football/teams/42.png",
                league = 39,
                position = "Goalkeeper",
                rating = 6.650000,
                level = 1
            ),
            UserPlayerEntity(
                id = 0,
                playerId = 1117,
                name = "Kieran Tierney",
                age = 25,
                nationality = "Scotland",
                height = "178 cm",
                weight = "70 kg",
                photo = "https://media.api-sports.io/football/players/1117.png",
                club = "Arsenal",
                clubPhoto = "https://media.api-sports.io/football/teams/42.png",
                league = 39,
                position = "Defender",
                rating = 6.963636,
                level = 1
            ),
            UserPlayerEntity(
                id = 0,
                playerId = 18800,
                name = "Marc Navarro",
                age = 26,
                nationality = "Spain",
                height = "188 cm",
                weight = "79 kg",
                photo = "https://media.api-sports.io/football/players/18800.png",
                club = "Watford",
                clubPhoto = "https://media.api-sports.io/football/teams/38.png",
                league = 39,
                position = "Defender",
                rating = 1.0,
                level = 1
            ),
            UserPlayerEntity(
                id = 0,
                playerId = 19350,
                name = "Luka Račić",
                age = 23,
                nationality = "Denmark",
                height = "187 cm",
                weight = "74 kg",
                photo = "https://media.api-sports.io/football/players/19350.png",
                club = "Brentford",
                clubPhoto = "https://media.api-sports.io/football/teams/55.png",
                league = 39,
                position = "Defender",
                rating = 1.0,
                level = 1
            ),
            UserPlayerEntity(
                id = 0,
                playerId = 19959,
                name = "B. White",
                age = 25,
                nationality = "England",
                height = "185 cm",
                weight = "78 kg",
                photo = "https://media.api-sports.io/football/players/19959.png",
                club = "Arsenal",
                clubPhoto ="https://media.api-sports.io/football/teams/42.png" ,
                league = 39,
                position = "Defender",
                rating = 6.937500,
                level = 1
            ),
            UserPlayerEntity(
                id = 0,
                playerId = 2795,
                name = "G. Sigurðsson",
                age = 33,
                nationality = "Iceland",
                height = "186 cm",
                weight = "79 kg",
                photo = "https://media.api-sports.io/football/players/2795.png",
                club = "Everton",
                clubPhoto = "https://media.api-sports.io/football/teams/2795.png",
                league = 39,
                position = "Midfielder",
                rating = 1.0,
                level = 1
            ),
            UserPlayerEntity(
                id = 0,
                playerId = 138919,
                name = "Thomas Dickson-Peters",
                age = 20,
                nationality = "Scotland",
                height = "172 cm",
                weight = "null",
                photo = "https://media.api-sports.io/football/players/138919.png",
                club = "Norwich",
                clubPhoto = "https://media.api-sports.io/football/teams/71.png",
                league = 39,
                position = "Midfielder",
                rating = 1.0,
                level = 1
            ),
            UserPlayerEntity(
                id = 0,
                playerId = 181420,
                name = "Jaime Alberto Alvarado Hoyos",
                age = 23,
                nationality = "Colombia",
                height = "null",
                weight = "null",
                photo = "https://media.api-sports.io/football/players/181420.png",
                club = "Watford",
                clubPhoto = "https://media.api-sports.io/football/teams/39.png",
                league = 39,
                position = "Midfielder",
                rating = 1.0,
                level = 1
            ),
            UserPlayerEntity(
                id = 0,
                playerId = 138786,
                name = "Ellis Reco Simms",
                age = 21,
                nationality = "England",
                height = "183 cm",
                weight = "73 kg",
                photo = "https://media.api-sports.io/football/players/138786.png",
                club = "Everton",
                clubPhoto = "https://media.api-sports.io/football/teams/45.png",
                league = 39,
                position = "Attacker",
                rating = 6.700000,
                level = 1
            ),
            UserPlayerEntity(
                id = 0,
                playerId = 20126,
                name = "J. Sinclair",
                age = 25,
                nationality = "England",
                height = "181 cm",
                weight = "78 kg",
                photo = "https://media.api-sports.io/football/players/20126.png",
                club = "Watford",
                clubPhoto = "https://media.api-sports.io/football/teams/38.png",
                league = 39,
                position = "Attacker",
                rating = 1.0,
                level = 1
            ),
            UserPlayerEntity(
                id = 0,
                playerId = 301,
                name = "Benjamin Luke Woodburn",
                age = 23,
                nationality = "Wales",
                height = "174 cm",
                weight = "72 kg",
                photo = "https://media.api-sports.io/football/players/301.png",
                club = "Liverpool",
                clubPhoto = "https://media.api-sports.io/football/teams/40.png",
                league = 39,
                position = "Attacker",
                rating = 1.0,
                level = 1
            )
        )
    }

}