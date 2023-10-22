package com.notes.taskapp.UI.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.notes.taskapp.databinding.RowItemBinding
import com.notes.taskapp.models.ApiData


class GameAdapter(private val apiData: ApiData) : RecyclerView.Adapter<GameAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return apiData.data!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    Glide.with(holder.image.context).load(apiData.data?.get(position)?.thumb_image).into(holder.image)
        holder.title.text = apiData.data?.get(position)?.name
        holder.title.text = apiData.data?.get(position)?.name

        holder.downloadBtn.setOnClickListener{
            try {
                val viewIntent = Intent(
                    "android.intent.action.VIEW",
                    Uri.parse(apiData.data?.get(position)?.app_link)
                )
                holder.title.context.startActivity(viewIntent)
            } catch (e: Exception) {
                Toast.makeText(
                    holder.title.context, "Unable to Connect Try Again...",
                    Toast.LENGTH_LONG
                ).show()
                e.printStackTrace()
            }
        }

    }


    class MyViewHolder(binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.gameImage
        val title = binding.gameTitle
        val downloadBtn = binding.gameDownloadsBtn
    }
}