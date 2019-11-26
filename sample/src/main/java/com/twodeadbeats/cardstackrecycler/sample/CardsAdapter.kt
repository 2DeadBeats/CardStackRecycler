package com.twodeadbeats.cardstackrecycler.sample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_card.view.*

class CardsAdapter : RecyclerView.Adapter<CardsAdapter.CardViewHolder>() {

    private val _cards = mutableListOf<CardModel>()
    var cards: List<CardModel>
        get() = _cards.toList()
        set(newCards) {
            _cards.apply {
                clear()
                addAll(newCards)
            }
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(_cards[position])
    }

    override fun getItemCount(): Int = _cards.size

    class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(card: CardModel) {
            with(itemView) {
                val drawable = ContextCompat.getDrawable(context, card.drawable)
                imageView.setImageDrawable(drawable)
            }
        }
    }
}
