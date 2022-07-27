package me.mohekkus.olsera_testproject.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import me.mohekkus.olsera_testproject.R
import me.mohekkus.olsera_testproject.database.entity.RetailEntity
import me.mohekkus.olsera_testproject.databinding.FragmentRetailListBinding
import me.mohekkus.olsera_testproject.ui.list.adapter.ListRetailAdapter
import me.mohekkus.olsera_testproject.ui.list.adapter.PagingAdapter
import javax.inject.Inject

@AndroidEntryPoint
class ListRetailFragment : Fragment() {

    private var _binding: FragmentRetailListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewmodel : RetailViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)
    @Inject lateinit var pageAdapter: PagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_retail_list, container, false)

        (activity as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbar)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewmodel.apply {
                rvRetail.apply {
                    adapter = pageAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                }

                lifecycleScope.launchWhenCreated {
                    getAllRetail.collectLatest {
                        pageAdapter.submitData(lifecycle, it)

                        leftText = allStatus.value + "(${pageAdapter.itemCount})"
                    }
                }

                rightText = inactiveStatus.value
                midText = activeStatus.value
                leftText = allStatus.value
            }

            fab.setOnClickListener {
                findNavController().navigate(R.id.formFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
