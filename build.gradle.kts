plugins {
    id("java")
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {



        compileOnly ("org.projectlombok:lombok")

        annotationProcessor ("org.projectlombok:lombok")

       implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    runtimeOnly ("com.mysql:mysql-connector-j")

    implementation("io.jsonwebtoken:jjwt-api:0.12.5")

    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")

    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")

    implementation ("org.springframework.boot:spring-boot-starter-security")

    implementation ("org.springframework.boot:spring-boot-starter-validation")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.projectlombok:lombok:1.18.46")


}

tasks.test {
    useJUnitPlatform()
}