package com.spcoding.foodiebuddy.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object CharacterGraph : Route

    @Serializable
    data object CharacterList : Route

    @Serializable
    data class CharacterDetail(val id : Int) : Route
}