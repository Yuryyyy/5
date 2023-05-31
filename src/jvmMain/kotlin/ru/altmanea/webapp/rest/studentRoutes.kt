package ru.altmanea.webapp.rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.altmanea.webapp.auth.*
import ru.altmanea.webapp.config.Config
import ru.altmanea.webapp.repo.lessonsRepo
import ru.altmanea.webapp.repo.rolesRepo
import ru.altmanea.webapp.repo.studentsRepo

fun Route.studentRoutes() {
    route(Config.studentsPath) {
        repoRoutes(
            studentsRepo,
            listOf(
                ApiPoint.read to { rolesRepo.read().map { it.elem }.toSet() },
                ApiPoint.write to { setOf(roleAdmin) }
            )
        )
        authenticate("auth-form") {
            authorization(setOf(roleAdmin)) {
                get("ByStartName/{startName}") {
                    val startName =
                        call.parameters["startName"] ?: return@get call.respondText(
                            "Missing or malformed startName",
                            status = HttpStatusCode.BadRequest
                        )
                    val students = studentsRepo.read().filter {
                        it.elem.firstname.startsWith(startName)
                    }
                    if (students.isEmpty()) {
                        call.respondText("No students found", status = HttpStatusCode.NotFound)
                    } else {
                        call.respond(students)
                    }
                }
            }
            authorization(roleList.toSet()) {
                get("{idS}/lessons") {
                    val idS = call.parameters["idS"] ?: return@get call.respondText(
                        "Missing or malformed student id",
                        status = HttpStatusCode.BadRequest
                    )
                    val item =
                        studentsRepo.read(idS) ?: return@get call.respondText(
                            "No element with id $idS",
                            status = HttpStatusCode.NotFound
                        )
                    val lessons = lessonsRepo.read().map {
                        if (it.elem.students.find { it.studentId == idS } != null) it.elem.name else null
                    }
                    if (lessons.isEmpty()) {
                        call.respondText("No lessons found", status = HttpStatusCode.NotFound)
                    } else {
                        call.respond(Pair(item, lessons))
                    }
                }
            }
        }
    }
}