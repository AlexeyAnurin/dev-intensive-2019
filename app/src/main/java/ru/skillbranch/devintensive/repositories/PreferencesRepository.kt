package ru.skillbranch.devintensive.repositories

import ru.skillbranch.devintensive.models.Profile

//object в Kotlin - это SingleTon. Экземпляр в единственном числе, ленивая инициализация
object PreferencesRepository {

    fun getProfileData()

    fun getProfile(): Profile? {

    }
}