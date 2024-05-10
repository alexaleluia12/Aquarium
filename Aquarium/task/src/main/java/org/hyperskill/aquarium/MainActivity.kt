package org.hyperskill.aquarium

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso

class PageViewHolder(iV: View) : RecyclerView.ViewHolder(iV)

class SwipeAdapter(
    val imageAnimals: List<String>,
    val nameAnimals: List<String>,
    val descriptionAnimals: List<String>,
) : RecyclerView.Adapter<PageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        return PageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.page_item, parent, false)
        )
    }

    override fun getItemCount() = imageAnimals.size

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        require(position in imageAnimals.indices) { "$position not exits" }

        holder.itemView.run {
            findViewById<TextView>(R.id.tv_name)?.text = nameAnimals[position]
            findViewById<TextView>(R.id.tv_description)?.text = descriptionAnimals[position]
            val imgTarget = findViewById<ImageView>(R.id.image_view)
            loadImageFrom(imageAnimals[position], imgTarget)
        }
    }

    private fun loadImageFrom(url: String, imageTarget: ImageView) {
        Picasso.get()
            .load(url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.error)
            .into(imageTarget)
    }

}


class MainActivity : AppCompatActivity() {
    var imageAnimals = images
    var nameAnimals = names
    var descriptionAnimals = descriptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intent.extras?.getSerializable("imageAnimals")?.let {
            imageAnimals = it as List<String>
        }
        intent.extras?.getSerializable("nameAnimals")?.let {
            nameAnimals = it as List<String>
        }
        intent.extras?.getSerializable("descriptionAnimals")?.let {
            descriptionAnimals = it as List<String>
        }

        val viewPager2 = findViewById<ViewPager2>(R.id.viewpager2)
        val swapAdapter = SwipeAdapter(
            imageAnimals = imageAnimals,
            nameAnimals = nameAnimals,
            descriptionAnimals = descriptionAnimals,
        )
        viewPager2.adapter = swapAdapter

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager2) {tab, i ->
            tab.text = nameAnimals[i]
        }.attach()

    }
}
