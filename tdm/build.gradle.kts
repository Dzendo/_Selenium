// Top-level build file where you can add configuration options common to all sub-projects/modules.
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinTest

plugins {
    java
    kotlin("jvm") version "1.6.10"
    application
}
group = "ru.cs.tdm"
version = "1.0-SNAPSHOT"
val junitVersion = "5.8.2"

repositories {
    mavenCentral()
}

//sourceCompatibility = '15'
//targetCompatibility = '15'

tasks.test {
    useJUnitPlatform()
    testLogging { events("passed", "skipped", "failed") }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
tasks.withType<KotlinTest> {
    //kotlinOptions.jvmTarget = "1.8"
    //options.jvmTarget = "1.8"
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.seleniumhq.selenium:selenium-java:4.1.2")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.1.0")
    testImplementation("org.slf4j:slf4j-simple:1.7.36")

    // https://junit.org/junit5/docs/current/user-guide/#running-tests agregator
    testImplementation("org.junit.jupiter:junit-jupiter:${junitVersion}")
    //testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    //testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
    //testImplementation(platform("org.junit:junit-bom:${junitVersion}"))
    //testImplementation("org.junit.jupiter:junit-jupiter-params:${junitVersion}")

    // https://mvnrepository.com/artifact/org.junit.platform/junit-platform-launcher
    //testImplementation("org.junit.platform:junit-platform-launcher:1.8.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.8.2")
    //{ because("Only needed to run tests in a version of IntelliJ IDEA that bundles older versions") }
// java -jar junit-platform-console-standalone-1.8.2.jar <Options>
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