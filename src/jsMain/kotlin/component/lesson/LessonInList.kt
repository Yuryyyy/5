package component.lesson

import component.template.ElementInListProps
import react.FC
import react.dom.html.ReactHTML.span
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.data.Lesson


val CLessonInList = FC<ElementInListProps<Item<Lesson>>>("LessonInList") { props ->
    span {
        +props.element.elem.name
    }
}