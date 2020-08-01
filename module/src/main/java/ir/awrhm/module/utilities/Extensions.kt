package ir.awrhm.module.utilities

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import ir.awrhm.module.TimeDialogFragment
import ir.awrhm.module.OnTime12PickedListener
import ir.awrhm.module.OnTime24PickedListener
import ir.awrhm.module.model.AwrhmTime12
import ir.awrhm.module.model.AwrhmTime24
import java.util.Calendar

fun TimeDialogFragment.setOnTime12PickedListener(listener: (AwrhmTime12) -> Unit) {
  this.setOnTime12PickedListener(object : OnTime12PickedListener {
    override fun onTimePicked(time: AwrhmTime12) = listener(time)
  })
}

fun TimeDialogFragment.setOnTime24PickedListener(listener: (AwrhmTime24) -> Unit) {
  this.setOnTime24PickedListener(object : OnTime24PickedListener {
    override fun onTimePicked(time: AwrhmTime24) = listener(time)
  })
}

// Calculates the number of days in a certain month and year.
fun getNumberOfDays(month: Int, year: Int): Int {
  val calendar = Calendar.getInstance()
  calendar.isLenient = false
  calendar.set(year, month - 1, 1)
  calendar.add(Calendar.MONTH, 1)
  calendar.add(Calendar.DAY_OF_MONTH, -1)

  return calendar.get(Calendar.DAY_OF_MONTH)
}

fun RecyclerView.attachSnapHelperWithListener(
  snapHelper: SnapHelper,
  behavior: SnapOnScrollListener.Behavior = SnapOnScrollListener.Behavior.NOTIFY_ON_SCROLL,
  onSnapPositionChangeListener: OnSnapPositionChangeListener
) {
  snapHelper.attachToRecyclerView(this)
  val snapOnScrollListener = SnapOnScrollListener(snapHelper, behavior, onSnapPositionChangeListener)
  addOnScrollListener(snapOnScrollListener)
}

fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
  val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
  val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
  return layoutManager.getPosition(snapView)
}