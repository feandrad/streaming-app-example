package com.outcomehealth.app.ui.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.outcomehealth.app.R
import kotlinx.android.synthetic.main.activity_player.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerActivity : AppCompatActivity() {

    companion object {
        const val SELECTED_VIDEO = "SELECTED_VIDEO"
    }

    private val viewModel: PlayerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        initToolbar()
        observeLiveData()
        initUiEvents()

        viewModel.activityCreated(intent.extras ?: savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        viewModel.activityResumed(this)
    }

    override fun onPause() {
        super.onPause()
        viewModel.activityPaused(this)
    }


    private fun initToolbar() {
    }

    private fun observeLiveData() {
        viewModel.playerLiveData.observe(this, Observer {
            it?.let { player_main.player = it }
        })
    }

    private fun initUiEvents() {
    }


}