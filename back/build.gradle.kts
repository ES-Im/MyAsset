plugins {
    java
    id("org.springframework.boot") version "3.5.8"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.github.spotbugs") version "6.4.8"
    id("org.asciidoctor.jvm.convert") version "4.0.5"
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

configurations {
    create("asciidoctorExt")
}

dependencies {
    // web
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    // validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // OAuth2 & Security
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")
    testImplementation("org.springframework.security:spring-security-test")

    // json web token (jwt)
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    // JPA
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // mysql
    runtimeOnly("com.mysql:mysql-connector-j")

    // docker
    runtimeOnly("org.springframework.boot:spring-boot-docker-compose")

    // lombok
    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // test (h2)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    add("asciidoctorExt", "org.springframework.restdocs:spring-restdocs-asciidoctor")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")


}


// snippets(코드 조각)의 Dir(디렉토리)를 전역변수로 선언
val snippetsDir by extra { file("build/generated-snippets") }

tasks {

    test {
        useJUnitPlatform()
        outputs.dir(snippetsDir) // 테스트가 끝난 결과물(문서 파일)을 이 snippet 디렉토리로 넣도록 지정
    }

    asciidoctor {

        dependsOn(test)         // 테스트가 성공한 결과물을 받아서, 아스키독터라는 태스크에서 문서를 만든다.
        inputs.dir(snippetsDir)
        configurations("asciidoctorExt")

        sources {
            include("**/index.adoc")
        }

        baseDirFollowsSourceFile()

    }

    bootJar {
        dependsOn(asciidoctor)
        from("${asciidoctor.get().outputDir}") {
            into("static/docs")
        }
    }


}