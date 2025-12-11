package com.spcoding.foodiebuddy.recipe.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [CharacterEntity::class],
    version = 1
)
@TypeConverters(
    ListStringTypeConverter::class
)
@ConstructedBy(DatabaseConstructor::class)
abstract class FavoriteCharacterDatabase : RoomDatabase(){

    abstract val characterDao : CharacterDao

    companion object {
        const val DB_NAME = "character.db"
    }
}