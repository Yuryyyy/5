package component.role

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import react.*
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.option
import react.dom.html.ReactHTML.select
import ru.altmanea.webapp.access.Role
import ru.altmanea.webapp.access.userList
import ru.altmanea.webapp.common.Item
import web.html.HTMLSelectElement


external interface AddUserProps : Props {
    var role: Item<Role>
    var addUser: (Role) -> Unit
}

val CAddUserToLesson = FC<AddUserProps>("AddStudent") { props ->
    val selectRef = useRef<HTMLSelectElement>()
    select {
        ref = selectRef
        userList.map { user ->
            option {
                +"${user.username} ${user.password}"
                value = Json.encodeToString(user)
            }
        }
    }
    button {
        +"add"
        onClick = {
            selectRef.current?.value?.let {
                props.addUser(props.role.elem.addUser(Json.decodeFromString(selectRef.current!!.value)))
            }
        }
    }
}