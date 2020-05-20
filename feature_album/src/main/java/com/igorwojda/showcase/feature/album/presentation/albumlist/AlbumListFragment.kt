package com.igorwojda.showcase.feature.album.presentation.albumlist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.igorwojda.showcase.app.presentation.NavHostActivity
import com.igorwojda.showcase.feature.album.R
import com.igorwojda.showcase.feature.album.presentation.albumdetail.AlbumDetailActivity
import com.igorwojda.showcase.feature.album.presentation.albumlist.recyclerview.AlbumAdapter
import com.igorwojda.showcase.feature.album.presentation.albumlist.recyclerview.GridAutofitLayoutManager
import com.igorwojda.showcase.library.base.presentation.extension.observe
import com.igorwojda.showcase.library.base.presentation.fragment.BaseContainerFragment
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.fragment_album_list.*
import org.kodein.di.generic.instance

class AlbumListFragment : BaseContainerFragment() {

    private val viewModel: AlbumListViewModel by instance()

    override val layoutResourceId = R.layout.fragment_album_list

    private val albumAdapter: AlbumAdapter by instance()

    private val stateObserver = Observer<AlbumListViewModel.ViewState> {
        albumAdapter.albums = it.albums
        progressBar.visible = it.isLoading
        errorAnimation.visible = it.isError
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = checkNotNull(context)

        albumAdapter.setOnDebouncedClickListener {
            // Navigate to activity using a good old intent
            val intent = Intent(requireActivity(), AlbumDetailActivity::class.java)
            startActivity(intent)

            // Navigate to details fragment using the navigation component
//            viewModel.navigateToAlbumDetails(it.artist, it.name, it.mbId)
        }

        recyclerView.apply {
            setHasFixedSize(true)
            val columnWidth = context.resources.getDimension(R.dimen.image_size).toInt()
            layoutManager =
                GridAutofitLayoutManager(
                    context,
                    columnWidth
                )
            adapter = albumAdapter
        }

        observe(viewModel.stateLiveData, stateObserver)
        viewModel.loadData()
    }
}
