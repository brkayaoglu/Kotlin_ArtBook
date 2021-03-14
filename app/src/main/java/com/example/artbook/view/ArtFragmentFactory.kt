package com.example.artbook.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.artbook.adapter.ArtRecyclerAdapter
import com.example.artbook.adapter.ImageRecyclerAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter,
    private val glide : RequestManager,
    private val artRecyclerAdapter: ArtRecyclerAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ArtsApiFragment::class.java.name -> ArtsApiFragment(imageRecyclerAdapter)
            ArtsDetailFragment::class.java.name -> ArtsDetailFragment(glide)
            ArtsFragment::class.java.name -> ArtsFragment(artRecyclerAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}