
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
project(':react-native-visa-checkout').projectDir = new File(rootProject.projectDir,     '../node_modules/react-native-visa-checkout/android')
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

5. Add the `NSFaceIDUsageDescription` key to your `Info.plist` along with a message `Permission to use Face ID` as a value, because VisaCheckout SDK may use Face ID for authentication in iPhone X.

6. From VisaCheckoutSDK docs:
```
Go to your Build Phases tab for your target’s settings.
Click on the + icon and select New Run Script Phase. Make sure the script is run after Embedded Frameworks.
Select the ✅Run script only when installing option.
Paste the following code into the script code window:
```

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

## Methods

### RNVisaCheckout

#### `configureProfileAsync:Promise`

Parameters:
- `environment` (number):
- Use either `RNVisaCheckout.Constants.Environment.Sandbox` or `RNVisaCheckout.Constants.Environment.Production`. Use Sandbox for testing and development.
- `apiKey` (string):
- Create an API key at [Visa Portal](https://developer.visa.com) and pass it here.
- `profileName` (string):
- Pass your profile name. Can be `null` if you do not have it.

Configures your environment. If promise is resolved, it worked and you are ready to use checkout. If it rejected, an error has occured and you can see the message.

#### `checkoutAsync:Promise`

Parameters:
- `total` (double):
- The value of your transaction as a double (for example, 22.03).
- `currency` (integer):
- The currency code of the currency that your transaction should use. Use a `RNVisaCheckout.Constants.Currency` value, like `RNVisaCheckout.Constants.Currency.USD` for american dollars.

Opens up the checkout screen for your user to make the transaction using the Visa SDK.
If the promise is resolved it should return an object representing the payment summary, it has the format:
```
callId: string,
lastFourDigits: string,
country: number,
postalCode: string,
paymentMethodType: string,
cardBrand: number,
encryptedKey: string,
```

`cardBrand` is an int of the value of `RNVisaCheckout.Constants.Card` of the card used in the transaction.
`country` is an int of the value of `RNVisaCheckout.Constants.Country`.

If the promise is rejected, an error message will be shown accordingly.

### RNVisaCheckoutButton

#### Properties

###### `cardStyle`

Values: `RNVisaCheckout.Constants.CardStyle.Neutral` or `RNVisaCheckout.Constants.CardStyle.Standard` (default).

Sets the card style on the left side of the button.

###### `cardAnimations`

Values: `true` (default) or `false`

Sets if the swipe animation is on or off. If off, nothing will happen when trying to drag the arrow image on the left.

###### `checkoutOptions`

This sets the checkout options for the transaction to be done when the user swipes the Visa Checkout Button all the way and the Visa Checkout screens pops up.

An object with two fields:
- `total` (double):
- The value of your transaction as a double (for example, 22.03).
- `currency` (integer):
-  The currency code of the currency that your transaction should use. Use a `RNVisaCheckout.Constants.Currency` value, like `RNVisaCheckout.Constants.Currency.USD` for american dollars.

The default is `{ total: 0, currency: 0 }`.

###### `onCardCheckout`

Function to be called when the user swipes the card to the end and FINISHES the transaction without errors.

The object received is a payment summary and has the format:

```
callId: string,
lastFourDigits: string,
country: number,
postalCode: string,
paymentMethodType: string,
cardBrand: number,
encryptedKey: string,
```

`cardBrand` is an int of the value of `RNVisaCheckout.Constants.Card` of the card used in the transaction.
`country` is an int of the value of `RNVisaCheckout.Constants.Country`.

###### `onCardCheckoutError`

Function to be called when the user swiped the card to the end but something went wrong with the transaction, like a user cancellation or a Visa internal error.

The object received has two fields: `code` (string) and `message` (string) containing the error code and error message.

## Constants

### Environment
Prefix: `RNVisaCheckout.Constants.Environment` or `RNVisaCheckoutButton.Constants.Environment`.

Values: `RNVisaCheckout.Constants.Environment.Sandbox` or `RNVisaCheckout.Constants.Environment.Production`.

### Card
Prefix: `RNVisaCheckout.Constants.Card` or `RNVisaCheckoutButton.Constants.Card`.

Values:
- `RNVisaCheckout.Constants.Card.Amex`;
- `RNVisaCheckout.Constants.Card.Discover`;
- `RNVisaCheckout.Constants.Card.Electron`;
- `RNVisaCheckout.Constants.Card.Elo`;
- `RNVisaCheckout.Constants.Card.Mastercard`;
- `RNVisaCheckout.Constants.Card.Visa`;

### CardStyle
Prefix: `RNVisaCheckout.Constants.CardStyle` or `RNVisaCheckoutButton.Constants.CardStyle`.

Values: `RNVisaCheckout.Constants.CardStyle.Standard` or `RNVisaCheckout.Constants.CardStyle.Neutral.`

### Currency
Prefix: `RNVisaCheckout.Constants.Currency` or `RNVisaCheckoutButton.Constants.Currency`.

Values:
- `RNVisaCheckout.Constants.Currency.AED`
- `RNVisaCheckout.Constants.Currency.ARS`
- `RNVisaCheckout.Constants.Currency.AUD`
- `RNVisaCheckout.Constants.Currency.BRL`
- `RNVisaCheckout.Constants.Currency.CAD`
- `RNVisaCheckout.Constants.Currency.CLP`
- `RNVisaCheckout.Constants.Currency.CNY`
- `RNVisaCheckout.Constants.Currency.EUR`
- `RNVisaCheckout.Constants.Currency.GBP`
- `RNVisaCheckout.Constants.Currency.HKD`
- `RNVisaCheckout.Constants.Currency.INR`
- `RNVisaCheckout.Constants.Currency.KWD`
- `RNVisaCheckout.Constants.Currency.MYR`
- `RNVisaCheckout.Constants.Currency.NZD`
- `RNVisaCheckout.Constants.Currency.PEN`
- `RNVisaCheckout.Constants.Currency.PLN`
- `RNVisaCheckout.Constants.Currency.QAR`
- `RNVisaCheckout.Constants.Currency.SAR`
- `RNVisaCheckout.Constants.Currency.SGD`
- `RNVisaCheckout.Constants.Currency.UAH`
- `RNVisaCheckout.Constants.Currency.USD`
- `RNVisaCheckout.Constants.Currency.ZAR`

### Country
Prefix: `RNVisaCheckout.Constants.Country` or `RNVisaCheckoutButton.Constants.Country`.

Values:
- `RNVisaCheckout.Constants.Country.Argentina`
- `RNVisaCheckout.Constants.Country.Australia`
- `RNVisaCheckout.Constants.Country.Brazil`
- `RNVisaCheckout.Constants.Country.Canada`
- `RNVisaCheckout.Constants.Country.Chile`
- `RNVisaCheckout.Constants.Country.China`
- `RNVisaCheckout.Constants.Country.Colombia`
- `RNVisaCheckout.Constants.Country.France`
- `RNVisaCheckout.Constants.Country.HongKong`
- `RNVisaCheckout.Constants.Country.India`
- `RNVisaCheckout.Constants.Country.Ireland`
- `RNVisaCheckout.Constants.Country.Kuwait`
- `RNVisaCheckout.Constants.Country.Malaysia`
- `RNVisaCheckout.Constants.Country.Mexico`
- `RNVisaCheckout.Constants.Country.NewZealand`
- `RNVisaCheckout.Constants.Country.Peru`
- `RNVisaCheckout.Constants.Country.Poland`
- `RNVisaCheckout.Constants.Country.Qatar`
- `RNVisaCheckout.Constants.Country.SaudiArabia`
- `RNVisaCheckout.Constants.Country.Singapore`
- `RNVisaCheckout.Constants.Country.SouthAfrica`
- `RNVisaCheckout.Constants.Country.Spain`
- `RNVisaCheckout.Constants.Country.Ukraine`
- `RNVisaCheckout.Constants.Country.UnitedArabEmirates`
- `RNVisaCheckout.Constants.Country.UnitedKingdom`
- `RNVisaCheckout.Constants.Country.UnitedStates`

## Usage

### RNVisaCheckout methods

```javascript
import RNVisaCheckout from 'react-native-visa-checkout';

await RNVisaCheckout.configureProfileAsync(RNVisaCheckout.Constants.Environment.Sandbox, 'apiKey', null);
const checkoutData = await RNVisaCheckout.checkoutAsync(22.09, RNVisaCheckout.Constants.Currency.USD);
console.log(checkoutData);
```

### RNVisaCheckoutButton

See the Example app in this repository to see a demo of the RNVisaCheckoutButton in action.


