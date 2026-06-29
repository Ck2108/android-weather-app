// =============================================================
// build.gradle.kts (PROJECT-LEVEL) — "What tools do we need?"
// =============================================================
// This is the TOP-LEVEL recipe book. It says:
//   "For this project, we MIGHT need these tools (plugins)."
//   But it doesn't USE them yet — that happens in app/build.gradle.kts
//
// 🍽️ Restaurant analogy: This is like saying "Our restaurant
//    will need an oven, a stove, and a mixer" but not turning
//    them on yet. Each kitchen (module) decides what to turn on.
//
// "apply false" = "Download this tool but don't use it here.
//                  The app module will decide to use it."
// =============================================================

plugins {
    // Tool for building Android apps
    id("com.android.application") version "8.5.2" apply false

    // Tool for writing in Kotlin language
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false

    // Tool that lets Kotlin understand Jetpack Compose (our UI framework)
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.0" apply false
}
