package com.example.magicgathering.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.magicgathering.R
import com.example.magicgathering.data.api.ApiServiceHelper
import com.example.magicgathering.data.api.RetrofitBuilder
import com.example.magicgathering.data.model.Card
import com.example.magicgathering.data.model.GetCardByIdResponse
import com.example.magicgathering.util.Status
import com.example.magicgathering.viewmodel.CardDetailsViewModel
import com.example.magicgathering.viewmodel.CardListViewModel
import com.example.magicgathering.viewmodel.ViewModelFactory
import com.squareup.picasso.Picasso

class CardDetailsActivity : AppCompatActivity() {
    private lateinit var viewModel: CardDetailsViewModel
    private lateinit var cardId: String
    private lateinit var progressBar: ProgressBar
    private lateinit var cardNameTextView: TextView
    private lateinit var cardImageImageView: ImageView
    private lateinit var cardTypeTextView: TextView
    private lateinit var cardManaTextView: TextView
    private lateinit var cardSetTextView: TextView
    private lateinit var cardTextTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_details)
        setSupportActionBar(findViewById(R.id.detail_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        progressBar = findViewById(R.id.progress_bar)
        cardNameTextView = findViewById(R.id.card_name)
        cardTypeTextView = findViewById(R.id.card_type)
        cardManaTextView = findViewById(R.id.card_mana)
        cardSetTextView = findViewById(R.id.card_set)
        cardTextTextView = findViewById(R.id.card_text)
        cardImageImageView = findViewById(R.id.card_image)

        cardId = intent.getStringExtra("card_id")!!

        setupViewModel()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(ApiServiceHelper(RetrofitBuilder.magicService))
        ).get(CardDetailsViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.getCardById(cardId).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        resource.data?.let { cardResponse -> displayCardDetails(cardResponse) }
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun displayCardDetails(cardResponse: GetCardByIdResponse){
        val card = cardResponse.card

        cardNameTextView.text = card.name
        cardTypeTextView.text = "Type: ${card.type}"
        cardManaTextView.text = "Mana Cost: ${card.manaCost}"
        cardSetTextView.text = "Set: ${card.set}"
        cardTextTextView.text = card.text
        val imageUrl = card.imageUrl?.replace("http", "https") ?: "https://static.wikia.nocookie.net/mtgsalvation_gamepedia/images/f/f8/Magic_card_back.jpg/revision/latest/scale-to-width-down/250?cb=20140813141013"
        Picasso.get().load(imageUrl).into(cardImageImageView)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}