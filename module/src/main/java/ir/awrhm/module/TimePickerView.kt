package ir.awrhm.module

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import ir.awrhm.module.model.AwrhmTime
import ir.awrhm.module.model.AwrhmTime12
import ir.awrhm.module.model.AwrhmTime24
import ir.awrhm.module.model.AwrhmTimeType.HOUR_12
import ir.awrhm.module.model.AwrhmTimeType.HOUR_24
import ir.awrhm.module.recycler.ValuesAdapter
import kotlinx.android.synthetic.main.cmtp_timepicker_view.view.*

class TimePickerView @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  private val recyclerHoursAdapter by lazy { ValuesAdapter() }
  private val recyclerMinutesAdapter by lazy { ValuesAdapter() }
  private val recyclerPmAmAdapter by lazy { ValuesAdapter() }

  private val recyclerHoursLayoutManager by lazy { LinearLayoutManager(context, VERTICAL, false) }
  private val recyclerMinutesLayoutManager by lazy { LinearLayoutManager(context, VERTICAL, false) }
  private val recyclerPmAmLayoutManager by lazy { LinearLayoutManager(context, VERTICAL, false) }

  private val recyclerHoursSnapHelper by lazy { LinearSnapHelper() }
  private val recyclerMinutesSnapHelper by lazy { LinearSnapHelper() }
  private val recyclerPmAmSnapHelper by lazy { LinearSnapHelper() }

  private var time: AwrhmTime = AwrhmTime24.DEFAULT

  init {
    val view = inflate(
      ContextThemeWrapper(context, R.style.ViewTheme),
      R.layout.cmtp_timepicker_view,
      this
    )
    layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
    setupRecyclers()
  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    val padding = measuredHeight / 2
    cmtpRecyclerHours.setPadding(0, padding, 0, padding)
    cmtpRecyclerMinutes.setPadding(0, padding, 0, padding)
    cmtpRecyclerPmAm.setPadding(0, padding, 0, padding)
  }

  private fun setupRecyclers() {
    cmtpRecyclerHours.apply {
      setHasFixedSize(true)
      adapter = recyclerHoursAdapter
      layoutManager = recyclerHoursLayoutManager
      recyclerHoursSnapHelper.attachToRecyclerView(this)
    }
    cmtpRecyclerMinutes.apply {
      setHasFixedSize(true)
      adapter = recyclerMinutesAdapter
      layoutManager = recyclerMinutesLayoutManager
      recyclerMinutesSnapHelper.attachToRecyclerView(this)
    }
    cmtpRecyclerPmAm.apply {
      setHasFixedSize(true)
      adapter = recyclerPmAmAdapter
      layoutManager = recyclerPmAmLayoutManager
      recyclerPmAmSnapHelper.attachToRecyclerView(this)
    }
    setupRecyclersData()
  }

  private fun setupRecyclersData() {
    val hours = when (time.getType()) {
      HOUR_12 -> TimeData.HOURS_12
      HOUR_24 -> TimeData.HOURS_24
    }
    cmtpRecyclerHours.apply {
      recyclerHoursAdapter.setItems(hours.map { String.format("%02d", it) })
      recyclerHoursLayoutManager.scrollToPosition(hours.indexOf(time.hour))
      smoothScrollBy(0, 1)
    }
    cmtpRecyclerMinutes.apply {
      val minutes = TimeData.MINUTES
      recyclerMinutesAdapter.setItems(minutes.map { String.format("%02d", it) })
      recyclerMinutesLayoutManager.scrollToPosition(minutes.indexOf(time.minute))
      smoothScrollBy(0, 1)
    }
    cmtpRecyclerPmAm.apply {
      recyclerPmAmAdapter.setItems(TimeData.PM_AM)
      visibility = when (time.getType()) {
        HOUR_12 -> {
          recyclerPmAmLayoutManager.scrollToPosition((time as AwrhmTime12).pmAm.ordinal)
          smoothScrollBy(0, 1)
          VISIBLE
        }
        HOUR_24 -> GONE
      }
    }
  }

  /**
   * Set time with 24-Hour or 12-Hour format.
   */
  fun setTime(initialTime: AwrhmTime) {
    time = initialTime
    setupRecyclersData()
  }

  /**
   * Get selected time in 24-Format.
   * @throws IllegalStateException will be thrown if CmtpTimePickerView has been initialised with 12-Hour format.
   */
  fun getTime24(): AwrhmTime24 {
    check(time.getType() == HOUR_24) { "Can't retrieve time in 24-Hour format. TimePicker view was initialised with 12-Hour format." }

    val hoursView = recyclerHoursSnapHelper.findSnapView(recyclerHoursLayoutManager)
    val minutesView = recyclerMinutesSnapHelper.findSnapView(recyclerMinutesLayoutManager)

    if (hoursView == null || minutesView == null)
      throw IllegalStateException("TimePicker view has not been initialized yet.")

    val hoursIndex = recyclerHoursLayoutManager.getPosition(hoursView)
    val minutesIndex = recyclerMinutesLayoutManager.getPosition(minutesView)

    return AwrhmTime24(
      recyclerHoursAdapter.getItems()[hoursIndex].toInt(),
      recyclerMinutesAdapter.getItems()[minutesIndex].toInt()
    )
  }

  /**
   * Get selected time in 12-Format.
   * @throws IllegalStateException will be thrown if CmtpTimePickerView has been initialised with 24-Hour format.
   */
  fun getTime12(): AwrhmTime12 {
    check(time.getType() == HOUR_12) { "Can't retrieve time in 12-Hour format. TimePicker view was initialised with 24-Hour format." }

    val hoursView = recyclerHoursSnapHelper.findSnapView(recyclerHoursLayoutManager)
    val minutesView = recyclerMinutesSnapHelper.findSnapView(recyclerMinutesLayoutManager)
    val pmAmView = recyclerPmAmSnapHelper.findSnapView(recyclerPmAmLayoutManager)

    if (hoursView == null || minutesView == null || pmAmView == null)
      throw IllegalStateException("TimePicker view has not been initialized yet.")

    val hoursIndex = recyclerHoursLayoutManager.getPosition(hoursView)
    val minutesIndex = recyclerMinutesLayoutManager.getPosition(minutesView)
    val pmAmIndex = recyclerPmAmLayoutManager.getPosition(pmAmView)

    return AwrhmTime12(
      recyclerHoursAdapter.getItems()[hoursIndex].toInt(),
      recyclerMinutesAdapter.getItems()[minutesIndex].toInt(),
      AwrhmTime12.PmAm.values()[pmAmIndex]
    )
  }

  /**
   * Get time picker type.
   */
  fun getType() = time.getType()
}