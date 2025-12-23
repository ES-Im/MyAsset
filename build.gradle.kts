plugins {
    java
    id("org.springframework.boot") version "3.5.8"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.github.spotbugs") version "6.4.8"
}

group = "dev.es"
version = "0.0.1-SNAPSHOT"
description = "MyAsset"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // web
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // OAuth2 & Security
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // mysql
    runtimeOnly("com.mysql:mysql-connector-j")

    // h2
//    runtimeOnly("com.h2database:h2")

    // docker
    runtimeOnly("org.springframework.boot:spring-boot-docker-compose")

    // lombok
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // test
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // devtool
//    "developmentOnly"("org.springframework.boot:spring-boot-devtools")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
