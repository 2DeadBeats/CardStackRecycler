package com.twodeadbeats.cardstackrecycler

import androidx.recyclerview.widget.RecyclerView

internal class CenterScrollListener : RecyclerView.OnScrollListener() {

    private var autoSet = true

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        val layoutManager = recyclerView.layoutManager
        check(layoutManager is CardStackLayoutManager) {
            "RecyclerView's layout manager must be an instance of CardStackLayoutManager!"
        }
        if (RecyclerView.SCROLL_STATE_IDLE == newState) {
            recyclerView.smoothScrollBy(layoutManager.getCenterViewOffset(), 0)
            autoSet = true
        }
        if (RecyclerView.SCROLL_STATE_DRAGGING == newState || RecyclerView.SCROLL_STATE_SETTLING == newState) {
            autoSet = false
        }
    }
}