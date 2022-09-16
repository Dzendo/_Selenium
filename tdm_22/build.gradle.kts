// Top-level build file where you can add configuration options common to all sub-projects/modules.
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinTest

plugins {
    //java
    kotlin("jvm") version "1.7.20-RC" //"1.7.10"
    application
}
group = "ru.cs.tdm"
version = "1.0-SNAPSHOT"
val junitVersion = "5.9.0" //"5.9.0-M1" //"5.8.2"

repositories {
    mavenCentral()
}

//sourceCompatibility = '11'
//targetCompatibility = '11'

tasks.test {
    useJUnitPlatform()
    testLogging { events("passed", "skipped", "failed") }
}
/*
[compileKotlin, compileTestKotlin].forEach {
    it.kotlinOptions {
        jvmTarget = '11'
    }
}
*/
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}
tasks.withType<KotlinTest> {
    //kotlinOptions.jvmTarget = "11"
    //options.jvmTarget = "11"
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.seleniumhq.selenium:selenium-java:4.4.0")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.3.0")
    testImplementation("org.slf4j:slf4j-simple:2.0.1") //1.7.36")  // 2.0.0-alpha7 // 1.8.0-beta4

    // https://junit.org/junit5/docs/current/user-guide/#running-tests agregator
    testImplementation("org.junit.jupiter:junit-jupiter:${junitVersion}")
    //testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    //testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
    //testImplementation(platform("org.junit:junit-bom:${junitVersion}"))
    //testImplementation("org.junit.jupiter:junit-jupiter-params:${junitVersion}")

    // https://mvnrepository.com/artifact/org.junit.platform/junit-platform-launcher
    //testImplementation("org.junit.platform:junit-platform-launcher:1.8.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.9.0") // 1.8.2")
    //{ because("Only needed to run tests in a version of IntelliJ IDEA that bundles older versions") }
    // java -jar junit-platform-console-standalone-1.8.2.jar <Options>
    testImplementation("org.assertj:assertj-core:3.23.1")
}

application {
    mainClass.set("MainKt")
}

/*import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
plugins {
    kotlin("jvm") version "1.6.20-RC"
    application
}
group = "ru.cs.tdm"
version = "1.0-SNAPSHOT"
repositories {
    mavenCentral()
}
dependencies {
    testImplementation(kotlin("test"))
}
tasks.test {
    useJUnitPlatform()
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
application {
    mainClass.set("MainKt")
}*/