package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils

// класс чтобы данные из PreferencesRepository доставлять в UI интерфейс
data class Profile (

        val firstName: String,
        val lastName: String,
        val about: String,
        val repository: String,
        val rating: Int = 0,
        val respect: Int = 0
) {

 //   val nickName: String = "John Doe"
    val nickName: String = Utils.transliteration("${firstName.trim()} ${lastName.trim()}".trim(), "_")
    val rank: String = "Junior Android Developer"

    //НЕ ПОНЯТНО
    fun toMap(): Map<String, Any> = mapOf(
                   "nickName" to nickName,
                   "rank" to rank,
                   "firstName" to firstName,
                   "lastName" to lastName,
                   "about" to about,
                   "repository" to repository,
                   "rating" to rating,
                   "respect" to respect
    )

}