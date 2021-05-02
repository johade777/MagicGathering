package com.example.magicgathering.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.magicgathering.R
import com.example.magicgathering.data.model.Card

class CardAdapter : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    private var cardList: List<Card> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    fun setMagicCards(cards: List<Card>) {
        cardList = cards
        notifyDataSetChanged()
    }

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.card_id)
        val contentView: TextView = view.findViewById(R.id.card_name)
    }
}