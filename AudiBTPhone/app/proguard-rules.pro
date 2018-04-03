# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\Heyu\Tools\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# 指定代码的压缩级别
-optimizationpasses 5

# 包明不混合大小写
-dontusemixedcaseclassnames

# 不去忽略非公共的库类
-dontskipnonpubliclibraryclasses

# 优化 不优化输入的类文件
-dontoptimize

# 预校验
-dontpreverify

# 混淆时是否记录日志
-verbose

# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/,!class/merging/

# 保护注解
-keepattributes Annotation

-dontwarn android.support.v4.**
-keep class android.support.v4.**{*;}

# Application classes
-keep class com.lingfei.android.main.ui.main.bean.** { *; }

##---------------End: proguard configuration for Gson  ----------

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public class * implements java.io.Serializable {*;}

# 保持哪些类不被混淆
#-keep public class * extends android.app.Fragment
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

# 如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment

-keepattributes *JavascriptInterface*

# 忽略警告
-ignorewarning

# 记录生成的日志数据,gradle build时在本项目根目录输出
# apk 包内所有 class 的内部结构
-dump class_files.txt

# 未混淆的类和成员
-printseeds seeds.txt

# 列出从 apk 中删除的代码
-printusage unused.txt

# 混淆前后的映射
-printmapping mapping.txt

-libraryjars ../uilib/libs/butterknife-7.0.1.jar
-libraryjars ../uilib/libs/classes.jar
-libraryjars ../uilib/libs/eventbus-3.0.0-beta1.jar
-libraryjars ../uilib/libs/otto-1.3.8.jar·

-keep class butterknife.** { *; }
-keep class de.greenrobot.**{*;}


