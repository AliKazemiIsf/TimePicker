package ir.awrhm.module.recycler

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import ir.awrhm.module.R
import kotlinx.android.synthetic.main.cmtp_value_view.view.*

internal class ValueView @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

  init {
    inflate(context, R.layout.cmtp_value_view, this)
  }

  fun bind(value: String) {
    cmtpTimeNumberValue.text = value
  }
}