package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.extensions.humanizeDiff
import java.util.*

/**
 * @author Space
 * @date 29.06.2019
 */

class TextMessage(
        id: String,
        from: User?,
        chat: Chat,
        isIncoming: Boolean,
        date: Date = Date(),
        var text: String?) : BaseMessage(id, from, chat, isIncoming, date) {

    override fun formatMessage() = "id:$id ${from?.firstName} ${if (isIncoming) "получил" else "отправил"} сообщение \"$text\" ${date.humanizeDiff()}"
}