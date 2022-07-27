package me.mohekkus.olsera_testproject.ui.form

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.viewModelScope
import com.adevinta.leku.LocationPickerActivity
//import com.adevinta.leku.LocationPickerActivity
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.mohekkus.olsera_testproject.R
import me.mohekkus.olsera_testproject.database.entity.RetailEntity
import me.mohekkus.olsera_testproject.databinding.FragmentRetailFormBinding
import me.mohekkus.olsera_testproject.ui.list.RetailViewModel

@AndroidEntryPoint
class FormFragment: Fragment(), OnMapReadyCallback {

    private var _binding: FragmentRetailFormBinding? = null

    private val viewmodel : RetailViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val defLatLng = LatLng(-6.2278962,106.7707808)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_retail_form, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            if (arguments?.getInt("id") == null) {
                bDelete.visibility = View.GONE
                textView5.text = "Add Location"
                rbActive.apply {
                    isChecked = true
                    setTextColor(Color.WHITE)
                }
            } else {
                textView5.text = "Edit Location"

                val data = arguments?.getSerializable("data") as RetailEntity
                data.apply {
                    retName.setValue(name)
                    retAdd.setValue(address)
                    retCity.setValue(city)
                    retZip.setValue(zipcode)
                }

                bDelete.setOnClickListener {
                    viewmodel.delete(data.id)
                    requireActivity().onBackPressed()
                }
            }


            rbActive.setOnCheckedChangeListener { compoundButton, b ->
                if (b)
                    compoundButton.setTextColor(Color.WHITE)
                else
                    compoundButton.setTextColor(Color.BLACK)
            }

            rbInactive.setOnCheckedChangeListener { compoundButton, b ->
                if (b)
                    compoundButton.setTextColor(Color.WHITE)
                else
                    compoundButton.setTextColor(Color.BLACK)
            }

            ivClose.setOnClickListener {
                requireActivity().onBackPressed()
            }

            bClose.setOnClickListener {
                requireActivity().onBackPressed()
            }

            mapButton.setOnClickListener {
                Snackbar.make(this.root, "Need API KEY For Set/Get", Snackbar.LENGTH_SHORT).show()
                val locationPickerIntent = LocationPickerActivity.Builder()
                    .withLocation(defLatLng)
                    .withGeolocApiKey("0")
                    .build(requireContext())


                val MAP_BUTTON_REQUEST_CODE = 2
                startActivityForResult(locationPickerIntent, MAP_BUTTON_REQUEST_CODE)
            }

            bSave.setOnClickListener {
                Snackbar.make(this.root, "Data Added", Snackbar.LENGTH_SHORT).show()
                val data =
                    RetailEntity(
                        0,
                        when {
                            binding.rbActive.isChecked -> true
                            else -> false
                        },
                        retName.getValue(),
                        retAdd.getValue(),
                        retCity.getValue(),
                        retZip.getValue(),
                        -6.2278962,106.7707808
                    )
                if (viewmodel.addItem.value == true)
                    viewmodel.viewModelScope.launch {
                        viewmodel.insert(
                            data
                        )
                    }
                requireActivity().onBackPressed()

            }
        }
    }

    override fun onStart() {
        super.onStart()
        (childFragmentManager.findFragmentById(R.id.my_map) as SupportMapFragment).apply {
            getMapAsync(
                this@FormFragment
            )
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        val latLng = defLatLng
        p0.apply {
            addMarker(
                MarkerOptions().position(latLng)
            )
            moveCamera(CameraUpdateFactory.newLatLng(latLng))
        }
    }
}