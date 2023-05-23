package com.fuadhev.fotohunt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fuadhev.fotohunt.databinding.SFotoItemBinding
import com.fuadhev.fotohunt.model.Hit
import com.squareup.picasso.Picasso

class SearchFotoAdapter(private val imageClickListener: ImageClickListener,private var searchfotosList:List<Hit>):RecyclerView.Adapter<SearchFotoAdapter.ViewHolder>() {


    inner class ViewHolder(val view:SFotoItemBinding):RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = SFotoItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
      return searchfotosList.size
    }

    fun updateFotos(newFotoList:List<Hit>){
        this.searchfotosList=newFotoList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val foto=searchfotosList[position]
        val b=holder.view

        Picasso.get().load(foto.largeImageURL).into(b.image)

        b.imgConts.setOnClickListener {

            imageClickListener.imageClickListener(foto)
        }
    }


}
interface ImageClickListener{
    fun imageClickListener(imageInfo:Hit)
}