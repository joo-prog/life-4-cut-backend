plugins {
    java
    id("org.springframework.boot") version "3.1.2"
    id("io.spring.dependency-management") version "1.1.2"
    id("org.asciidoctor.jvm.convert") version "3.3.2"
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

val asciidoctorExt: Configuration by configurations.creating

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
//    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    testImplementation("junit:junit:4.13.1")
    testImplementation("org.testng:testng:7.1.0")

    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.mysql:mysql-connector-j")
    testRuntimeOnly("com.h2database:h2")

    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.navercorp.fixturemonkey:fixture-monkey-starter:0.6.3")
    asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
//    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}



tasks {
    val snippetsDir = file("${buildDir}/generated-snippets")

    test {
        outputs.dir(snippetsDir)
        useJUnitPlatform()
    }

    asciidoctor {
        configurations(asciidoctorExt.name)
        inputs.dir(snippetsDir)
        dependsOn(test)
    }
}
