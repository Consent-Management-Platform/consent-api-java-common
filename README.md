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
    implementation("com.consentframework:api-java-common:0.0.6")
}
```

## Technologies
[AWS SDK for Java 2.x](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide) is used to write Java application code integrating with AWS services such as Lambda and DynamoDB.

[GitHub Actions](https://docs.github.com/en/actions) are used to automatically build and run unit tests against service code changes pushed or submitted through pull requests.

[Gradle](https://docs.gradle.org) is used to build the project and manage package dependencies.

## License
The code in this project is released under the [GPL-3.0 License](LICENSE).
