import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    //implementation(kotlin("test"))
    testImplementation("org.seleniumhq.selenium:selenium-java:4.6.0")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.3.1")
    { exclude ("org.bouncycastle") }
    testImplementation("org.slf4j:slf4j-simple:2.0.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
    testImplementation ("org.junit.platform:junit-platform-launcher:1.9.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}