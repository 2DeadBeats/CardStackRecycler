package com.twodeadbeats.cardstackrecycler

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.*

internal class CardStackLayoutManager(
    private val paginationEnabled: Boolean = true,
    private val maxCards: Int = 3
) : RecyclerView.LayoutManager() {

    private var currentPosition = 0
    private var totalScroll = 0
    private var orientationHelper: OrientationHelper =
        OrientationHelper.createHorizontalHelper(this)

    private val scaleFactor = 0.9f

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (state.itemCount == 0) {
            removeAndRecycleAllViews(recycler)
            return
        }
        fill(recycler)
    }

    override fun onScrollStateChanged(state: Int) {
        when (state) {
            RecyclerView.SCROLL_STATE_IDLE -> {
                if (paginationEnabled) {
                    currentPosition = calculateCurrentTopPosition()
                }
            }
        }
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        val pendingScrollOffset = totalScroll + dx
        if (!paginationEnabled || isPendingScrollOutOfRange(pendingScrollOffset)) {
            totalScroll = getScrollOffsetWithinScrollBounds(pendingScrollOffset)
            fill(recycler)
            return dx
        }
        totalScroll = if (dx > 0) {
            getXiForPosition(currentPosition + 1)
        } else {
            getXiForPosition(currentPosition - 1)
        }
        fill(recycler)
        return 0
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun isAutoMeasureEnabled(): Boolean {
        return true
    }

    internal fun getCenterViewOffset(): Int {
        return getCurrentScrollPosition().roundToInt() * width - totalScroll
    }

    private fun isPendingScrollOutOfRange(pendingScrollOffset: Int): Boolean {
        return pendingScrollOffset <= getXiForPosition(currentPosition + 1)
                && pendingScrollOffset >= getXiForPosition(currentPosition - 1)
    }

    private fun fill(recycler: RecyclerView.Recycler) {
        detachAndScrapAttachedViews(recycler)
        val bottomCardIndex = getBottomCardIndex()
        val topCardIndex = getTopCardIndex()
        val lastButOneIndex = bottomCardIndex - 1
        for (index in lastButOneIndex downTo topCardIndex) {
            val view = recycler.getViewForPosition(index)
            drawCards(index, view, lastButOneIndex == index)
        }
        if (!paginationEnabled) {
            currentPosition = calculateCurrentTopPosition()
        }
    }

    private fun getBottomCardIndex(): Int {
        return min(itemCount, calculateCurrentTopPosition() + (maxCards + 1))
    }

    private fun getTopCardIndex(): Int {
        return max(0, currentPosition - 1)
    }

    private fun calculateCurrentTopPosition(): Int {
        if (width == 0) {
            return 0
        }
        return ceil((totalScroll / width).toFloat()).toInt()
    }

    private fun drawCards(index: Int, view: View, shouldFadeInOut: Boolean) {
        val position = getXiForPosition(index) - totalScroll
        addView(view)
        measureChildWithMargins(view, 0, 0)
        val top = paddingTop
        val bottom = top + orientationHelper.getDecoratedMeasurementInOther(view)
        layoutDecoratedWithMargins(view, 0, top, width, bottom)

        if (shouldFadeInOut && width != 0) {
            val visibleItems = maxCards + 1
            val customAlphaCardStartPosition = getXiForPosition(index + 1)
            val customAlphaCardEndPosition = totalScroll.toFloat() + visibleItems * width
            view.alpha = abs(customAlphaCardEndPosition - customAlphaCardStartPosition) / width
        } else {
            view.alpha = 1f
        }
        if (position <= 0) {
            translateLeftItemsFromCenter(view, position.toFloat())
        } else {
            translateRightItemsFromCenter(index, view)
        }
        setCardElevation(view, index)
    }

    private fun translateRightItemsFromCenter(index: Int, view: View) {
        val translation = if (width == 0) {
            0f
        } else {
            (getXiForPosition(index) - totalScroll) / width.toFloat()
        }
        if (translation + 1 != 0f && width != 0) {
            val viewScale = 1 - ((1f - 1f / (translation + 1)) * (1f - scaleFactor))
            val scaledHeight = viewScale * height
            val scaledWidth = viewScale * width
            val calculatedTranslationX = translation * 15f + abs(scaledWidth - width) / 2f
            val calculatedTranslationY = translation * 10f + abs(scaledHeight - height) / 2f
            with(view) {
                scaleY = viewScale
                scaleX = viewScale
                translationX = calculatedTranslationX
                translationY = calculatedTranslationY
            }
        } else {
            with(view) {
                scaleY = 1.0f
                scaleX = 1.0f
                translationX = translation * 15f
                translationY = translation * 10f
            }
        }

    }

    private fun translateLeftItemsFromCenter(view: View, itemPosition: Float) {
        with(view) {
            translationX = itemPosition
            translationY = 0f
            scaleX = 1.0f
            scaleY = 1.0f
        }
    }

    private fun getScrollOffsetWithinScrollBounds(scrollOffset: Int): Int {
        return max(0, min(scrollOffset, getMaxScrollFromCenterOffset()))
    }

    private fun setCardElevation(view: View, index: Int) {
        val bottomCardIndex = getBottomCardIndex()
        val elevation = abs(index - bottomCardIndex) + 3f
        ViewCompat.setElevation(view, elevation)
    }

    private fun getCurrentScrollPosition(): Float {
        val fullScrollSize = getMaxScrollOffset()
        return if (fullScrollSize == 0 || width == 0) {
            0f
        } else {
            totalScroll.toFloat() / width
        }
    }

    private fun getXiForPosition(position: Int): Int {
        return position * width
    }

    private fun getMaxScrollFromCenterOffset(): Int {
        return (itemCount - 1) * width
    }

    private fun getMaxScrollOffset(): Int {
        return width * (itemCount - 1)
    }
}