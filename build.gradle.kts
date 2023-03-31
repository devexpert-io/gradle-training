import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import java.util.Locale

plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.com.github.ben.manes.versions)
    alias(libs.plugins.nl.littlerobots.version.catalog.update)
    alias(libs.plugins.com.github.triplet.play) apply false
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase(Locale.getDefault())
        .contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.withType<DependencyUpdatesTask> {
    resolutionStrategy {
        componentSelection {
            all {
                if (isNonStable(candidate.version) && !isNonStable(currentVersion)) {
                    reject("Release candidate")
                }
            }
        }
    }
}

val sayHello by tasks.registering {
    doLast {
        println("Hello from Gradle!")
    }
}

val sayGoodbye by tasks.registering {
    dependsOn(sayHello)
    doLast {
        println("Goodbye from Gradle!")
    }
}

val deleteZip by tasks.registering(Delete::class) {
    delete("build/project.zip")
}

val zipProject by tasks.registering(Zip::class) {
    dependsOn(deleteZip)
    from(".")
    destinationDirectory.set(file("build"))
    archiveFileName.set("project.zip")
    exclude("build/**")
}
