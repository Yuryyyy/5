package ru.altmanea.webapp.access

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.altmanea.webapp.data.GradeInfo
import ru.altmanea.webapp.data.Lesson

@Serializable
class Role(
    val name: String,
    val users: List<User>
){
    fun addUser(user: User) =
        Role(
            name,
            users + user
        )
}


val Role.json
    get() = Json.encodeToString(this)