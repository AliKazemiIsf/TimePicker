package ir.awrhm.module

import ir.awrhm.module.model.AwrhmTime12

internal object TimeData {
  val HOURS_24 = (7..16)

  val HOURS_12 = (7..16)

  val MINUTES = (0..59)

  val PM_AM = AwrhmTime12.PmAm.values().map { it.name }
}