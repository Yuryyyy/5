package ru.altmanea.webapp.auth

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import ru.altmanea.webapp.access.userList
import ru.altmanea.webapp.repo.rolesRepo


fun Application.authConfig() {
    install(Authentication) {
        form("auth-form") {
            userParamName = "username"
            passwordParamName = "password"
            validate { credentials ->
                fun findUserInBase(login: String, pass: String): UserIdPrincipal? {
                    userList.forEach {
                        return if (it.username == login && it.password == pass)
                            UserIdPrincipal(it.username)
                        else
                            null
                    }
                    return null
                }

                findUserInBase(credentials.name, credentials.password) ?: throw PrincipalError
            }
        }
        session<UserSession>("auth-session") {
            validate { session -> session }
            challenge {
                call.respondRedirect("/form-login")
            }
        }

    }
    install(Authorization) {
        getRole = { user ->
            val usr = userList.find { it.username == user }

            val listRole = rolesRepo.read().map { it.elem }

            listRole.mapNotNull { role ->
                if (usr!!.username in role.users.map { it.username }) {
                    role
                } else {
                    null
                }
            }.toSet()
        }
    }
}