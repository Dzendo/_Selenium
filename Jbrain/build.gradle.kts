import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("test"))
    implementation("org.seleniumhq.selenium:selenium-java:4.6.0")
    implementation("io.github.bonigarcia:webdrivermanager:5.3.1")
    { exclude ("org.bouncycastle") }
    implementation("org.slf4j:slf4j-simple:2.0.5")
    implementation("org.junit.jupiter:junit-jupiter:5.9.1")
    //implementation("org.assertj:assertj-core:3.23.1")
    // https://mvnrepository.com/artifact/org.junit.platform/junit-platform-launcher
    implementation("org.junit.platform:junit-platform-launcher:1.9.1") // 1.8.2")
    //{ because("Only needed to run tests in a version of IntelliJ IDEA that bundles older versions") }
    // java -jar junit-platform-console-standalone-1.9.1.jar <Options>

    //implementation(kotlin("test"))
    testImplementation("org.seleniumhq.selenium:selenium-java:4.6.0")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.3.1")
    { exclude ("org.bouncycastle") }
    testImplementation("org.slf4j:slf4j-simple:2.0.5")
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