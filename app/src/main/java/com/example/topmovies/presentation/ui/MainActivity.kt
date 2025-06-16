package com.example.topmovies.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
                            if (it) {
                                binding.mainLoader.visibility = View.VISIBLE
                            } else {
                                binding.mainLoader.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
        binding.recyclerNowShowing.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val centerX = recyclerView.width / 2
                var minDistance = Int.MAX_VALUE
                var closestView: View? = null
                var closestPosition = -1

                for (i in 0 until recyclerView.childCount) {
                    val child = recyclerView.getChildAt(i)
                    val childCenterX = (child.left + child.right) / 2
                    val distanceFromCenter = abs(centerX - childCenterX)

                    // Escalado visual
                    val scale = 1 - (distanceFromCenter.toFloat() / centerX) * 0.2f
                    child.scaleX = scale
                    child.scaleY = scale

                    // Encontrar el más cercano al centro
                    if (distanceFromCenter < minDistance) {
                        minDistance = distanceFromCenter
                        closestView = child
                        closestPosition = recyclerView.getChildAdapterPosition(child)
                    }
                }

                // Aquí tienes el View más cercano al centro y su posición
                closestView?.let {
                    val adapter = recyclerView.adapter as? MovieAdapter
                    val movie = adapter?.getItemAt(closestPosition)
                    Glide.with(baseContext)
                        .load(movie?.imageFile)
                        .into(binding.imageViewManBrackground)
                }
            }
        })
        binding.btnCloseSession.setOnClickListener {
            topRatedMoviesViewModel.logAOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val radius = 20f
        val decorView = window.decorView
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
        val windowBackground = decorView.background
        binding.blurViewHome.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurRadius(radius)

    }
}