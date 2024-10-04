-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

-keep class com.frbiw.core.** {*;}
-dontwarn com.frbiw.core.**
-keepattributes Exceptions, Signature, InnerClasses

-dontobfuscate