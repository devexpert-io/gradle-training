plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
}

gradlePlugin {

    plugins {
        create("custom-plugin") {
            id = "custom-plugin"
            implementationClass = "com.devexperto.customplugin.CustomPlugin"
        }
    }

}