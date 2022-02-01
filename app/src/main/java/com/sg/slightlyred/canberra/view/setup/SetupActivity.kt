package com.sg.slightlyred.canberra.view.setup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.sg.slightlyred.canberra.databinding.ActivityMainBinding
import com.sg.slightlyred.canberra.databinding.ActivitySetupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetupActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySetupBinding
    private val viewModel: SetupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySetupBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewModel.fetchAllBusStops()
    }

}