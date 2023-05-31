package ru.altmanea.webapp.rest

import io.ktor.server.auth.*
import io.ktor.server.routing.*
import ru.altmanea.webapp.auth.authorization
import ru.altmanea.webapp.auth.roleAdmin
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.repo.rolesRepo

fun Route.roleRoutes() {
    route(Config.rolesPath) {
        repoRoutes(
            rolesRepo,
            listOf(
                ApiPoint.read to { rolesRepo.read().map { it.elem }.toSet() },
                ApiPoint.write to { setOf(roleAdmin) }
            )
        )
        authenticate("auth-form") {
            authorization(setOf(roleAdmin)) {
            }
        }
    }
}
