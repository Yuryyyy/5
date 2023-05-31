package component.role

import component.template.ElementInListProps
import react.FC
import react.dom.html.ReactHTML.span
import react.router.dom.Link
import ru.altmanea.webapp.access.Role
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.data.Student


val CRoleInList = FC<ElementInListProps<Item<Role>>>("StudentInList") { props ->
    span {
        +props.element.elem.name
    }
}