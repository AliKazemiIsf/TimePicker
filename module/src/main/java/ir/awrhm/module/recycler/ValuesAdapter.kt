package ir.awrhm.module.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

internal class ValuesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private val items = mutableListOf<String>()

  fun setItems(items: List<String>) {
    this.items.clear()
    this.items.addAll(items)
    notifyDataSetChanged()
  }

  fun getItems() = items

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    ValueViewHolder(
      ValueView(
        parent.context
      )
    )

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    (holder as ValueViewHolder).view.bind(items[position])
  }

  override fun getItemCount() = items.size

  internal class ValueViewHolder(val view: ValueView) : RecyclerView.ViewHolder(view)
}

