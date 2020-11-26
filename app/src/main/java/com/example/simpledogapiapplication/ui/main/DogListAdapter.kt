package com.example.simpledogapiapplication.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.simpledogapiapplication.R
import com.example.simpledogapiapplication.data.models.Breed
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_dog_list_item.view.*

class DogListAdapter(private val dogListner: DogClickListner) :
        RecyclerView.Adapter<DogViewHolder>() {

    var breeds: List<Breed> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder =
        DogViewHolder.from(parent)

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) =
        holder.bind(breeds[position], dogListner)

    override fun getItemCount(): Int = breeds.size
}

class DogViewHolder private constructor(override val containerView: View):
        RecyclerView.ViewHolder(containerView), LayoutContainer {
    companion object {
        fun from(parent: ViewGroup): DogViewHolder {
            val layout = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_dog_list_item, parent, false)
            return DogViewHolder(layout)
        }
    }

    fun bind(breed: Breed, dogListener: DogClickListner) {
        itemView.itemTitle.text = breed.title

        containerView.setOnClickListener {
            dogListener.onDogClick(breed)
        }

        val drawable = if(breed.isFaved) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24

        itemView.faveButton.setImageResource(drawable)
        itemView.faveButton.setOnClickListener {
            dogListener.onFaveClick(breed)
        }
    }
}

//class DogClickListner(private val listner: (breed: Breed) -> Unit) {
//    fun onDogClick(breed: Breed) = listner(breed)
//}

interface DogClickListner{
    fun onDogClick(breed: Breed)
    fun onFaveClick(breed: Breed)
}