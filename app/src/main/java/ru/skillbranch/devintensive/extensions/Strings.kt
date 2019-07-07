package ru.skillbranch.devintensive.extensions

/**
 * Необходимо реализовать метод truncate, усекающий исходную строку до указанного числа символов
 * и добавляющий заполнитель "..." в конец строки
 *
 * Реализуй extension, усекающий исходную строку до указанного числа символов (по умолчанию 16)
 * и возвращающий усеченную строку с заполнителем "..." (если строка была усечена)
 * если последний символ усеченной строки является пробелом - удалить его и добавить заполнитель
 * Пример:
 * "Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate() //Bender Bending R...
 * "Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate(15) //Bender Bending...
 * "A     ".truncate(3) //A
 */
fun String.truncate(lengthValue : Int = 16) : String {
    val string = this.trim( )
    return when(string.length){
        in 0..lengthValue ->{
            string.trimEnd()
        }
        lengthValue -> {
            string
        }
        else -> {
            val subStr = string.substring(0, lengthValue)
            if (subStr[subStr.length-1].toString() == " ")
                "${subStr.trimEnd()}..."
            else
                "$subStr..."
        }
    }
}

fun String.stripHtml() : String{
    var removedHtml = "<(.*?)>".toRegex().replace(this, "")
    var removedHtmlEscape = """(&amp;|&lt;|&gt;|&#39;|&quot;)""".toRegex().replace(removedHtml, "") //&amp;|&lt;|&gt;|&#39;|&quot;
    return  "\\s\\s+".toRegex().replace(removedHtmlEscape, " ")
}

