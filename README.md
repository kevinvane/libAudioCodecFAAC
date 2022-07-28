# FAAC Native Library

### 导入

Add it in your root `build.gradle` at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
        implementation 'com.github.kevinvane:libAudioCodecFAAC:1.0.0'
}
```


### 混淆

```
-keepclassmembers class com.codec.faac.** {*;}
```


### 示例

查看Native类或者FAAC官方文档