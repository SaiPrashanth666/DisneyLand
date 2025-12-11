package com.spcoding.foodiebuddy.recipe.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class ListStringTypeConverter
{
    @TypeConverter
    fun fromString(value : String) : List<String> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromList(list : List<String>) : String {
        return Json.encodeToString(list)
    }
}