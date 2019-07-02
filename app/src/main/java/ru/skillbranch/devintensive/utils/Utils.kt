package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName:String?): Pair<String?, String?> {

        if (fullName != null) {
            if (fullName.trim().isEmpty()) {
                return Pair("null", "null")
            }
        }


        val parts :List<String>? = fullName?.trim()?.split(" ")


        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)


        return when {
            firstName == null -> Pair("null", lastName)
            lastName == null -> Pair(firstName, "null")

            else -> return Pair(firstName, lastName)
        }

     //   return Pair(firstName, lastName)

        //аналогичная запись ключ-значение
        // return firstName to lastName
    }
}