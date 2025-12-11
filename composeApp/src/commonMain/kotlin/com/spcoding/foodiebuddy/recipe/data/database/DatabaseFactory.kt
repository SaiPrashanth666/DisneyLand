package com.spcoding.foodiebuddy.recipe.data.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {

     fun create() : RoomDatabase.Builder<FavoriteCharacterDatabase>

}