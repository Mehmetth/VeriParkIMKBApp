package com.mehmetpetek.veriparkimkb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.mehmetpetek.veriparkimkb.R
import com.mehmetpetek.veriparkimkb.model.res.ResSerStock
import com.mehmetpetek.veriparkimkb.model.res.ResSerStockItem
import com.mehmetpetek.veriparkimkb.utils.AESHelper
import kotlinx.android.synthetic.main.stockadapter_rv_item.view.*

class StockAdapter(private val onClickListener: OnClickListener, var dataList: ArrayList<ResSerStockItem>): RecyclerView.Adapter<StockAdapter.DataViewHolder> (),
    Filterable {
    class DataViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.stockadapter_rv_item,parent,false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        if(position % 2 == 0) holder.view.titles_layout.setBackgroundResource(R.color.fragment_item_bg)
        else holder.view.titles_layout.setBackgroundResource(R.color.white)

        holder.view.symbol.text = AESHelper.decrypt(dataList[position].symbol.toByteArray())
        holder.view.price.text = dataList[position].price.toString()
        holder.view.difference.text = dataList[position].difference.toString()
        holder.view.volume.text = (Math.round(dataList[position].volume * 1000.0) / 1000.0).toString()
        holder.view.buying.text = dataList[position].bid.toString()
        holder.view.sales.text = dataList[position].offer.toString()

        if (dataList[position].isDown) holder.view.arrow.setImageResource(R.drawable.arrow_down)
        else holder.view.arrow.setImageResource(R.drawable.arrow_up)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(dataList[position])
        }
    }

    fun updateDataList(newDataList: ResSerStock) {
        dataList.clear()
        dataList.addAll(newDataList.stocks)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
                dataList = filterResults.values as ArrayList<ResSerStockItem>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = Filter.FilterResults()
                filterResults.values = if (queryString==null || queryString.isEmpty())
                    dataList
                else
                    dataList.filter {
                        AESHelper.decrypt(it.symbol.toByteArray()).toLowerCase().contains(queryString)
                    }
                return filterResults
            }
        }
    }

    class OnClickListener(val clickListener: (stockItem: ResSerStockItem) -> Unit) {
        fun onClick(stockItem: ResSerStockItem) = clickListener(stockItem)
    }
}