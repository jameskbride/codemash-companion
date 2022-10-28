[![CircleCI](https://circleci.com/gh/jameskbride/codemash-companion.svg?style=svg)](https://circleci.com/gh/jameskbride/codemash-companion)

# Setup
1. Download and run the [codemash-data](https://github.com/jameskbride/codemash-data) project per the instructions. This will spin up a server on port 8181, at which point you can run the emulator and pull data.
2. Copy `debug.properties.template` to `local.properties`.
3. Create a new debug keystore if necessary at `~/.android/debug.keystore`. This file may already exist, in which case you can skip this step.
4. Add the following lines to your `.bashrc`/`./zshrc`, whatever shell file:
    ```
    export KEYSTORE_DEBUG=$HOME/.android/debug.keystore
    launchctl setenv KEYSTORE_DEBUG $KEYSTORE_DEBUG # This line is necessary on mac to pass environment variables to Android Studio.
    ```
5. Assemble the apk with `./gradlew assembleDebug`.
6. Run the tests with the `./gradlew testDebug`.
7. Assuming you have Android Studio installed and an emulator running api 19 or higher created and launched, you can now install and run the app with `./gradlew installDebug`. 

# Releasing
You'll also need a `release.properties` file in the root project directory in the following format:

```properties
store.file=<PATH TO .jks STORE>
store.password=<PASSWORD TO THE .jks store>
key.alias=codemash-companion-key
key.password=<KEY PASSWORD>
```

# Creating a keystore (if necessary)
Assuming you've successfully installed Android Studio, you *should* already have a debug keystore in place. If not you can use `keytool` to create one.

```shell
keytool -genkey -v -keystore ~/.android/debug.keystore -storepass android -alias androiddebugkey -keypass android -keyalg RSA -keysize 2048 -validity 10000
```

You can follow these same instructions to create a release keystore (in another path), set ot the properties for releasing above.