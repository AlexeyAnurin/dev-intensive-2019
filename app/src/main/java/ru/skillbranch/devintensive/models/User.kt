package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
        val id: String,
        var firstName: String?,
        var lastName: String?,
        var avatar: String?,
        var rating: Int = 0,
        var respect: Int = 0,
        val lastVisit: Date? = Date(),
        val isOnline: Boolean = false
) {

    constructor(id: String, firstName: String?, lastName: String?) : this(
            id = id,
            firstName = firstName,
            lastName = lastName,
            avatar = null
    )

    constructor(id: String) : this(id, null, null)

    init {
        println("Its Alive!!! \n" +
                "${if (lastName !== null) "His name is $firstName $lastName" else "And his name is $firstName $lastName !!!"}\n")

        //  "${"And his name is $firstName $lastName !!!"}\n")
    }

    companion object Factory {
        private var lastId: Int = -1
        fun makeUser(fullName: String?): User {
            lastId++

            //   val parts :List<String>? =fullName?.split(" ")
            //   val firstName = parts?.getOrNull(0)
            //   val lastName = parts?.getOrNull(1)

            val (firstName, lastName) = Utils.parseFullName(fullName)

            return User(id = "$lastId", firstName = firstName, lastName = lastName)
        }

        fun makeUser(
                firstName: String?,
                lastName: String?,
                avatar: String?,
                rating: Int = 0,
                respect: Int = 0,
                lastVisit: Date? = Date(),
                isOnline: Boolean = false
        ): User {
            lastId++
            return User("$lastId", firstName, lastName, avatar, rating, respect, lastVisit, isOnline)
        }
    }

    class Builder {
        private var id: String? = null
        private var firstName: String? = null
        private var lastName: String? = null
        private var avatar: String? = null
        private var rating: Int = 0
        private var respect: Int = 0
        private var lastVisit: Date? = Date()
        private var isOnline: Boolean = false

        fun id(id: String) = apply { this.id = id }
        fun firstName(firstName: String?) = apply { this.firstName = firstName }
        fun lastName(lastName: String?) = apply { this.lastName = lastName }
        fun avatar(avatar: String?) = apply { this.avatar = avatar }
        fun rating(rating: Int) = apply { this.rating = rating }
        fun respect(respect: Int) = apply { this.respect = respect }
        fun lastVisit(lastVisit: Date?) = apply { this.lastVisit = lastVisit }
        fun isOnline(isOnline: Boolean) = apply { this.isOnline = isOnline }

        fun build() = if (id == null) makeUser(firstName, lastName, avatar, rating, respect, lastVisit, isOnline)
        else User(id!!, firstName, lastName, avatar, rating, respect, lastVisit, isOnline)
    }
}