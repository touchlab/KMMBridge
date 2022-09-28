buildscript {
    val KOTLIN_VERSION: String by project
    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.18.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION")
        classpath("org.jetbrains.kotlin:kotlin-allopen:$KOTLIN_VERSION")
    }
}


