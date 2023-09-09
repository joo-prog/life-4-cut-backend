plugins {
    java
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    id("jacoco")
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
val asciidoctorExt: Configuration by configurations.creating

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.session:spring-session-data-redis")
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
    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.springframework.security:spring-security-test")
}

sourceSets {
    getByName("main").java.srcDirs(querydslDir)
}

tasks.withType<JavaCompile> {
    options.generatedSourceOutputDirectory.set(file(querydslDir))
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
     * REST Docs & Asciidoc
     */
    asciidoctor {
        configurations(asciidoctorExt.name)
        inputs.dir(snippetsDir)
        dependsOn(test)
    }

    bootJar {
        dependsOn(asciidoctor)
        from("build/docs/asciidoc") {
            into("static/docs")
        }
    }

    register<Copy>("copyAsciidoctor") {
        dependsOn(asciidoctor)
        from(file("$buildDir/docs/asciidoc"))
        into(file("src/main/resources/static/docs"))
    }

    build {
        dependsOn("copyAsciidoctor")
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
