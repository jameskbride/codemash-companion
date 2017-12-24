# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn com.squareup.okhttp.**
-dontwarn retrofit2.Platform$Java8
-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep,includedescriptorclasses class com.crashlytics.**
-keep,includedescriptorclasses class io.fabric.sdk.android.**
-dontwarn com.crashlytics.**
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
-keep public class com.jameskbride.codemashcompanion.application.ApplicationComponentFactory {*;}
-keep class kotlin.jvm.internal.DefaultConstructorMarker {*;}
-keep,includedescriptorclasses class com.jameskbride.codemashcompanion.utils.FabricWrapper {*;}
-keep,includedescriptorclasses class com.jameskbride.codemashcompanion.utils.CrashlyticsFactory {*;}
-keep,includedescriptorclasses class com.jameskbride.codemashcompanion.bus.** {*;}