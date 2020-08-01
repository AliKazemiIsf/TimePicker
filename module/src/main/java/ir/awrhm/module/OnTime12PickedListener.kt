package ir.awrhm.module

import androidx.annotation.NonNull
import ir.awrhm.module.model.AwrhmTime12

interface OnTime12PickedListener {

  fun onTimePicked(@NonNull time: AwrhmTime12)
}