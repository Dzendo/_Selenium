
// Top-level build file where you can add configuration options common to all sub--projects/modules.
// https://habr.com/ru/post/305974/

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinTest

plugins {
    kotlin("jvm") version "1.9.10"
    application
}
group = "ru.cs.tdm"
version = "1.0-SNAPSHOT"
val junitVersion = "5.9.3"

repositories {
    mavenCentral()
}

tasks.test {
    useJUnitPlatform()
    testLogging { events("passed", "skipped", "failed") }
}
kotlin {
    jvmToolchain(11)
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
//    kotlinOptions.jvmTarget = "1.8"
}
tasks.withType<KotlinTest> {
    //kotlinOptions.jvmTarget = "11"
    //options.jvmTarget = "11"  // "1.8"
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
dependencies {
//    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-test:1.9.10")  // 1.8.20-RC2
    implementation("org.seleniumhq.selenium:selenium-java:4.12.1")
    implementation("io.github.bonigarcia:webdrivermanager:5.5.3")
    { exclude ("org.bouncycastle") }
    // https://coderlessons.com/tutorials/java-tekhnologii/vyuchi-slf4j/slf4j-kratkoe-rukovodstvo
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("org.junit.jupiter:junit-jupiter:5.10.0")
    //implementation("org.assertj:assertj-core:3.23.1")
    implementation("org.junit.platform:junit-platform-launcher:1.10.0")
//    implementation("org.seleniumhq.selenium:selenium-http-jdk-client:4.10.0")

}
application {
    mainClass.set("TdmKt")
}
