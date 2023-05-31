package ru.altmanea.webapp.auth

import ru.altmanea.webapp.access.*

val roleAdmin = Role(
    "admin",
    listOf(userAdmin)
)

val roleUser = Role(
    "user",
    listOf(userAdmin, userTutor)
)

val roleList = listOf(roleAdmin, roleUser)