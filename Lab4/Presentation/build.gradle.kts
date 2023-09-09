plugins {
    java
    id("org.springframework.boot") version "3.0.6"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "com.dregnersen.security"
version = "0.0.2-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(project(mapOf("path" to ":Lab4:Application")))
    implementation(project(mapOf("path" to ":Lab3:Presentation")))
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4")
    implementation(project(mapOf("path" to ":Lab4:DataAccess")))
    implementation(project(mapOf("path" to ":Lab3:Application")))
    implementation(project(mapOf("path" to ":Lab3:DataAccess")))
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
