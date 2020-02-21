plugins {
    java
    kotlin("jvm") version "1.3.61"
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "kotlin")

    group = "io.arct"
    version = "1.0.0"

    repositories {
        mavenCentral()
        jcenter()
        
        flatDir {
            dirs("libs")
        }   
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))

        compile(kotlin("reflect"))
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
    }
}

subprojects {
    apply(plugin = "java")

    dependencies {
        implementation(fileTree(mapOf("dir" to "../libs", "include" to listOf("*.jar"))))

        implementation(project(":"))
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}