# ProGuard rules for Cyber Beast Optimizer
-keep public class * extends android.app.Application
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service

# Keep Compose
-keep class androidx.compose.** { *; }
-keep class androidx.navigation.** { *; }

# Keep Hilt
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.internal.GeneratedComponent

# Keep Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keepclassmembers @androidx.room.Entity class * { <init>(...); }

# Keep Shizuku
-keep class rikka.shizuku.** { *; }

# Keep Gson
-keep class com.google.gson.** { *; }
-keep class com.cyberbeast.optimizer.data.model.** { *; }

# Keep Kotlin serialization
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

# General
-keepattributes Signature
-keepattributes Exceptions
-keepattributes SourceFile
-keepattributes LineNumberTable
-renamesourcefileattribute SourceFile
