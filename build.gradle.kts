plugins {
  kotlin("jvm") version "1.8.10"
  idea
  jacoco
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

jacoco {
  toolVersion = "0.8.8"
  reportsDirectory.set(layout.buildDirectory.dir("customJacocoReportDir"))
}

tasks.test {
  finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
  dependsOn(tasks.test) // tests are required to run before generating the report
}