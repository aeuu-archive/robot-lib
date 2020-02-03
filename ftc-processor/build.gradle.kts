plugins {
    java
    kotlin("jvm")
}

dependencies {
    implementation(project(":ftc-lib"))
    implementation("com.squareup:kotlinpoet:1.4.4")
}