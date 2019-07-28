package ru.skillbranch.devintensive.repositories

//object в Kotlin - это SingleTon. Экземпляр в единственном числе, ленивая инициализация
object PreferencesRepository {

    fun getProfileData()

    fun getProfile(): Profile? {

    }
}