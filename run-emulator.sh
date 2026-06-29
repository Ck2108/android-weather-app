#!/bin/bash
# Run the Weather App on the Android emulator (same setup Antigravity/Android Studio use)
set -euo pipefail

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
SDK="${ANDROID_HOME:-$HOME/Library/Android/sdk}"
JAVA_HOME="${JAVA_HOME:-/Applications/Android Studio.app/Contents/jbr/Contents/Home}"
AVD="${1:-Medium_Phone_API_36.1}"

export JAVA_HOME
export ANDROID_HOME="$SDK"
export ANDROID_SDK_ROOT="$SDK"
export GRADLE_USER_HOME="${GRADLE_USER_HOME:-$HOME/.gradle}"
export PATH="$JAVA_HOME/bin:$SDK/platform-tools:$SDK/emulator:$PATH"

echo "Using Java: $($JAVA_HOME/bin/java -version 2>&1 | head -1)"
echo "Using AVD:  $AVD"

if ! adb devices | grep -q "emulator-.*device"; then
  echo "Starting emulator (cold boot)..."
  nohup emulator -avd "$AVD" -gpu host -no-snapshot-load -no-boot-anim > /tmp/weather-emulator.log 2>&1 &
  echo "Waiting for emulator..."
  adb wait-for-device
  until [ "$(adb shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')" = "1" ]; do
    sleep 2
  done
  # Package manager needs a few extra seconds after boot_completed
  sleep 5
fi

echo "Building and installing..."
cd "$PROJECT_DIR"
./gradlew installDebug --no-daemon

echo "Launching app..."
adb shell am start -n com.weather.app/.MainActivity
echo "Done! Weather app should be on your emulator screen."
