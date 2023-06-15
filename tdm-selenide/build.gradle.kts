// Top-level build file where you can add configuration options common to all sub--projects/modules.
// https://habr.com/ru/post/305974/
// 28.03.2023 rev IDEA 2022.3  xx2023.1
// 1.4.39_7.0.20
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinTest

plugins {
    kotlin("jvm") version "1.8.21"
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
}
tasks.withType<KotlinTest> {
    //kotlinOptions.jvmTarget = "11"
    //options.jvmTarget = "11"  // "1.8"
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-test:1.8.21")  // 1.8.20-RC2
    implementation("org.seleniumhq.selenium:selenium-java:4.9.1")
    implementation("io.github.bonigarcia:webdrivermanager:5.3.3")
    { exclude ("org.bouncycastle") }
    // https://coderlessons.com/tutorials/java-tekhnologii/vyuchi-slf4j/slf4j-kratkoe-rukovodstvo
    implementation("org.slf4j:slf4j-simple:2.0.7")
    implementation("org.junit.jupiter:junit-jupiter:5.9.3")
    //implementation("org.assertj:assertj-core:3.23.1")
    implementation("org.junit.platform:junit-platform-launcher:1.9.3")
    implementation("org.seleniumhq.selenium:selenium-http-jdk-client:4.9.1")

}
application {
    mainClass.set("TdmKt")
}
