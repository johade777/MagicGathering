package com.example.magicgathering.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.magicgathering.R
import com.example.magicgathering.data.model.Card
import com.example.magicgathering.view.CardListActivity
import com.squareup.picasso.Picasso
import java.lang.Exception

class CardAdapter : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    private var cardList: List<Card> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = cardList[position]
        holder.nameView.text = item.name
        val imageUrl = cardList[position].imageUrl?.replace("http", "https") ?: "https://static.wikia.nocookie.net/mtgsalvation_gamepedia/images/f/f8/Magic_card_back.jpg/revision/latest/scale-to-width-down/250?cb=20140813141013"
        Picasso.get().load(imageUrl).into(holder.cardImage)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    fun setMagicCards(cards: List<Card>) {
        cardList = cards
        notifyDataSetChanged()
    }

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.card_name)
        val cardImage: ImageView = view.findViewById(R.id.card_image)
    }
}