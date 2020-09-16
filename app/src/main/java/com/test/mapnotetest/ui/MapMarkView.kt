package com.test.mapnotetest.ui

import android.content.Context
import com.test.mapnotetest.R

class MapMarkView(context: Context, var name: String) : androidx.appcompat.widget.AppCompatImageView(context) {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setImageResource(R.drawable.map_marker_1)
    }
}