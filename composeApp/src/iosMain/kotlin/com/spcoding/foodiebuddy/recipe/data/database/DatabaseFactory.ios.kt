@file:OptIn(ExperimentalForeignApi::class)

package com.spcoding.foodiebuddy.recipe.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<FavoriteCharacterDatabase> {
        val dbFile = documentDirectory() + "/${FavoriteCharacterDatabase.DB_NAME}"

        return Room.databaseBuilder<FavoriteCharacterDatabase>(
            dbFile
        )
    }

    private fun documentDirectory() : String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            create = false,
            appropriateForURL = null,
            error = null
        )

        return requireNotNull(documentDirectory?.path)
    }
}