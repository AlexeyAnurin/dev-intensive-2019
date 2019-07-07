package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")?.filter { !it.isBlank() }
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return (if (firstName.isNullOrEmpty()) null else firstName) to
                (if (lastName.isNullOrEmpty()) null else lastName)
    }


    fun toInitials(firstName: String?, lastName: String?): String? = when {
        (firstName == null || firstName.isBlank()) && (lastName == null || lastName.isBlank()) -> null
        (firstName == null || firstName.isBlank()) && lastName != null -> lastName.trimStart().first().toString().toUpperCase()
        firstName != null && (lastName == null || lastName.isBlank()) -> firstName.trimStart().first().toString().toUpperCase()
        else -> (firstName!!.trimStart().first().toString() + lastName!!.trimStart().first()).toUpperCase()
    }


    fun transliteration(payload: String, divider: String = " "): String {
        val abcCyr = charArrayOf(
                'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т',
                'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я',
                'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т',
                'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я'
        )
        val abcLat = arrayOf(
                "a", "b", "v", "g", "d", "e", "e", "zh", "z", "i", "i", "k", "l", "m", "n", "o", "p", "r", "s", "t",
                "u", "f", "h", "c", "ch", "sh", "sh'", "", "i", "", "e", "yu", "ya",
                "A", "B", "V", "G", "D", "E", "E", "Zh", "Z", "I", "I", "K", "L", "M", "N", "O", "P", "R", "S", "T",
                "U", "F", "H", "C", "Ch", "Sh", "Sh'", "", "I", "", "E", "Yu", "Ya"
        )
            val stringBuilder = StringBuilder()
        for (i in 0 until payload.length) {
            for (j in abcCyr.indices)
                if (payload[i] == abcCyr[j]) {
                    stringBuilder.append(abcLat[j])
                    break
                } else if (j == abcCyr.size - 1) stringBuilder.append(payload[i])
        }
        val str = stringBuilder.toString()
        return if (divider != " ") str.replace(" ", divider) else str
    }

}






    /*fun parseFullName(fullName: String?): Pair<String?, String?> {

        if (fullName != null) {
            if (fullName.trim().isEmpty()) {
                return Pair("null", "null")
            }
        }

        val parts: List<String>? = fullName?.trim()?.split(" ")


        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return when {
            firstName == null -> Pair("null", lastName)
            lastName == null -> Pair(firstName, "null")

            else -> return Pair(firstName, lastName)
        }*/
