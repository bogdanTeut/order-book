plugins {
  kotlin("jvm") version "1.8.10"
  idea
}

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.junit.jupiter:junit-jupiter:5.8.1")
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
  testImplementation("com.google.truth:truth:1.1.3")
}

tasks {
  test {
    useJUnitPlatform()
  }
}