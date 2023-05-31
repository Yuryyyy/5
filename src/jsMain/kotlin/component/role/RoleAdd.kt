package component.role

import component.template.EditAddProps
import react.FC
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.span
import react.useState
import ru.altmanea.webapp.access.Role
import web.html.InputType

val CRoleAdd = FC<EditAddProps<Role>>("StudentAdd") { props ->
    var role by useState("")
    span {
        input {
            type = InputType.text
            value = role
            onChange = { role = it.target.value }
        }
    }
    button {
        +"✓"
        onClick = {
            props.saveElement(
                Role(
                    role,
                    emptyList()
                )
            )
        }
    }
}
