package com.example.magicgathering.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
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
    private lateinit var cardNameTextView: TextView
    private lateinit var cardImageImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_details)
        cardNameTextView = findViewById(R.id.card_name)
        cardImageImageView = findViewById(R.id.card_image)

        cardId = intent.getStringExtra("card_id")!!
        cardIdTextView.text = cardId

        setupViewModel()
        setupObservers()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(ApiServiceHelper(RetrofitBuilder.magicService))
        ).get(CardDetailsViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.getCardById(cardId).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
//                        progressBar.visibility = View.GONE
                        resource.data?.let { cardResponse -> displayCardDetails(cardResponse) }
                    }
                    Status.ERROR -> {
//                        progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
//                        progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun displayCardDetails(cardResponse: GetCardByIdResponse){
        val card = cardResponse.card

        cardNameTextView.text = card.name
        val imageUrl = card.imageUrl?.replace("http", "https") ?: "https://static.wikia.nocookie.net/mtgsalvation_gamepedia/images/f/f8/Magic_card_back.jpg/revision/latest/scale-to-width-down/250?cb=20140813141013"
        Picasso.get().load(imageUrl).into(cardImageImageView)
    }
}