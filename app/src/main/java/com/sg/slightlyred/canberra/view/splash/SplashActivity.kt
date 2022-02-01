package com.sg.slightlyred.canberra.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sg.slightlyred.canberra.databinding.ActivitySplashBinding
import com.sg.slightlyred.canberra.view.main.MainActivity
import com.sg.slightlyred.canberra.view.setup.SetupActivity

class SplashActivity : AppCompatActivity() {
    private val LOG_TAG: String = this::class.java.name

    private lateinit var viewBinding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewModel.getIsFirstRun().observe(this) { isFirstRun ->
            Log.d(LOG_TAG, "isFirstRun: $isFirstRun")
            onFirstRunCheck(isFirstRun)
        }
    }

    private fun onFirstRunCheck(isFirstRun: Boolean) {
        /*val intent: Intent = if (isFirstRun) {
            Intent(this, SetupActivity::class.java)
        } else {
            Intent(this, MainActivity::class.java)
        }*/
        val intent: Intent = Intent(this, MainActivity::class.java)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(intent)
        }, 2000)
    }
}