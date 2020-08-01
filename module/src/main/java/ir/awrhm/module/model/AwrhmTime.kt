package ir.awrhm.module.model

interface AwrhmTime {
  val hour: Int
  val minute: Int

  fun getType(): AwrhmTimeType
}