package component.group

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.data.Student
import tanstack.query.core.QueryKey

external interface SingleStudentProps : Props {
    var student: Item<Student>
    var updateGroup: (Pair<String, String>) -> Unit
    var queryKey: QueryKey
}

val CSingleStudent = FC<SingleStudentProps>("StudentsContainer") { props ->
    div {
        +props.student.elem.fullname()
    }
}