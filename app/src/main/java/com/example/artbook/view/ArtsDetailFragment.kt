package com.example.artbook.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.artbook.R
import com.example.artbook.databinding.FragmentArtsBinding
import com.example.artbook.databinding.FragmentArtsDetailBinding
import com.example.artbook.util.Status
import com.example.artbook.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtsDetailFragment @Inject constructor(
    val glide : RequestManager
) : Fragment(R.layout.fragment_arts_detail) {

    lateinit var viewModel : ArtViewModel

    private var fragmentBind : FragmentArtsDetailBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        fragmentBind = FragmentArtsDetailBinding.bind(view)

        subscribeToObservers()

        fragmentBind!!.artImageView.setOnClickListener {
            findNavController().navigate(ArtsDetailFragmentDirections.actionArtsDetailFragmentToArtsApiFragment())
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        fragmentBind!!.saveButton.setOnClickListener {
            viewModel.makeArt(fragmentBind!!.nameText.text.toString(),
                fragmentBind!!.artistText.text.toString(),
                fragmentBind!!.yearText.text.toString())

        }

    }

    private fun subscribeToObservers() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            fragmentBind?.let {
                glide.load(url).into(it.artImageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireActivity(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                    viewModel.resetInsertArtMsg()
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                }

                Status.LOADING -> {

                }
            }
        })
    }

    override fun onDestroyView() {
        fragmentBind = null
        super.onDestroyView()
    }
}