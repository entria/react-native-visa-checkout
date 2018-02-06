
# react-native-visa-checkout

## Getting started

`$ npm install react-native-visa-checkout --save`

### Mostly automatic installation

`$ react-native link react-native-visa-checkout`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-visa-checkout` and add `RNVisaCheckout.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNVisaCheckout.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import org.reactnative.RNVisaCheckoutPackage;` to the imports at the top of the file
  - Add `new RNVisaCheckoutPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-visa-checkout'
  	project(':react-native-visa-checkout').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-visa-checkout/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-visa-checkout')
  	```
    
### Post install and building

#### iOS

1. In app's target, `Build Settings`, in `Build options` set `Always Embed Swift Standard Libraries` to `Yes`.
2. Download the correct iOS VisaCheckout SDK for your XCode from https://developer.visa.com/capabilities/visa_checkout/docs. Add `TrustDefender.framework` and `VisaCheckoutSDK.framework`to your app's project.
3. On your app's target, on `Embedded Binaries` click on the + and add the `VisaCheckoutSDK.framework`.
4. The TrustDefender library also needs another system framework. In `Linked Frameworks and Libraries`, click the + button and add `libz.tbd`.

5. From VisaCheckoutSDK docs:
```
Go to your Build Phases tab for your target’s settings.
Click on the + icon and select New Run Script Phase. Make sure the script is run after Embedded Frameworks.
Select the ✅Run script only when installing option.
Paste the following code into the script code window:```

```
# This script removes simulator architectures from VisaCheckoutSDK.framework so it can be uploaded to the App Store.
# Make this a Build Phase run script after the 'Embed Frameworks' phase.
# 'Run script only when installing' should be checked.
# http://stackoverflow.com/questions/30547283/submit-to-app-store-issues/30866648

APP_PATH="${TARGET_BUILD_DIR}/${WRAPPER_NAME}"

FRAMEWORK_EXECUTABLE_NAME=VisaCheckoutSDK
find "$APP_PATH" -name "${FRAMEWORK_EXECUTABLE_NAME}.framework" -type d | while read -r FRAMEWORK
do

FRAMEWORK_EXECUTABLE_PATH="$FRAMEWORK/$FRAMEWORK_EXECUTABLE_NAME"

EXTRACTED_ARCHS=()

for ARCH in $ARCHS
do
lipo -extract "$ARCH" "$FRAMEWORK_EXECUTABLE_PATH" -o "$FRAMEWORK_EXECUTABLE_PATH-$ARCH"
EXTRACTED_ARCHS+=("$FRAMEWORK_EXECUTABLE_PATH-$ARCH")
done

lipo -o "$FRAMEWORK_EXECUTABLE_PATH-merged" -create "${EXTRACTED_ARCHS[@]}"
rm "${EXTRACTED_ARCHS[@]}"

rm "$FRAMEWORK_EXECUTABLE_PATH"
mv "$FRAMEWORK_EXECUTABLE_PATH-merged" "$FRAMEWORK_EXECUTABLE_PATH"
done
```

4. Build your project.

#### Android

1. In your `android/build.gradle` add this inside the `allprojetcs/repositories`:
    ```
    flatDir {
        dirs project(':react-native-visa-checkout').file('libs')
    }
    ```
    For example:
    ```
    allprojects {
        repositories {
        ...
            flatDir {
                dirs project(':react-native-visa-checkout').file('libs')
            }
        }
    }
    ```
2. In your manifest add the following permissions:
    ```
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.VIBRATE"/>
    <uses-permission android:name="android.permission.USE_FINGERPRINT"/>
    ```
3. In your manifest in `<application` set the `android:allowBackup` to `false:
    ```
    <application
    android:name=".MainApplication"
    android:allowBackup="false"
    ```

## Usage
```javascript
import RNVisaCheckout from 'react-native-visa-checkout';

await RNVisaCheckout.configureProfileAsync(RNVisaCheckout.Constants.Environment.Sandbox, 'apiKey', null);
const checkoutData = await RNVisaCheckout.checkoutAsync(22.09, RNVisaCheckout.Constants.Currency.USD);
console.log(checkoutData);
```
  
