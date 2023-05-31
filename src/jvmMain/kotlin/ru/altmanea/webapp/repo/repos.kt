package ru.altmanea.webapp.repo

import ru.altmanea.webapp.access.Role
import ru.altmanea.webapp.auth.roleList
import ru.altmanea.webapp.common.Item
import ru.altmanea.webapp.data.Grade
import ru.altmanea.webapp.data.GradeInfo
import ru.altmanea.webapp.data.Lesson
import ru.altmanea.webapp.data.Student

val studentsRepo = ListRepo<Student>()
val lessonsRepo = ListRepo<Lesson>()
val rolesRepo = ListRepo<Role>()

fun createTestData() {
    roleList.map {
        rolesRepo.create(it)
    }

    listOf(
        Student("Sheldon", "Cooper"),
        Student("Leonard", "Hofstadter"),
        Student("Howard", "Wolowitz"),
        Student("Penny", "Hofstadter"),
    ).apply {
        map {
            studentsRepo.create(it)
        }
    }

    listOf(
        Lesson("Math"),
        Lesson("Phys"),
        Lesson("Story"),
    ).apply {
        map {
            lessonsRepo.create(it)
        }
    }

    val students = studentsRepo.read()
    val lessons = lessonsRepo.read()
    val sheldon = students.findLast { it.elem.firstname == "Sheldon" }
    check(sheldon != null)
    val leonard = students.findLast { it.elem.firstname == "Leonard" }
    check(leonard != null)
    val math = lessons.findLast { it.elem.name =="Math" }
    check(math != null)
    val newMath = Lesson(
        math.elem.name,
        arrayOf(
            GradeInfo(sheldon.id, Grade.A),
            GradeInfo(leonard.id, Grade.B)
        ))
    lessonsRepo.update(Item(newMath, math.id, math.version))
}
