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
       println( "Its Alive!!! \n" +
                "${if(lastName!==null) "His name is $firstName $lastName" else "And his name is $firstName $lastName !!!"}\n")

              //  "${"And his name is $firstName $lastName !!!"}\n")
    }

    companion object Factory{
        private var lastId:Int=-1
        fun makeUser(fullName:String?): User {
            lastId++

            //   val parts :List<String>? =fullName?.split(" ")
            //   val firstName = parts?.getOrNull(0)
            //   val lastName = parts?.getOrNull(1)

            val (firstName, lastName) = Utils.parseFullName(fullName)

            return User(id = "$lastId", firstName = firstName, lastName = lastName )
        }
    }
}