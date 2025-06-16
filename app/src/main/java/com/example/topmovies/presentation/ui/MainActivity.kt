package com.example.topmovies.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.topmovies.databinding.ActivityMainBinding
import com.example.topmovies.domain.model.MovieWithImage
import com.example.topmovies.presentation.ui.adapter.MovieAdapter
import com.example.topmovies.presentation.viewmodel.TopRatedMoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val topRatedMoviesViewModel: TopRatedMoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        topRatedMoviesViewModel.fetchTopRatedMovies()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    combine(
                        topRatedMoviesViewModel.moviesModel,
                        topRatedMoviesViewModel.imageFiles
                    ) { movies, images ->
                        movies.mapIndexed { index, movie ->
                            MovieWithImage(movie, images.getOrNull(index))
                        }
                    }.collect { moviesWithImages ->
                        binding.recyclerNowShowing.apply {
                            layoutManager = LinearLayoutManager(
                                context, LinearLayoutManager.HORIZONTAL, false
                            )
                            adapter = MovieAdapter(moviesWithImages.take(10)) { movieId ->
                                val intent = Intent(context, MovieDetailActivity::class.java).apply {
                                    putExtra("movieId", movieId)
                                }
                                startActivity(intent)
                            }
                        }
                    }
                }
                launch {
                    topRatedMoviesViewModel.error.collect { error ->
                        error?.let {
                            Toast.makeText(this@MainActivity, it, Toast.LENGTH_LONG).show()
                        }
                    }
                }
                launch {
                    topRatedMoviesViewModel.isLoading.collect { isLoading ->
                        isLoading?.let {
                            binding.mainLoader.isGone = !it
                        }
                    }
                }
            }
        }
        binding.recyclerNowShowing.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val centerX = recyclerView.width / 2
                for (i in 0 until recyclerView.childCount) {
                    val child = recyclerView.getChildAt(i)
                    val childCenterX = (child.left + child.right) / 2
                    val distanceFromCenter = abs(centerX - childCenterX)
                    val scale = 1 - (distanceFromCenter.toFloat() / centerX) * 0.2f
                    child.scaleX = scale
                    child.scaleY = scale
                }
            }
        })
        binding.btnCloseSession.setOnClickListener {
            topRatedMoviesViewModel.logAOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}