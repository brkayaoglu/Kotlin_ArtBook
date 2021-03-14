package com.example.artbook.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.artbook.R
import com.example.artbook.adapter.ArtRecyclerAdapter
import com.example.artbook.databinding.FragmentArtsBinding
import com.example.artbook.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtsFragment  @Inject constructor(
    val artRecyclerAdapter: ArtRecyclerAdapter
)  : Fragment(R.layout.fragment_arts) {

    lateinit var viewModel : ArtViewModel

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt = artRecyclerAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)

        }

    }



    private var fragmentBind : FragmentArtsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        fragmentBind = FragmentArtsBinding.bind(view)

        subscribeToObservers()

        fragmentBind!!.recyclerViewArtFragment.adapter = artRecyclerAdapter

        fragmentBind!!.recyclerViewArtFragment.layoutManager = LinearLayoutManager(requireContext())


        ItemTouchHelper(swipeCallBack).attachToRecyclerView(fragmentBind!!.recyclerViewArtFragment)

        fragmentBind!!.floatingActionButtonArtsFragment.setOnClickListener {
            findNavController().navigate(ArtsFragmentDirections.actionArtsFragmentToArtsDetailFragment())
        }



    }

    private fun subscribeToObservers() {
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            artRecyclerAdapter.arts = it
        })
    }

    override fun onDestroyView() {
        fragmentBind = null
        super.onDestroyView()
    }
}