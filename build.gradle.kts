import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.4.10"
}

buildscript {

    val kotlinVersion = "1.4.10"
    val springBootVersion = "2.2.4.RELEASE"

    repositories {
        mavenCentral()
        maven(url = "https://repo.spring.io/snapshot")
        maven(url = "https://repo.spring.io/milestone")
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
        classpath(kotlin("gradle-plugin"))
        classpath(kotlin("allopen", kotlinVersion))
        classpath(kotlin("noarg", kotlinVersion))
    }
}

allprojects {

    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-jpa")
    apply(plugin = "kotlin-noarg")
    apply(plugin = "kotlin-allopen")
    apply(plugin = "jacoco")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    repositories {
        jcenter()
        mavenCentral()
        maven(url = "https://repo.spring.io/snapshot")
        maven(url = "https://repo.spring.io/milestone")
    }

    java.sourceCompatibility = JavaVersion.VERSION_1_8

    dependencies {
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation(kotlin("reflect"))
        implementation(kotlin("stdlib-jdk8"))
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        runtime("org.postgresql:postgresql")
        implementation("org.flywaydb:flyway-core")
        implementation("org.springframework:spring-context")
        implementation ("com.lemmingapex.trilateration:trilateration:1.0.2")
        implementation ("org.apache.commons:commons-math3:3.6.1")
        implementation ("com.google.code.gson:gson:2.8.6")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }

        testImplementation("org.mock-server:mockserver-netty:5.8.1")
        testImplementation("org.mock-server:mockserver-client-java:5.8.1")
        implementation("org.mock-server:mockserver-junit-jupiter:5.8.1")
    }

    configurations {
        all {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    tasks.getByName<Jar>("jar") {
        enabled = true
    }
    tasks.getByName<BootJar>("bootJar") {
        mainClassName = "com.fuego.quasar.app.ApplicationKt"
        classifier = "boot"
    }

    tasks.withType < Test > {
        useJUnitPlatform()

        testLogging {
            events("passed", "skipped", "failed", "standardError")
        }

        extensions.configure(JacocoTaskExtension::class) {
            setDestinationFile(file("${rootProject.buildDir}/jacoco/test.exec"))
        }


        addTestListener(object: TestListener {

            override fun beforeTest(testDescriptor: TestDescriptor) {}

            override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {}

            override fun beforeSuite(testDescriptor: TestDescriptor) {
                println("=> Running ${testDescriptor.name} ...")
            }

            override fun afterSuite(testDescriptor: TestDescriptor, testResult: TestResult) {
                val summary = mapOf(
                        "total" to testResult.testCount,
                        "passed" to testResult.successfulTestCount,
                        "failed" to testResult.failedTestCount,
                        "skipped" to testResult.skippedTestCount
                )

                println()

                if (testDescriptor.parent == null) {
                    println(">>> Overall test results: ${testResult.resultType} $summary <<<")
                } else {
                    println("=> Finished ${testDescriptor.name} $summary")
                }
            }
        })
    }
}

task<JacocoReport>("jacocoRootReport") {
    dependsOn(subprojects.map { it.tasks.withType < Test > () })
    dependsOn(subprojects.map { it.tasks.withType < JacocoReport > () })
    additionalSourceDirs.setFrom(subprojects.map { it.the < SourceSetContainer > ()["main"].allSource.srcDirs })
    sourceDirectories.setFrom(subprojects.map { it.the < SourceSetContainer > ()["main"].allSource.srcDirs })
    classDirectories.setFrom(subprojects.map { it.the < SourceSetContainer > ()["main"].output })
    executionData.setFrom(project.fileTree(".") { include("**/build/jacoco/test.exec") })
    reports {
        xml.isEnabled = true
        html.isEnabled = true
        html.destination = file("$buildDir/reports/jacoco/test/html")
        xml.destination = file("$buildDir/reports/jacoco/test/jacocoTestReport.xml")
    }
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}