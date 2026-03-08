# How to Build the APK Without Android Studio

## Option 1: GitHub Actions (Recommended - Completely Free)

This is the easiest method. GitHub will build your APK in the cloud automatically.

### Steps:

1. **Create a GitHub account** (if you don't have one)
   - Go to https://github.com and sign up

2. **Create a new repository**
   - Click the "+" icon → "New repository"
   - Name it `SujoodCounter`
   - Make it Public (free builds)
   - Click "Create repository"

3. **Upload the project files**
   - Extract the `SujoodCounter.zip` file
   - In GitHub, click "uploading an existing file"
   - Drag and drop ALL the extracted files/folders
   - Click "Commit changes"

4. **Wait for the build** (2-3 minutes)
   - Go to the "Actions" tab in your repo
   - You'll see "Build APK" running
   - Wait for the green checkmark ✓

5. **Download your APK**
   - Click on the completed workflow run
   - Scroll down to "Artifacts"
   - Click "SujoodCounter-debug" to download
   - Extract and install the APK on your phone!

---

## Option 2: Codemagic (Free tier available)

1. Go to https://codemagic.io
2. Sign up with GitHub
3. Connect your repository
4. It will auto-detect Android project
5. Click "Start new build"
6. Download APK when done

---

## Option 3: Appetize.io (For testing only)

If you just want to TEST the app without installing:
1. Use Option 1 or 2 to get the APK
2. Go to https://appetize.io
3. Upload your APK
4. Test it in browser (simulated Android)

---

## Option 4: Local build with just JDK (No Android Studio)

If you have Java installed:

### Windows:
```cmd
cd SujoodCounter
gradlew.bat assembleDebug
```

### Mac/Linux:
```bash
cd SujoodCounter
chmod +x gradlew
./gradlew assembleDebug
```

APK will be at: `app/build/outputs/apk/debug/app-debug.apk`

Note: First build downloads ~500MB of dependencies.

---

## Installing the APK on your phone

1. Transfer the APK to your phone
2. Open the APK file
3. If blocked, go to Settings → Security → Enable "Install from unknown sources"
4. Install and enjoy!

---

## Troubleshooting

**Build fails?**
- Make sure all files are uploaded correctly
- Check that folder structure matches the original

**Can't install APK?**
- Enable "Unknown sources" in Android settings
- Make sure you downloaded the .apk file, not the .zip

**App crashes?**
- Your phone might not have a proximity sensor
- Check app permissions are granted
