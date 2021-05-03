package com.example.magicgathering.view

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.magicgathering.R
import com.example.magicgathering.adapter.CardAdapter
import com.example.magicgathering.data.api.ApiServiceHelper
import com.example.magicgathering.data.api.RetrofitBuilder
import com.example.magicgathering.data.model.GetCardsResponse
import com.example.magicgathering.util.MyApplicationContext
import com.example.magicgathering.util.Status
import com.example.magicgathering.viewmodel.CardListViewModel
import com.example.magicgathering.viewmodel.ViewModelFactory


class CardListActivity : AppCompatActivity() {
    private lateinit var viewModel: CardListViewModel
    private lateinit var cardRecyclerView: RecyclerView
    private lateinit var adapter: CardAdapter
    private lateinit var fetchCardsButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardRecyclerView = findViewById(R.id.card_recycler)
        fetchCardsButton = findViewById(R.id.fetch_button)
        progressBar = findViewById(R.id.progress_bar)
        MyApplicationContext.setContext(this)

        setupViewModel()
        setupView()

        fetchCardsButton.setOnClickListener {
            setupObservers()
        }
    }

    private fun setupView(){
        cardRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CardAdapter()
        cardRecyclerView.addItemDecoration(
                DividerItemDecoration(
                        cardRecyclerView.context,
                        (cardRecyclerView.layoutManager as LinearLayoutManager).orientation
                )
        )
        cardRecyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(ApiServiceHelper(RetrofitBuilder.magicService))
        ).get(CardListViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.getCards().observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        resource.data?.let { cardList -> updateCards(cardList) }
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

    private fun updateCards(cards: GetCardsResponse) {
        adapter.setMagicCards(cards.cardList)
    }
}