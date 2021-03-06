## <img src="res/drawable-ldpi/ic_launcher.png"> DashClock Hotspot Extension

[DashClock Wifi Extension](https://play.google.com/store/apps/details?id=com.pixelus.dashclock.ext.hotspot) 
is an extension for the 
[DashClock Widget](https://play.google.com/store/apps/details?id=net.nurik.roman.dashclock) from Roman Nurik.

This extension displays your devices current Hotspot status on the DashClock widget and even allows you to 
toggle it on/off just by tapping the extension.

Specifically, this extension allows you to:
+ Easily see your current hotspot status (enabled, enabling, disabled, disabling)
+ Easily see your active hotspot connections
+ Toggle your current Hotspot status with one tap

<img src="playstore/screenshots/thumbs/s5-device-3.png" 
  align="center">&nbsp;&nbsp;<img src="playstore/screenshots/thumbs/s5-device-2.png" align="center">

## Technical Details

This application is built using the [Maven Android Plugin](https://code.google.com/p/maven-android-plugin/).  See the 
[Getting Started Guide](https://code.google.com/p/maven-android-plugin/wiki/GettingStarted) for required environment 
pre-requisites and steps.

### Building a DEVELOPMENT apk:

To build a development apk, use the maven goal:
```
mvn clean install
```

### Deploying an apk to your device/emulator:

To deploy an apk to your connected device or emulator, use the maven goal:
```
mvn android:deploy
```

### Building a RELEASE apk:

When building a release version of the application, the generated artifacts will be suitable for uploading directly into 
the Google Playstore.  As such, the release build process will ensure that the code is shrunk, optimized and obfuscated
(using proguard), signed (certificate not included with this repo), and zip-aligned.  The resulting 
`target/hotspot-signed-aligned.apk` is then ready for upload into the playstore.  To initiate a new release, simply:

1. Bump maven application version and versionCode:  
   Update `pom.xml` to increment <project>'s <version> and <project.versionCode> values 
        
3. Commit the changes to source control  

4. Build a **release** apk using maven:  
   ```
   mvn clean package -P release
   ```
 Note: a relevant profile should exist in your ~/.m2/settings.xml that defines the key signing properties used by the jarsigner