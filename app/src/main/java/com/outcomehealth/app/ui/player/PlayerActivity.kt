package com.outcomehealth.app.ui.player

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.outcomehealth.app.R
import com.outcomehealth.app.ui.base.BaseActivity
import com.outcomehealth.app.ui.gallery.GalleryAdapter
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
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            initRecyclerView()
        }
    }

    override fun observeLiveData() {
        viewModel.playerLiveData.observe(this, Observer {
            it?.let { player_main.player = it }
        })

        viewModel.videoList.observe(this, Observer {
            adapter.setData(it)
            if (!isLayoutLandscape()) {
                resolveGalleryVisibility()
            }
        })
    }


    private fun initRecyclerView() {
        if (!isLayoutLandscape()) {
            rv_gallery!!.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            adapter.onVideoOHClicked = { viewModel.videoClicked(it, this) }
            rv_gallery!!.adapter = adapter
        }
    }

    private fun isLayoutLandscape() =
        resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE


    private fun resolveGalleryVisibility() {
        if (adapter.isEmpty()) {
            v_loading!!.visibility = View.VISIBLE
            rv_gallery!!.visibility = View.GONE
        } else {
            v_loading!!.visibility = View.GONE
            rv_gallery!!.visibility = View.VISIBLE
        }
    }


}