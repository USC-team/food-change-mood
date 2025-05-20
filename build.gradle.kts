plugins {
    kotlin("jvm") version "2.1.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("com.google.truth:truth:1.4.4")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:1.4.0")
    implementation(project.dependencies.platform("io.insert-koin:koin-bom:4.0.3"))
    implementation("io.insert-koin:koin-core")


}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}