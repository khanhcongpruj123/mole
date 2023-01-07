rootProject.name = "mole"
include("post-service")

pluginManagement {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
        maven { url = uri("https://repo.spring.io/release") }
    }
}
