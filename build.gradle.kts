import org.hidetake.gradle.swagger.generator.GenerateSwaggerUI

plugins {
    java
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
    id("jacoco")
    id("com.epages.restdocs-api-spec") version "0.18.2"
    id("org.hidetake.swagger.generator") version "2.18.2"
}

group = "com.onebyte"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")

val querydslDir = "src/main/generated"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("software.amazon.awssdk:s3:2.20.146")

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")

    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.mysql:mysql-connector-j")
    testRuntimeOnly("com.h2database:h2")

    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter:0.6.3")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.security:spring-security-test")

    // open api swagger
    testImplementation("com.epages:restdocs-api-spec-mockmvc:0.18.2")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    swaggerUI("org.webjars:swagger-ui:4.11.1")

    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:localstack")
}


openapi3 {
    setServer("http://localhost:8080")
    title = "spring-rest-docs + Swagger-UI"
    description = "Swagger UI"
    version = "0.0.1"
    format = "json"
    outputDirectory = "build/resources/main/static/docs"
}


sourceSets {
    getByName("main").java.srcDirs(querydslDir)
}

tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory.set(file(querydslDir))
}

tasks.withType<GenerateSwaggerUI> {
    dependsOn("openapi3")

    copy {
        from("$buildDir/resources/main/static/docs")
        into("src/main/resources/static/docs/")
    }
}

tasks {
    /**
     * QueryDSL
     */
    val snippetsDir = file("$buildDir/generated-snippets")

    test {
        outputs.dir(snippetsDir)
        useJUnitPlatform()
        finalizedBy(jacocoTestReport)
    }

    clean {
        doLast {
            file(querydslDir).deleteRecursively()
        }
    }

    /**
     * REST Docs
     */
    bootJar {
        dependsOn("openapi3")

        copy {
            from("$buildDir/resources/static/docs")
            into("src/main/resources/static/docs")
        }
    }

    /**
     * Jacoco
     */
    jacocoTestReport {
        dependsOn(test)
    }

//    jacocoTestCoverageVerification {
//        violationRules {
//            rule {
//                enabled = true
//
//                element = "CLASS"
//
//                limit {
//                    counter = "BRANCH"
//                    value = "COVEREDRATIO"
//                    minimum = "0.80".toBigDecimal()
//                }
//
//                // 커버리지 체크 제외 클래스 지정
//                excludes = listOf(
//                    "*.Config.*",
//                )
//            }
//        }
//    }
}
