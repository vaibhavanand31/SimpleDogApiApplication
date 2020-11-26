// Name: Vaibhav Anand
// Student ID : A00219501

package com.example.simpledogapiapplication.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.simpledogapiapplication.DogApplication
import com.example.simpledogapiapplication.R
import com.example.simpledogapiapplication.data.DogRepository
import com.example.simpledogapiapplication.data.models.Breed
import com.example.simpledogapiapplication.ui.BundleKeys
import com.example.simpledogapiapplication.ui.image.ImageActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val dogViewModel by viewModels<DogViewModel>{
        val apiService = (applicationContext as DogApplication).serviceLocator.apiService
        val database = (applicationContext as DogApplication).serviceLocator.database
        val dogRepository = DogRepository(apiService, database)
        DogViewModelFactory(dogRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = DogListAdapter(object: DogClickListner {
            override fun onDogClick(breed: Breed) {
                dogViewModel.onDogClick(breed)
            }

            override fun onFaveClick(breed: Breed) {
                dogViewModel.onFaveClick(breed)
            }
        })

        dogRecyclerView.adapter = adapter

        dogViewModel.dogLists.observe(this){
            adapter.breeds = it
            adapter.notifyDataSetChanged()
        }

        dogViewModel.navigatetoImage.observe(this){
            it?.let {
                val intent = Intent(this, ImageActivity::class.java).apply {
                    putExtra(BundleKeys.BREED_INFO, it)
                }
                startActivity(intent)
                dogViewModel.onNavigateToDetailsComplete()
            }
        }
    }
}

