[![CircleCI](https://circleci.com/gh/jameskbride/codemash-companion.svg?style=svg)](https://circleci.com/gh/jameskbride/codemash-companion)

Running the App Locally
=======================
To run the app locally you'll need to download and run the [codemash-data](https://github.com/jameskbride/codemash-data) project per the instructions.
This will spin up a server on port 8181, at which point you can run the emulator and pull data.

You'll also need a `release.properties` file in the root project directory in the following format:

```
store.file=<PATH TO .jks STORE>
store.password=<PASSWORD TO THE .jks store>
key.alias=codemash-companion-key
key.password=<KEY PASSWORD>
```
