package component.group

import js.core.get
import js.core.jso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import query.QueryError
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.router.useParams
import react.useContext
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.data.Student
import tanstack.query.core.QueryKey
import tanstack.react.query.useMutation
import tanstack.react.query.useQuery
import tanstack.react.query.useQueryClient
import tools.HTTPResult
import tools.fetch
import tools.fetchText
import userInfoContext
import kotlin.js.json

val CCurrentStudent = FC<Props> {
    val param = useParams()["name"]
    val queryClient = useQueryClient()
    val userInfo = useContext(userInfoContext)

    val studentQueryKey = arrayOf("editGroupStudent").unsafeCast<QueryKey>()

    val query = useQuery<String, QueryError, String, QueryKey>(
        queryKey = studentQueryKey,
        queryFn = {
            fetchText(
                Config.studentsPath + param + "/lessons",
                jso {
                    headers = json("Authorization" to userInfo?.second?.authHeader)
                }
            )
        }
    )

    val updateGroupMutation = useMutation<HTTPResult, Any, Pair<String, String>, Any>(
        mutationFn = { studentItem: Pair<String, String> ->
            fetch(
                url = Config.edit,
                jso {
                    method = "PUT"
                    headers = json(
                        "Content-Type" to "application/json",
                        "Authorization" to userInfo?.second?.authHeader
                    )
                    body = Json.encodeToString(studentItem)
                }
            )
        },
        options = jso {
            onSuccess = { _: Any, _: Any, _: Any? ->
                queryClient.invalidateQueries<Any>(studentQueryKey)
            }
        }
    )

    if (query.isLoading) div { +"Loading .." }
    else if (query.isError) div { +"Error!" }
    else {
        val item =
            Json.decodeFromString<Pair<Item<Student>, List<String?>>>(query.data ?: "")
        CSingleStudent {
            student = item.first
            updateGroup = {
                updateGroupMutation.mutateAsync(it, null)
            }
            queryKey = studentQueryKey
        }
        ReactHTML.table {
            if (item.second.filterNotNull().isEmpty()) {
                ReactHTML.tr { ReactHTML.td { +"No lessons" } }
            } else
                item.second.filterNotNull().forEach {
                    ReactHTML.tr {
                        ReactHTML.td { +it }
                    }
                }
        }
    }
}