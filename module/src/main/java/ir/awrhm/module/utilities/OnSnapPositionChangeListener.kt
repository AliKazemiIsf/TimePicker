package ir.awrhm.module.utilities

import androidx.recyclerview.widget.RecyclerView

interface OnSnapPositionChangeListener {

  fun onSnapPositionChange(position: Int, rv: RecyclerView)
}