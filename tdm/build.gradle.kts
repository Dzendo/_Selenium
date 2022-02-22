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
        testImplementation("io.github.bonigarcia:webdrivermanager:5.0.3")
        testImplementation("org.slf4j:slf4j-simple:1.7.36")

        //testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
        //testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")
        testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
    }

application {
    mainClass.set("MainKt")
}