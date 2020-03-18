package com.outcomehealth.app.ui.player

import android.os.Bundle
import androidx.lifecycle.Observer
import com.outcomehealth.app.R
import com.outcomehealth.app.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_player.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerActivity : BaseActivity<PlayerViewModel>() {

    companion object {
        const val SELECTED_VIDEO = "SELECTED_VIDEO"
    }

    override val viewModel: PlayerViewModel by viewModel()
    override val layout: Int get() = R.layout.activity_player

    override fun observeLiveData() {
        viewModel.playerLiveData.observe(this, Observer {
            it?.let { player_main.player = it }
        })
    }


}