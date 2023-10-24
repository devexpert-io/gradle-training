package com.devexperto.customplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.bundling.Zip
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class CustomPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        val sayHello = target.tasks.register("sayHello") {
            doLast {
                println("Hello from Gradle! Today is ${today()}")
            }
        }

        target.tasks.register("sayGoodbye") {
            dependsOn(sayHello)
            doLast {
                println("Goodbye from Gradle!")
            }
        }

        val deleteZip = target.tasks.register("deleteZip", Delete::class.java) {
            delete("build/project.zip")
        }

        target.tasks.register("zipProject", Zip::class.java) {
            dependsOn(deleteZip)
            from(".")
            destinationDirectory.set(target.file("build"))
            archiveFileName.set("project.zip")
            exclude("build/**")
        }
    }
}

private fun today(): String = LocalDate.now().run {
    format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
}