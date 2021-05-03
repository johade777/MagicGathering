package com.example.magicgathering.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.magicgathering.R
import com.example.magicgathering.data.model.Card
import com.example.magicgathering.view.CardDetailsActivity
import com.squareup.picasso.Picasso

class CardAdapter : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    private var cardList: List<Card> = listOf()

    private val onClickListener: View.OnClickListener = View.OnClickListener { v ->
        val card = v.tag as Card
        val intent = Intent(v.context, CardDetailsActivity::class.java).apply {
            putExtra("card_id", card.id)
        }
        v.context.startActivity(intent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = cardList[position]
        holder.nameView.text = item.name
        val imageUrl = cardList[position].imageUrl?.replace("http", "https") ?: "https://static.wikia.nocookie.net/mtgsalvation_gamepedia/images/f/f8/Magic_card_back.jpg/revision/latest/scale-to-width-down/250?cb=20140813141013"
        Picasso.get().load(imageUrl).into(holder.cardImage)

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
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