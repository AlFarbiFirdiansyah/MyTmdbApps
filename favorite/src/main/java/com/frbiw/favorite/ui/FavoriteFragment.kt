package com.frbiw.favorite.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.frbiw.core.ui.FavoriteAdapter
import com.frbiw.favorite.R
import com.frbiw.favorite.databinding.FragmentFavoriteBinding
import com.frbiw.favorite.di.DaggerFavoriteComponent
import com.frbiw.mytmdbapps.di.FavoriteModule
import com.frbiw.mytmdbapps.ui.detail.DetailFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject


class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter {movie ->
            findNavController().navigate(
                com.frbiw.mytmdbapps.R.id.action_favoriteFragment_to_detailFragment,
                Bundle().apply {
                    putParcelable(DetailFragment.MOVIE_KEY,movie)
                }
            )
        }
    }
    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: FavoriteViewModel by viewModels{
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),FavoriteModule::class.java
                )
            ).build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPress()
        observeDataFavorite()
        setupRv()
    }

    private fun observeDataFavorite() {
        viewModel.getAllMovieFavorite.observe(viewLifecycleOwner){
            favoriteAdapter.differ.submitList(it)
            if (it.isNotEmpty()){
                binding.rvFav.visibility = View.VISIBLE
            }else{
                binding.rvFav.visibility = View.GONE
            }
        }
    }

    private fun setupRv() {
        binding.rvFav.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    private fun onBackPress() {
        binding.icBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}