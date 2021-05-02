package com.example.magicgathering.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.magicgathering.data.model.GetCardResponse
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.magicgathering.R
import com.example.magicgathering.adapter.CardAdapter
import com.example.magicgathering.data.api.ApiServiceHelper
import com.example.magicgathering.data.api.RetrofitBuilder
import com.example.magicgathering.data.model.Card
import com.example.magicgathering.util.Status
import com.example.magicgathering.viewmodel.CardListViewModel
import com.example.magicgathering.viewmodel.ViewModelFactory

class CardListActivity : AppCompatActivity() {
    private lateinit var viewModel: CardListViewModel
    private lateinit var cardRecyclerView: RecyclerView
    private lateinit var adapter: CardAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cardRecyclerView = findViewById(R.id.card_recycler)

        setupViewModel()
        setupView()
        setupObservers()
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
        viewModel.getCards().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
//                        progressBar.visibility = View.GONE
                        resource.data?.let { cardList -> updateCards(cardList) }
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

    private fun updateCards(cards: GetCardResponse) {
        adapter.setMagicCards(cards.cardList)
//        adapter.apply {
//            addUsers(users)
//            notifyDataSetChanged()
//        }
    }
}