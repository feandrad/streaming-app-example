package com.outcomehealth.app.ui.player

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.outcomehealth.app.R
import com.outcomehealth.app.ui.base.BaseActivity
import com.outcomehealth.app.ui.gallery.GalleryAdapter
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.activity_player.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerActivity : BaseActivity<PlayerViewModel>() {

    companion object {
        const val SELECTED_VIDEO = "SELECTED_VIDEO"
    }

    override val viewModel: PlayerViewModel by viewModel()
    override val layout: Int get() = R.layout.activity_player

    private val adapter: GalleryAdapter by inject()


    override fun initViews() {
        super.initViews()
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            initRecyclerView()
        }
    }

    private fun initRecyclerView() {
        rv_main_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.onVideoOHClicked = { viewModel.videoClicked(it) }
        rv_main_list.adapter = adapter
    }

    override fun observeLiveData() {
        viewModel.playerLiveData.observe(this, Observer {
            it?.let { player_main.player = it }
        })
    }


}