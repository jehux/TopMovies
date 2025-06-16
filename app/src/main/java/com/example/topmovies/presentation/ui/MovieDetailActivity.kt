package com.example.topmovies.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.topmovies.databinding.ActivityMovieDetailBinding
import com.example.topmovies.presentation.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding

    private val movieDetailViewModel: MovieDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra("movieId", 278)
        movieDetailViewModel.fetchMovie(movieId)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    movieDetailViewModel.movieModel.collect { moviewDetail ->
                        binding.textTitle.text = moviewDetail?.title
                        binding.textRating.text = String.format("%.1f", moviewDetail?.voteAverage)
                        binding.textPopularity.text = "${moviewDetail?.popularity}"
                        binding.textDescription.text = "${moviewDetail?.overview}"
                        binding.textReleaseDate.text = moviewDetail?.releaseDate
                        Glide.with(this@MovieDetailActivity)
                            .load("https://image.tmdb.org/t/p/w300${moviewDetail?.backdropPath}")
                            .into(binding.imageCover)
                    }
                }
                launch {
                    movieDetailViewModel.posterImage.collect { imageFile ->
                        Glide.with(this@MovieDetailActivity)
                            .load(imageFile)
                            .into(binding.imagePoster)
                    }
                }
                launch {
                    movieDetailViewModel.error.collect { error ->
                        error?.let {
                            Toast.makeText(this@MovieDetailActivity, it, Toast.LENGTH_LONG).show()
                        }
                    }
                }
                launch {
                    movieDetailViewModel.isLoading.collect { isLoading ->
                        isLoading?.let {
                            if (isLoading) {
                                binding.detailLoader.visibility = View.VISIBLE
                            } else {
                                binding.detailLoader.visibility = View.GONE
                            }

                        }
                    }
                }
            }
        }
    }
}
