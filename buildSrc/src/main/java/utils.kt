import org.gradle.api.Project
import java.io.FileInputStream
import java.util.*

fun Project.loadProperties(fileName: String): Properties =
    Properties().apply {
        load(FileInputStream(file(fileName)))
    }