package com.igorwojda.showcase.feature.album.presentation.albumdetail

import com.igorwojda.showcase.feature.album.R
import com.igorwojda.showcase.library.base.presentation.fragment.BaseContainerFragment
import kotlinx.android.synthetic.main.activity_album_detail.*

class AlbumDetailFragment2 : BaseContainerFragment() {

    override val layoutResourceId = R.layout.activity_album_detail

    override fun onResume() {
        super.onResume()
        underConstructionAnimation.playAnimation()
    }
}
