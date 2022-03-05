plugins {
    java
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("java")
}

group = "com.github.JIN_InI"
version = "1.0-SNAPSHOT"
val ktorVersion = "1.6.7"

repositories {
    mavenCentral()
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.6.0")
    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    // Ktor
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.register("deployToServer") {
    dependsOn("shadowJar")
    val from = File("$rootDir\\build\\libs\\${project.name}-${project.version}-all.jar")
    val server = File("$rootDir\\server\\plugins\\${project.name}-${project.version}.jar")

    doFirst {
        from.copyTo(server,true)
    }
}
