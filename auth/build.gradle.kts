plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

group = "org.idev.mole"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.keycloak:keycloak-spring-boot-starter:19.0.3")
    implementation("org.keycloak:keycloak-admin-client:19.0.3")

    implementation("com.github.pozo:mapstruct-kotlin:1.4.0.0")
    implementation("org.mapstruct:mapstruct:1.5.3.Final")

    kapt("org.mapstruct:mapstruct-processor:1.5.3.Final")
    kapt("com.github.pozo:mapstruct-kotlin-processor:1.4.0.0")
}