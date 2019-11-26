package com.twodeadbeats.cardstackrecycler.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val cardsAdapter by lazy { CardsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cardStackRecycler.adapter = cardsAdapter
        cardsAdapter.cards = createCards()
    }

    private fun createCards(): List<CardModel> {
        val cards = mutableListOf<CardModel>()
        val drawables = listOf(
            R.drawable.card_one,
            R.drawable.card_two,
            R.drawable.card_three,
            R.drawable.card_four,
            R.drawable.card_five,
            R.drawable.card_six
        )
        drawables.forEach { drawable ->
            cards.add(CardModel(drawable))
        }
        return cards
    }
}
