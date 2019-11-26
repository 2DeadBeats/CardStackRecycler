package com.twodeadbeats.cardstackrecycler

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class CardStackRecycler(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {

    private var paginationEnabled = false
    private var centerScrollEnabled = false
    private var maxCards = 3

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CardStackRecycler,
            0, 0
        )
        try {
            paginationEnabled = typedArray.getBoolean(
                R.styleable.CardStackRecycler_paginationEnabled,
                paginationEnabled
            )
            centerScrollEnabled = typedArray.getBoolean(
                R.styleable.CardStackRecycler_centerScrollEnabled,
                centerScrollEnabled
            )
            maxCards = typedArray.getInteger(
                R.styleable.CardStackRecycler_maxCards,
                maxCards
            )
        } finally {
            typedArray.recycle()
        }
        this.layoutManager =
            CardStackLayoutManager(paginationEnabled, maxCards)
        if (centerScrollEnabled) {
            this.addOnScrollListener(CenterScrollListener())
        }
        overScrollMode = OVER_SCROLL_NEVER
    }
}