package ru.skillbranch.devintensive.models

// класс чтобы данные из PreferencesRepository доставлять в UI интерфейс
data class Profile (
        val rank: String = "Junior Android Developer",
        val firstName: String,
        val lastName: String,
        val about: String,
        val repository: String,
        val rating: Int = 0,
        val respect: Int = 0
) {

    val nickName: String = "John Doe" // TODO time 2.02
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