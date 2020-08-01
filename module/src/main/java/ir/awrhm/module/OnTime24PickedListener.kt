package ir.awrhm.module

import androidx.annotation.NonNull
import ir.awrhm.module.model.AwrhmTime24

interface OnTime24PickedListener {

  fun onTimePicked(@NonNull time: AwrhmTime24)
}