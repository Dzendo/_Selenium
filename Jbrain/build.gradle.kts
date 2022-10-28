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
    implementation("org.seleniumhq.selenium:selenium-java:4.5.3")
    implementation("io.github.bonigarcia:webdrivermanager:5.3.0")
    { exclude ("org.bouncycastle") }
    implementation("org.slf4j:slf4j-simple:2.0.3")
    implementation("org.junit.jupiter:junit-jupiter:5.9.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}