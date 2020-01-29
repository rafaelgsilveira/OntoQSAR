plugins {
    kotlin("jvm") version "1.3.61"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    
    implementation("org.apache.jena:apache-jena-libs:3.13.1")
    
    implementation("io.ktor:ktor-server-core:1.2.6")
    implementation("io.ktor:ktor-server-netty:1.2.6")
    implementation("ch.qos.logback:logback-classic:1.2.3")
}

application {
    mainClassName = "me.kerooker.jena.MainKT"
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.register("stage") {
    dependsOn("installDist")
}
