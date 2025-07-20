package dev.javfuentes.tictactoe2.utils

import android.view.View
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

object ImmersiveMode {
    
    /**
     * Enable full immersive mode - hides both status bar and navigation bar
     */
    fun enableFullImmersive(activity: ComponentActivity) {
        val windowInsetsController = WindowCompat.getInsetsController(activity.window, activity.window.decorView)
        
        windowInsetsController.apply {
            // Configure the behavior of the hidden system bars
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            
            // Hide both status bar and navigation bar
            hide(WindowInsetsCompat.Type.systemBars())
        }
        
        // Make sure the content extends behind system bars
        WindowCompat.setDecorFitsSystemWindows(activity.window, false)
    }
    
    /**
     * Hide only navigation bar, keep status bar visible
     */
    fun hideNavigationBar(activity: ComponentActivity) {
        val windowInsetsController = WindowCompat.getInsetsController(activity.window, activity.window.decorView)
        
        windowInsetsController.apply {
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            hide(WindowInsetsCompat.Type.navigationBars())
        }
        
        WindowCompat.setDecorFitsSystemWindows(activity.window, false)
    }
    
    /**
     * Show system bars again
     */
    fun showSystemBars(activity: ComponentActivity) {
        val windowInsetsController = WindowCompat.getInsetsController(activity.window, activity.window.decorView)
        windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
    }
    
    /**
     * Set system bars to dark mode with black background
     */
    fun setDarkSystemBars(activity: ComponentActivity) {
        activity.window.apply {
            statusBarColor = android.graphics.Color.BLACK
            navigationBarColor = android.graphics.Color.BLACK
        }
        
        val windowInsetsController = WindowCompat.getInsetsController(activity.window, activity.window.decorView)
        windowInsetsController.isAppearanceLightStatusBars = false
        windowInsetsController.isAppearanceLightNavigationBars = false
    }
}