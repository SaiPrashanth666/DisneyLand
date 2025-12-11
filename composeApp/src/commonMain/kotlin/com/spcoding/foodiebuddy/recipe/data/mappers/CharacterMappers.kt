package com.spcoding.foodiebuddy.recipe.data.mappers

import com.spcoding.foodiebuddy.recipe.data.database.CharacterEntity
import com.spcoding.foodiebuddy.recipe.data.dto.disney.CharacterDto
import com.spcoding.foodiebuddy.recipe.domain.Character

fun CharacterDto.toCharacter() : Character {
    println("$imageUrl")
    return Character(
        id = id,
        name = name,
        films = films,
        shortFilms = shortFilms,
        tvShows = tvShows,
        videoGames = videoGames,
        imageUrl = imageUrl
    )
}

fun CharacterEntity.toCharacter() : Character {
    return Character(
        id = id,
        name = name,
        films = films,
        shortFilms = shortFilms,
        tvShows = tvShows,
        videoGames = videoGames,
        imageUrl = imageUrl
    )
}

fun Character.toCharacterEntity() : CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        films = films,
        shortFilms = shortFilms,
        tvShows = tvShows,
        videoGames = videoGames,
        imageUrl = imageUrl
    )
}