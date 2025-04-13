plugins {
    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    jacoco
    `maven-publish`

    id("com.consentframework.consentmanagement.checkstyle-config") version "1.1.0"
}

repositories {
    mavenCentral()
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/Consent-Management-Platform/consent-management-api-models")
            credentials {
                username = project.findProperty("gpr.usr") as String? ?: System.getenv("GITHUB_USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

dependencies {
    implementation(libs.guava)
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
    implementation("jakarta.annotation:jakarta.annotation-api:3.0.0")

    // Logging
    val log4j2Version = "2.23.1"
    implementation("org.apache.logging.log4j:log4j-api:$log4j2Version")
    implementation("org.apache.logging.log4j:log4j-core:$log4j2Version")

    // Immutables
    val immutablesDependency = "org.immutables:value:2.10.1"
    compileOnly(immutablesDependency)
    annotationProcessor(immutablesDependency)
    testCompileOnly(immutablesDependency)
    testAnnotationProcessor(immutablesDependency)

    // AWS DynamoDB
    implementation("software.amazon.awssdk:dynamodb-enhanced:2.26.7")

    // Consent service models
    implementation("com.consentframework.consentmanagement:consentmanagement-api-models:0.3.0")

    // Use JUnit Jupiter for testing.
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Use the following syntax to export dependencies to consumers, that is, add to their compile classpath.
    // api(libs.commons.math3)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks {
    withType<Test> {
        useJUnitPlatform()
        finalizedBy(jacocoTestReport)
    }

    jacocoTestCoverageVerification {
        violationRules {
            rule {
                limit {
                    minimum = BigDecimal.valueOf(0.95)
                }
            }
        }
    }

    build {
        dependsOn("packageJar")
    }

    check {
        // Fail build if under min test coverage thresholds
        dependsOn(jacocoTestCoverageVerification)
    }
}

// Build jar which will later be consumed to run the API service
tasks.register<Zip>("packageJar") {
    into("lib") {
        from(tasks.jar)
        from(configurations.runtimeClasspath)
    }
}

tasks.clean {
    delete("$rootDir/bin")
    delete("$rootDir/build")
}

// Publish jar to GitHub Packages so can import into other repositories
publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Consent-Management-Platform/consent-api-java-common")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }

    publications {
        register<MavenPublication>("gpr") {
            groupId = "com.consentframework"
            artifactId = "api-java-common"
            version = "0.0.10"

            from(components["java"])
        }
    }
}
