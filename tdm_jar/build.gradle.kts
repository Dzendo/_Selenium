// Top-level build file where you can add configuration options common to all sub--projects/modules.

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinTest

plugins {
    //java
    //id("java")
    kotlin("jvm") version "1.7.20"
    application
}
group = "ru.cs.tdm"
version = "1.0-SNAPSHOT"
val junitVersion = "5.9.1"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
    testLogging { events("passed", "skipped", "failed") }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
tasks.withType<KotlinTest> {
    //kotlinOptions.jvmTarget = "11"
    //options.jvmTarget = "11"  // "1.8"
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}


dependencies {
    implementation(kotlin("test"))
    implementation("org.seleniumhq.selenium:selenium-java:4.5.3")
    implementation("io.github.bonigarcia:webdrivermanager:5.3.0")
    { exclude ("org.bouncycastle") }
    implementation("org.slf4j:slf4j-simple:2.0.3")
    implementation("org.junit.jupiter:junit-jupiter:5.9.1")
    implementation("org.assertj:assertj-core:3.23.1")
    // https://mvnrepository.com/artifact/org.junit.platform/junit-platform-launcher
    // testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.9.1") // 1.8.2")
    //{ because("Only needed to run tests in a version of IntelliJ IDEA that bundles older versions") }
    // java -jar junit-platform-console-standalone-1.8.2.jar <Options>
}

/*
application {
    mainClass.set("RootKt")
}*/
