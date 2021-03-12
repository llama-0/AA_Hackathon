package com.hackatron52.androidacademyhackathon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PlaceDetailsFragment : Fragment() {

    companion object {
        private const val PLACE_KEY = "Place_item"

        fun newInstance(id: Int): PlaceDetailsFragment {
            val args = Bundle()
            args.putInt(PLACE_KEY, id)
            val fragment = PlaceDetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerPhoto: RecyclerView = view.findViewById(R.id.recyclerPhoto)
        val placeName = view.findViewById<TextView>(R.id.titlePlace)
        val addressPlace = view.findViewById<TextView>(R.id.addressPlace)
        val descriptionPlace = view.findViewById<TextView>(R.id.descriptionPlace)
        val recyclerReview = view.findViewById<TextView>(R.id.recyclerReview)
        val fabFavorite = view.findViewWithTag<FloatingActionButton>(R.id.fabFavorite)
        val fabRoute = view.findViewById<FloatingActionButton>(R.id.fabRoute)

        fabFavorite.setOnClickListener {
            //TODO
        }

        fabRoute.setOnClickListener {
            //TODO
        }
    }
}