package ru.altmanea.webapp.auth
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.html.*

fun Route.authRoutes() {
    get("/form-login") {
        call.respondHtml {
            body {
                form(
                    action = "/login",
                    encType = FormEncType.applicationXWwwFormUrlEncoded,
                    method = FormMethod.post
                ) {
                    p {
                        +"Username:"
                        textInput(name = "username")
                    }
                    p {
                        +"Password:"
                        passwordInput(name = "password")
                    }
                    p {
                        submitInput() { value = "Login" }
                    }
                }
            }
        }
    }

    authenticate("auth-form") {
        post("/form-login") {
            val userName = call.principal<UserIdPrincipal>()?.name.toString()
            call.sessions.set(UserSession(name = userName, count = 1))
            call.respondRedirect("/hello")
        }
    }

    authenticate("auth-session") {
        get("/hello") {
            val userSession = call.principal<UserSession>()
            call.sessions.set(userSession?.copy(count = userSession.count + 1))
            call.respondText("Hello, ${userSession?.name}! Visit count is ${userSession?.count}.")
        }
    }

    get("/logout") {
        call.sessions.clear<UserSession>()
        call.respondRedirect("/login")
    }
}
