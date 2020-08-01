package ir.awrhm.module.model

import ir.awrhm.module.TimeData
import ir.awrhm.module.model.AwrhmTimeType.HOUR_12

data class AwrhmTime12(
  override val hour: Int,
  override val minute: Int,
  val pmAm: PmAm
) : AwrhmTime {

  companion object {
    @JvmStatic
    val DEFAULT = AwrhmTime12(6, 30, PmAm.PM)
  }

  init {
    check(hour in TimeData.HOURS_12) { "Invalid hour. Must be between 1 and 12" }
    check(minute in TimeData.MINUTES) { "Invalid minute. Must be between 0 and 59" }
  }

  override fun getType() = HOUR_12

  override fun toString() = String.format("%02d:%02d:%s", hour, minute, pmAm.name)

  enum class PmAm { PM, AM }
}