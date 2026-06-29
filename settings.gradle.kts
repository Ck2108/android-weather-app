// =============================================================
// settings.gradle.kts — "What's in this project?"
// =============================================================
// Think of this like a TABLE OF CONTENTS for your project.
// It tells Gradle:
//   1. Where to find libraries/tools (pluginManagement + repositories)
//   2. What modules (parts) this project has (include ":app")
//
// 🍽️ Restaurant analogy: This is like the restaurant's floor plan
//    — it says "we have a kitchen (app module)" and "here's where
//    we order our supplies from (Google, Maven Central)."
// =============================================================

pluginManagement {
    // Where to find Gradle PLUGINS (tools that help build the app)
    repositories {
        google {
            // Only look here for Android & Google libraries
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()        // The biggest library store on the internet
        gradlePluginPortal()  // Where Gradle's own plugins live
    }
}

dependencyResolutionManagement {
    // FAIL_ON_PROJECT_REPOS = "Only use the repositories listed HERE,
    // don't let individual modules add their own." This keeps things tidy.
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    // Where to find LIBRARIES (code other people wrote that we use)
    repositories {
        google()        // Android libraries (Compose, Material, etc.)
        mavenCentral()  // Everything else (Retrofit, OkHttp, etc.)
    }
}

// The name of our project
rootProject.name = "WeatherApp"

// The modules (parts) in our project — we only have one: "app"
// At T-Mobile, a big project might have: include(":app", ":cart", ":checkout", ":network")
include(":app")
