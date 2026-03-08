package com.sujoodcounter

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.os.Build

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var proximitySensor: Sensor? = null
    
    private lateinit var countTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var instructionTextView: TextView
    private lateinit var resetButton: Button
    private lateinit var toggleButton: Button
    
    private var sujoodCount = 0
    private var isNear = false
    private var wasNear = false
    private var isTracking = false
    private var lastDetectionTime = 0L
    
    // Minimum time between detections to avoid double counting (milliseconds)
    private val DETECTION_COOLDOWN = 1500L
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Keep screen on during prayer
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        
        // Initialize views
        countTextView = findViewById(R.id.countTextView)
        statusTextView = findViewById(R.id.statusTextView)
        instructionTextView = findViewById(R.id.instructionTextView)
        resetButton = findViewById(R.id.resetButton)
        toggleButton = findViewById(R.id.toggleButton)
        
        // Initialize sensor
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        
        if (proximitySensor == null) {
            statusTextView.text = "⚠️ Proximity sensor not available on this device"
            toggleButton.isEnabled = false
        }
        
        // Button listeners
        resetButton.setOnClickListener {
            resetCount()
        }
        
        toggleButton.setOnClickListener {
            toggleTracking()
        }
        
        updateUI()
    }
    
    private fun toggleTracking() {
        isTracking = !isTracking
        if (isTracking) {
            startTracking()
        } else {
            stopTracking()
        }
        updateUI()
    }
    
    private fun startTracking() {
        proximitySensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        isTracking = true
        wasNear = false
        lastDetectionTime = 0L
    }
    
    private fun stopTracking() {
        sensorManager.unregisterListener(this)
        isTracking = false
    }
    
    private fun resetCount() {
        sujoodCount = 0
        updateUI()
        provideFeedback(isReset = true)
    }
    
    private fun updateUI() {
        countTextView.text = sujoodCount.toString()
        
        if (isTracking) {
            toggleButton.text = "⏸️ Pause Tracking"
            statusTextView.text = if (isNear) "🟢 Sujood Detected" else "⚪ Waiting for Sujood..."
            instructionTextView.text = "Place phone on prayer mat\nProximity sensor will detect prostration"
        } else {
            toggleButton.text = "▶️ Start Tracking"
            statusTextView.text = "⏸️ Tracking Paused"
            instructionTextView.text = "Press Start to begin counting"
        }
    }
    
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_PROXIMITY && isTracking) {
            val distance = event.values[0]
            val maxRange = proximitySensor?.maximumRange ?: 5f
            
            // Consider "near" if distance is less than max range (usually 0 = near, max = far)
            isNear = distance < maxRange
            
            val currentTime = System.currentTimeMillis()
            
            // Count when transitioning from near to far (lifting head from sujood)
            // This counts when you COMPLETE a sujood (rise from it)
            if (wasNear && !isNear && (currentTime - lastDetectionTime > DETECTION_COOLDOWN)) {
                sujoodCount++
                lastDetectionTime = currentTime
                provideFeedback(isReset = false)
            }
            
            wasNear = isNear
            updateUI()
        }
    }
    
    private fun provideFeedback(isReset: Boolean) {
        // Vibrate
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                val vibrator = vibratorManager.defaultVibrator
                vibrator.vibrate(VibrationEffect.createOneShot(if (isReset) 300L else 100L, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(if (isReset) 300L else 100L, VibrationEffect.DEFAULT_AMPLITUDE))
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(if (isReset) 300L else 100L)
                }
            }
        } catch (e: Exception) {
            // Vibration not available
        }
        
        // Short beep sound
        try {
            val toneGenerator = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 50)
            toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP, 100)
        } catch (e: Exception) {
            // Sound not available
        }
    }
    
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for proximity sensor
    }
    
    override fun onResume() {
        super.onResume()
        if (isTracking) {
            startTracking()
        }
    }
    
    override fun onPause() {
        super.onPause()
        stopTracking()
    }
}
