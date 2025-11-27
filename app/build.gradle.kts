plugins {
    id("java")
    id("application")
}

group = "org.mailtrap"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Your existing dependencies
    implementation("jakarta.mail:jakarta.mail-api:2.1.3")
    implementation("org.eclipse.angus:angus-mail:2.0.3")
    implementation("org.openjfx:javafx-controls:25.0.1")
    implementation("org.openjfx:javafx-fxml:25.0.1")

    // Add these for JUnit 5
    // testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0")
    // testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.0")
    // JUnit 4 dependency for testing
    testImplementation("junit:junit:4.13.2")
}

application {
    mainClass = "org.example.org.mailtrap.Main"
}

tasks.test {
     useJUnit() // This ensures JUnit 4 is used
    // useJUnitPlatform() // This enables JUnit 5
    // failOnNoDiscoveredTests = false
}


