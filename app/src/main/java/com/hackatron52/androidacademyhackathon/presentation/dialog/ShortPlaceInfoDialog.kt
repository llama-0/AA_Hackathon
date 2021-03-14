package com.hackatron52.androidacademyhackathon.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.FirebaseDatabase
import com.hackatron52.androidacademyhackathon.R
import com.hackatron52.androidacademyhackathon.databinding.PlaceBottomSheetBinding
import com.hackatron52.androidacademyhackathon.domain.models.PlaceDetails
import com.hackatron52.androidacademyhackathon.presentation.viewmodel.ShortPlaceInfoViewModel
import kotlinx.coroutines.flow.collect

class ShortPlaceInfoDialog : BottomSheetDialogFragment() {

    fun interface PlaceRouteListener {
        fun route(placeDetails: PlaceDetails)
    }

    private val shortPlaceInfoViewModel: ShortPlaceInfoViewModel by viewModels()
    private var binding: PlaceBottomSheetBinding? = null
    private var listener: PlaceRouteListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.ThemeOverlay_BottomSheetDialog_Rounded)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return PlaceBottomSheetBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()

        val placeId = arguments?.getString(KEY_PLACE_ID)
        if (placeId != null) {
            shortPlaceInfoViewModel.updateDetails(placeId)
        }
    }

    private fun initObservers() {
        lifecycleScope.launchWhenCreated {
            shortPlaceInfoViewModel.placeDetails.collect { lce ->
                if (lce.isFinishedSuccessfully) {
                    lce.content?.let(::showPlaceShortInfo)
                }
            }
        }
    }

    private fun showPlaceShortInfo(shortInfo: PlaceDetails) {
        binding?.apply {
            fabRoute.setOnClickListener {
                listener?.route(shortInfo)
                dismiss()
            }
            fabLike.setOnClickListener {
                val db = FirebaseDatabase.getInstance()
                db.getReference("Favorites")
                    .updateChildren(mapOf(shortInfo.placeId to shortInfo))
            }

            tvPlaceTitle.text = shortInfo.name
            tvPlaceRating.text = shortInfo.rating.toString()
            shortInfo.photos.firstOrNull()?.let {
                Glide.with(this.imgPlace)
                    .load(it.photoReference)
                    .into(this.imgPlace)
            }
        }
    }

    override fun onDestroy() {
        listener = null
        binding = null
        super.onDestroy()
    }

    companion object {
        private const val KEY_PLACE_ID = "place_id"
        fun newInstance(placeId: String, listener: PlaceRouteListener): ShortPlaceInfoDialog {
            return ShortPlaceInfoDialog().apply {
                arguments = bundleOf(KEY_PLACE_ID to placeId)
                this.listener = listener
            }
        }
    }
}