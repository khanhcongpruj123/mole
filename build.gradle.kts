import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "org.idev"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "1.7.20" apply(false)
    kotlin("kapt") version "1.7.20"
    application
}

subprojects {

   apply {
       plugin("org.gradle.application")
       plugin("org.jetbrains.kotlin.jvm")
       plugin("kotlin-kapt")
   }

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

        testImplementation(kotlin("test"))
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        testImplementation("io.projectreactor:reactor-test")

        implementation("com.github.pozo:mapstruct-kotlin:1.4.0.0")
        implementation("org.mapstruct:mapstruct:1.5.3.Final")
    }
    tasks.test {
        useJUnitPlatform()
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }
}