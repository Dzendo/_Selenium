// Top-level build file where you can add configuration options common to all sub--projects/modules.
// https://habr.com/ru/post/305974/
// 14.02.2023 rev 1.0.9 EAP 2 Listener
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinTest

plugins {
    kotlin("jvm") version "1.8.10"
    application
}
group = "ru.cs.tdm"
version = "1.0-SNAPSHOT"
val junitVersion = "5.9.2"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
    testLogging { events("passed", "skipped", "failed") }
}
kotlin {
    jvmToolchain(17)
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}
tasks.withType<KotlinTest> {
    //kotlinOptions.jvmTarget = "11"
    //options.jvmTarget = "11"  // "1.8"
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-test:1.8.10")
    implementation("org.seleniumhq.selenium:selenium-java:4.8.0")
    implementation("io.github.bonigarcia:webdrivermanager:5.3.2")
    { exclude ("org.bouncycastle") }
    // https://coderlessons.com/tutorials/java-tekhnologii/vyuchi-slf4j/slf4j-kratkoe-rukovodstvo
    implementation("org.slf4j:slf4j-simple:2.0.6")
    implementation("org.junit.jupiter:junit-jupiter:5.9.2")
    //implementation("org.assertj:assertj-core:3.23.1")
    // https://mvnrepository.com/artifact/org.junit.platform/junit-platform-launcher
    implementation("org.junit.platform:junit-platform-launcher:1.9.2") // 1.8.2")
    //{ because("Only needed to run tests in a version of IntelliJ IDEA that bundles older versions") }
    // java -jar junit-platform-console-standalone-1.9.1.jar <Options>
}
application {
    mainClass.set("TdmKt")
}
