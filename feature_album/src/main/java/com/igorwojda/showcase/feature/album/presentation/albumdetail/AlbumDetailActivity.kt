package com.igorwojda.showcase.feature.album.presentation.albumdetail

import com.igorwojda.showcase.feature.album.R
import com.igorwojda.showcase.library.base.presentation.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_album_detail.*

class AlbumDetailActivity : BaseActivity() {

    override val layoutResId = R.layout.activity_album_detail

    override fun onResume() {
        super.onResume()
        underConstructionAnimation.playAnimation()
    }
}
