# consent-api-java-common
Common Java classes for API services hosted in AWS Lambda that will handle requests from AWS API Gateway.

## Usage

Merge the following into build.gradle.kts:

```kotlin
repositories {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/Consent-Management-Platform/consent-api-java-common")
            credentials {
                username = project.findProperty("gpr.usr") as String? ?: System.getenv("GITHUB_USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

dependencies {
    // Common Consent Framework API Java libraries
    implementation("com.consentframework:api-java-common:0.0.3")
    testImplementation("com.consentframework:api-java-common:0.0.3")
}
```
