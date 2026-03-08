# Sujood Counter (سجود)

An Android app that automatically counts prostrations (sujood) during Muslim prayer (salah) using your phone's proximity sensor.

## How It Works

1. **Place your phone on the prayer mat** - face up, near where your head will be during sujood
2. **Press "Start Tracking"** - the app begins listening to the proximity sensor
3. **Pray normally** - when you prostrate (sujood), your head/forehead comes close to the phone
4. **Automatic counting** - the app detects when you rise from sujood and increments the counter
5. **Feedback** - a gentle vibration and beep confirm each counted prostration

## Features

- 🕌 **Automatic Detection** - Uses proximity sensor to detect prostrations
- 📱 **Screen stays on** - Won't go to sleep during prayer
- 📳 **Haptic & Audio Feedback** - Subtle vibration and beep for each count
- 🔄 **Reset Counter** - Start fresh for each prayer
- ⏸️ **Pause/Resume** - Control when tracking is active
- 🌙 **Dark Theme** - Easy on the eyes, Islamic aesthetic

## Detection Logic

The app uses a **cooldown system** (1.5 seconds) to prevent double-counting:
- Counts when you **rise from sujood** (transition from near to far)
- Won't count multiple times if you adjust position
- Perfect for the natural rhythm of salah

## Requirements

- Android 7.0 (API 24) or higher
- Device with proximity sensor (most phones have this)
- Vibration permission (for haptic feedback)

## Building the App

### Option 1: Android Studio
1. Open Android Studio
2. File → Open → Select the `SujoodCounter` folder
3. Wait for Gradle sync to complete
4. Run → Run 'app' (or press Shift+F10)

### Option 2: Command Line
```bash
cd SujoodCounter
./gradlew assembleDebug
# APK will be in app/build/outputs/apk/debug/
```

## Project Structure

```
SujoodCounter/
├── app/
│   ├── src/main/
│   │   ├── java/com/sujoodcounter/
│   │   │   └── MainActivity.kt      # Main logic + sensor handling
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   └── activity_main.xml # UI layout
│   │   │   ├── drawable/
│   │   │   │   └── gradient_background.xml
│   │   │   └── values/
│   │   │       ├── colors.xml
│   │   │       ├── strings.xml
│   │   │       └── themes.xml
│   │   └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## Tips for Best Results

1. **Phone Position**: Place face-up on the prayer mat, about 15-20cm from where your head reaches during sujood
2. **Test First**: Do a test prostration before starting your prayer to ensure detection works
3. **Flat Surface**: Ensure the phone is on a flat, stable surface
4. **Start Tracking**: Remember to press "Start Tracking" before beginning salah

## Prayer Reference

For a complete salah:
- **Fajr**: 4 sujood (2 rak'ah × 2 sujood)
- **Dhuhr**: 8 sujood (4 rak'ah × 2 sujood)
- **Asr**: 8 sujood (4 rak'ah × 2 sujood)
- **Maghrib**: 6 sujood (3 rak'ah × 2 sujood)
- **Isha**: 8 sujood (4 rak'ah × 2 sujood)

## License

MIT License - Feel free to use, modify, and distribute.

---

بسم الله الرحمن الرحيم
