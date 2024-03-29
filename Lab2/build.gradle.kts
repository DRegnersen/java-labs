plugins {
    id("java")
    id("io.freefair.lombok") version "8.0.1"
}

group = "com.dregnersen"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

subprojects{
    apply(plugin = "io.freefair.lombok")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}