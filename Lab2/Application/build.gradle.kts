plugins {
    id("java")
}

group = "com.dregnersen"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":Lab2:DataAccess")))
    implementation("org.mockito:mockito-testng:0.4.30")
    implementation("org.mockito:mockito-core:5.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}