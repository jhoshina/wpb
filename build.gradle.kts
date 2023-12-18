plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.kapt) apply false
}

tasks.create("clean", type = Delete::class) {
    delete(rootProject.buildDir)
}
