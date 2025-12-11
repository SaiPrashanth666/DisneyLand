package com.spcoding.foodiebuddy.recipe.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object DatabaseConstructor : RoomDatabaseConstructor<FavoriteCharacterDatabase> {

    override fun initialize(): FavoriteCharacterDatabase
}