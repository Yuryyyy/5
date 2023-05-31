package component.student

import component.template.ElementInListProps
import react.FC
import react.dom.html.ReactHTML.span
import react.router.dom.Link
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.data.Student


val CStudentInList = FC<ElementInListProps<Item<Student>>>("StudentInList") { props ->
    span {
        Link {
            +props.element.elem.fullname()
            to = props.element.id
        }
    }
}