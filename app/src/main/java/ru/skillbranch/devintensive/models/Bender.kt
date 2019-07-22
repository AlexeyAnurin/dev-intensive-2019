package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        if (question != Question.IDLE && !question.validation.matches(answer)) {
            return "${question.hint}\n${question.question}" to status.color
        }

        if (question == Question.IDLE) {
            return "На этом все, вопросов больше нет" to status.color
        }

        val nextQuestion = question.nextQuestion()
        val nextStatus = status.nextStatus()
        val text = if (question.answers.contains(answer.toLowerCase())) {
            question = nextQuestion
            if (nextQuestion == Question.IDLE) {
                "Отлично - ты справился\nНа этом все, вопросов больше нет"
            } else {
                "Отлично - ты справился\n${nextQuestion.question}"
            }
        } else {
            if (status == Status.CRITICAL) {
                question = Question.NAME
                status = Status.NORMAL
                "Это неправильный ответ. Давай все по новой\n${question.question}"
            } else {
                status = nextStatus
                "Это неправильный ответ\n${question.question}"
            }
        }

        return text to status.color
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>, val hint: String, val validation: Regex) {
        NAME(
                "Как меня зовут?",
                listOf("бендер", "bender"),
                "Имя должно начинаться с заглавной буквы",
                Regex("^[A-ZА-Я].*")
        ) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION(
                "Назови мою профессию?",
                listOf("сгибальщик", "bender"),
                "Профессия должна начинаться со строчной буквы",
                Regex("^[a-zа-я].*")
        ) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL(
                "Из чего я сделан?",
                listOf("металл", "дерево", "metal", "iron", "wood"),
                "Материал не должен содержать цифр",
                Regex("\\D+")
        ) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY(
                "Когда меня создали?",
                listOf("2993"),
                "Год моего рождения должен содержать только цифры",
                Regex("\\d+")
        ) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL(
                "Мой серийный номер?",
                listOf("2716057"),
                "Серийный номер содержит только цифры, и их 7",
                Regex("\\d{7}")
        ) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE(
                "На этом все, вопросов больше нет",
                listOf(),
                "",
                Regex("\\w+")
        ) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
    }
}


//JSLOdey 22.07.19
/*package ru.skillbranch.devintensive.models


class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    var counter: Int = 0

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        return if (answer.isByFormat()) {
            if (question.answers.contains(answer.toLowerCase())) {
                question = question.nextQuestion()
                "Отлично - ты справился\n${question.question}" to status.color
            } else {
                counter += 1
                status = status.nextStatus()

                if (counter >= 4) {
                    question = Question.NAME
                    counter = 0
                    "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
                } else {
                    "Это неправильный ответ\n${question.question}" to status.color
                }
            }
        } else {
            question.formatError() to status.color
        }
    }



    private fun String.isByFormat(): Boolean {
        return when (question) {
            Question.NAME -> this.contains(question.format)
            Question.PROFESSION -> this.contains(question.format)
            Question.MATERIAL -> !this.contains(question.format)
            Question.BDAY -> !this.contains(question.format)
            Question.SERIAL -> this.matches(question.format)
            Question.IDLE -> false
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
             //   values().last()
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>, val format: Regex, val formatAnswer: String) {
        NAME(
                "Как меня зовут?",
                listOf("бендер", "bender"),
                "^[A-Z]".toRegex(),
                "Имя должно начинаться с заглавной буквы"
        ) {
            override fun nextQuestion(): Question = PROFESSION
            override fun formatError(): String = "${this.formatAnswer}\n${this.question}"
        },
        PROFESSION(
                "Назови мою профессию?",
                listOf("сгибальщик", "bender"),
                "^[a-z]".toRegex(),
                "Профессия должна начинаться со строчной буквы"
        ) {
            override fun nextQuestion(): Question = MATERIAL
            override fun formatError(): String = "${this.formatAnswer}\n${this.question}"
        },
        MATERIAL(
                "Из чего я сделан?",
                listOf("металл", "дерево", "metal", "iron", "wood"),
                "\\d+".toRegex(),
                "Материал не должен содержать цифр"
        ) {
            override fun nextQuestion(): Question = BDAY
            override fun formatError(): String = "${this.formatAnswer}\n${this.question}"
        },
        BDAY(
                "Когда меня создали?",
                listOf("2993"),
                "\\D+".toRegex(),
                "Год моего рождения должен содержать только цифры"
        ) {
            override fun nextQuestion(): Question = SERIAL
            override fun formatError(): String = "${this.formatAnswer}\n${this.question}"
        },
        SERIAL(
                "Мой серийный номер?",
                listOf("2716057"),
                "^\\d\\d{5}\\d\$".toRegex(),
                "Серийный номер содержит только цифры, и их 7"
        ) {
            override fun nextQuestion(): Question = IDLE
            override fun formatError(): String = "${this.formatAnswer}\n${this.question}"
        },
        IDLE(
                "На этом все, вопросов больше нет",
                listOf(),
                ".*".toRegex(),
                ""
        ) {
            override fun nextQuestion(): Question = IDLE
            override fun formatError(): String = this.question
        };

        abstract fun nextQuestion(): Question
        abstract fun formatError(): String
    }
}*/

//JLSOdey
/*package ru.skillbranch.devintensive.models

import android.util.Log


class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    var counter: Int = 0

    fun askQuestion(): String = when (question) {


        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    //answer в fun listenAnswer - то, что я ввожу в поле ввода messageEt
    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
*//*if (answer.first() == 'a') {
}*//*
        if (validation(answer)) {

            return if (question.answer.contains(answer.toLowerCase())) {
                question = question.nextQuestion()
                "Отлично - ты справился\n${question.question}" to status.color
            } else {
                counter = counter + 1
                Log.d("M_MainActivity", "$counter")
                status = status.nextStatus()
                "это неправильный ответ! \n${question.question}" to status.color

                if (counter >= 4) {
                    question = Question.NAME
                    counter = 0
                    "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
                } else {
                    "это неправильный ответ! \n${question.question}" to status.color
                }
            }
        } else {
            return "${wrongValidation()} \n${question.question}" to status.color
        }
    }

    fun validation(answer: String): Boolean {
        when (question) {
            Question.NAME -> {
                if (answer.first().isUpperCase()) {
                    Log.d("M_MainActivity", "validation name")
                    return true
                } else {
                    Log.d("M_MainActivity", "validation name - error message")
                    return false
                }
            }
/////////////////
            Question.PROFESSION -> {
                if (answer.first().isLowerCase()) {
                    Log.d("M_MainActivity", "validation profession")
                    return true
                } else {
                    Log.d("M_MainActivity", "validation profession - error message")
                    return false
                }
            }
/////////////////
            Question.MATERIAL -> {
                var materialMatched = false
                val toRegeMaterial = "[0-9]".toRegex()
                materialMatched = !toRegeMaterial.containsMatchIn(input = answer)
                if (materialMatched) {
                    Log.d("M_MainActivity", "validation material")
                    return true
                } else {
                    Log.d("M_MainActivity", "validation material - error message")
                    return false
                }
            }
///////////
            Question.BDAY -> {
                var materialMatched = false
                val toRegeMaterial = "[0-9]".toRegex()
                materialMatched = toRegeMaterial.containsMatchIn(input = answer)
                if (materialMatched) {
                    Log.d("M_MainActivity", "validation BDAY")
                    return true
                } else {
                    Log.d("M_MainActivity", "validation BDAY - error message")
                    return false
                }
            }
////////////
            Question.SERIAL  -> {
                var materialMatched = false
                val toRegeMaterial = "[0-9]".toRegex()
                materialMatched = toRegeMaterial.containsMatchIn(input = answer)
                if (materialMatched && answer.toCharArray().size == 7) {
                    Log.d("M_MainActivity", "validation SERIAL")
                    return true
                } else {
                    Log.d("M_MainActivity", "validation SERIAL - error message")
                    return false
                }
            }

        }
        return false
    }


    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 255, 0));


        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answer: List<String>) {
        NAME("Как меня зовут?", listOf("Бендер", "bender")) {

            override fun nextQuestion(): Question = PROFESSION
        },

        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },

        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },

        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },

        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },

        IDLE("Отлично - ты справился\nНа этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
    }

    fun wrongValidation(): String = when (question) {


        Question.NAME -> "Имя должно начинаться с заглавной буквы"
        Question.PROFESSION -> "Профессия должна начинаться со строчной буквы"
        Question.MATERIAL -> "Материал не должен содержать цифр"
        Question.BDAY -> "Год моего рождения должен содержать только цифры"
        Question.SERIAL -> "Серийный номер содержит только цифры, и их 7"
        Question.IDLE -> ""
    }

}*/
