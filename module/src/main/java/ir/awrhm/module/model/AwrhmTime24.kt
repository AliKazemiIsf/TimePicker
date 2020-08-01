package ir.awrhm.module.model

import ir.awrhm.module.TimeData

data class AwrhmTime24(
  override val hour: Int,
  override val minute: Int
) : AwrhmTime {

  companion object {
    @JvmStatic
    val DEFAULT = AwrhmTime24(12, 30)
  }

  init {
    check(hour in TimeData.HOURS_24) { "Invalid hour. Must be between 0 and 23" }
    check(minute in TimeData.MINUTES) { "Invalid minute. Must be between 0 and 59" }
  }

  override fun getType() = AwrhmTimeType.HOUR_24

  override fun toString() = String.format("%02d:%02d", hour, minute)
}