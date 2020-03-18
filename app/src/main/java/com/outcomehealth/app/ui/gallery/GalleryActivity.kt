package com.outcomehealth.app.ui.gallery

import android.content.Intent
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.outcomehealth.app.R
import com.outcomehealth.app.ui.base.BaseActivity
import com.outcomehealth.app.ui.player.PlayerActivity
import com.outcomehealth.lib.VideoOH
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.toolbar.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class GalleryActivity : BaseActivity<GalleryViewModel>() {

    override val viewModel: GalleryViewModel by viewModel()
    override val layout: Int get() = R.layout.activity_gallery

    private val adapter: GalleryAdapter by inject()

    override fun initViews() {
        initToolbar()
        initRecyclerView()
    }

    private fun initToolbar() {
        val span = SpannableString(getString(R.string.app_name))
        span.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.primary)),
            8, 14,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        tv_toolbar_title.text = span
    }

    private fun initRecyclerView() {
        rv_main_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter.onVideoOHClicked = { viewModel.videoClicked(it) }
        rv_main_list.adapter = adapter
    }


    override fun observeLiveData() {
        viewModel.videoList.observe(this, Observer {
            adapter.setData(it)
            v_loading.visibility = View.GONE
            rv_main_list.visibility = View.VISIBLE
        })

        viewModel.selectedVideo.observe(this, Observer {
            it?.let { navigateToMovieActivity(it) }
        })
    }

    private fun navigateToMovieActivity(video: VideoOH) {
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra(PlayerActivity.SELECTED_VIDEO, video.title)
        startActivity(intent)
    }


    override fun initUiEvents() {
        ic_menu.setOnClickListener { drawer_layout.openDrawer(nav_view) }
    }

}