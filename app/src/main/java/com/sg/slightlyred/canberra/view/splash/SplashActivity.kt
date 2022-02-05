package com.sg.slightlyred.canberra.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sg.slightlyred.canberra.databinding.ActivitySplashBinding
import com.sg.slightlyred.canberra.utils.ResponseState
import com.sg.slightlyred.canberra.view.main.MainActivity
import com.sg.slightlyred.canberra.view.setup.SetupActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val TAG: String = "SplashActivity"

    private lateinit var viewBinding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isFirstRun.collect {
                    when (it) {
                        is ResponseState.Success -> onFirstRunCheck(it.data!!)
                        else -> {}
                    }
                }
            }
        }
    }

    private fun onFirstRunCheck(isFirstRun: Boolean) {
        val intent: Intent = if (isFirstRun) {
            Log.i(TAG, "Starting Setup")
            Intent(this, SetupActivity::class.java)
        } else {
            Log.i(TAG, "Starting Main Screen")
            Intent(this, MainActivity::class.java)
        }

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(intent)
        }, 2000)
    }
}