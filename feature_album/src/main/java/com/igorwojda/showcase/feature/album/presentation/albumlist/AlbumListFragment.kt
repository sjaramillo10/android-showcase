package com.igorwojda.showcase.feature.album.presentation.albumlist

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.igorwojda.showcase.feature.album.R
import com.igorwojda.showcase.feature.album.presentation.albumdetail.AlbumDetailActivity
import com.igorwojda.showcase.feature.album.presentation.albumdetail.AlbumDetailFragment2
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
//            val intent = Intent(requireActivity(), AlbumDetailActivity::class.java)
//            startActivity(intent)

//            navigateToActivityUsingNavController()

//            navigateToFragmentUsingNavController()

            // Navigate to details fragment using the navigation component
            viewModel.navigateToAlbumDetails(it.artist, it.name, it.mbId)
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

    private fun navigateToActivityUsingNavController() {
        val navController = findNavController()

        navController.graph.addDestination(
            navController.navigatorProvider.getNavigator(ActivityNavigator::class.java)
                .createDestination().apply {
                    // TODO this hack id works but is not pretty, find out a better way of doing it
                    id = R.id.underConstructionTextView
                    setComponentName(ComponentName(requireContext(), AlbumDetailActivity::class.java))
                }
        )

        navController.navigate(R.id.underConstructionTextView)
    }

    private fun navigateToFragmentUsingNavController() {
        val navController = findNavController()

        navController.graph.addDestination(
            navController.navigatorProvider.getNavigator(FragmentNavigator::class.java)
                .createDestination().apply {
                    // TODO this hack id works but is not pretty, find out a better way of doing it
                    id = R.id.underConstructionAnimation
                    className = AlbumDetailFragment2::class.java.name
                }
        )

        navController.navigate(R.id.underConstructionAnimation)
    }
}
