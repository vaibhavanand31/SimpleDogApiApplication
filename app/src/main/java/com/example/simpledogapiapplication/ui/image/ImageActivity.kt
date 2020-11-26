package com.example.simpledogapiapplication.ui.image

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.simpledogapiapplication.DogApplication
import com.example.simpledogapiapplication.R
import com.example.simpledogapiapplication.data.ImageRepository
import com.example.simpledogapiapplication.data.models.Breed
import com.example.simpledogapiapplication.ui.BundleKeys
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity() {

    private val viewModel by viewModels<ImageViewModel> {
        val apiService = (applicationContext as DogApplication).serviceLocator.apiService
        val repository = ImageRepository(apiService)
        val breed = intent.getSerializableExtra(BundleKeys.BREED_INFO) as Breed
        ImageViewModelFactory(repository, breed)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.dogImageUrl.observe(this){
            Glide.with(this).load(it).centerInside().into(dogImageView)
        }

        viewModel.shareDogImage.observe(this) {
            it?.let {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, it)
                }
                startActivity(Intent.createChooser(intent, getString(R.string.share)))
                viewModel.onShareComplete()
            }
        }

        viewModel.browseDogImage.observe(this){
            it?.let {
                val uri = Uri.parse(it)
                val intent = Intent(Intent.ACTION_VIEW, uri)

                intent.resolveActivity(packageManager)?.let {
                    startActivity(intent)
                }
                viewModel.onBrowseComplete()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.image_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.share -> {
                viewModel.share()
                true
            }
            R.id.open_in_browser -> {
                viewModel.openInBrowser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}