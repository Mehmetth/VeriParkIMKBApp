package com.mehmetpetek.veriparkimkb.view.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.mehmetpetek.veriparkimkb.R
import com.mehmetpetek.veriparkimkb.model.req.ReqStockItem
import com.mehmetpetek.veriparkimkb.model.res.ResSerStockItemDetail
import com.mehmetpetek.veriparkimkb.utils.AESHelper
import com.mehmetpetek.veriparkimkb.utils.CustomMarker
import com.mehmetpetek.veriparkimkb.viewmodel.StockDetailViewModel
import kotlinx.android.synthetic.main.activity_detail_view.*
import kotlinx.android.synthetic.main.stockadapter_rv_item.view.*

class DetailViewActivity : AppCompatActivity() {

    private lateinit var viewModel : StockDetailViewModel
    private var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_view)

        id = intent?.extras?.getInt("id").toString()

        viewModel = ViewModelProvider(this)[StockDetailViewModel::class.java]
        viewModel.refreshData(ReqStockItem(id=AESHelper.encrypt(id.toByteArray())))
    }

    override fun onStart() {
        super.onStart()

        viewModel.refreshData(ReqStockItem(id=id))
        observeStockItemDetailLiveData()
    }
    private fun observeStockItemDetailLiveData(){
        viewModel.data.observe(this, Observer { data-> data?.let {
            set_symbol.text = AESHelper.decrypt(data.symbol.toByteArray())
            set_price.text = data.price.toString()
            set_difference.text = data.difference.toString()
            set_volume.text = data.volume.toString()
            set_buying.text = data.bid.toString()
            set_ales.text = data.offer.toString()

            set_daily_low_symbol.text = data.lowest.toString()
            set_daily_high_symbol.text = data.highest.toString()
            set_piece_symbol.text = data.count.toString()
            set_ceiling_symbol.text = data.maximum.toString()
            set_base_symbol.text = data.minimum.toString()

            if (data.isDown) set_change_symbol.setImageResource(R.drawable.arrow_down)
            else set_change_symbol.setImageResource(R.drawable.arrow_up)

            setUpLineChart(data)
        } })
        viewModel.dataError.observe(this, Observer { error -> error?.let {} })
        viewModel.dataLoading.observe(this, Observer { loading-> loading?.let {}})
    }
    private fun setUpLineChart(data: ResSerStockItemDetail ) {
        val entries = ArrayList<Entry>()

        data.graphicData.forEach {
            entries.add(Entry(it.day.toFloat(),it.value.toFloat()))
        }

        val vl = LineDataSet(entries, "")

        vl.enableDashedLine(10f,10f,0f)
        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.fillColor = Color.parseColor("#F99278")
        vl.setCircleColor(R.color.black)
        vl.setColor(R.color.black)
        vl.lineWidth = 3f
        vl.circleRadius = 3f

        stock_item_lineChart.xAxis.labelRotationAngle = 0f

        stock_item_lineChart.data = LineData(vl)

        stock_item_lineChart.axisRight.isEnabled = false
        stock_item_lineChart.xAxis.axisMaximum = 20+0.1f

        stock_item_lineChart.setTouchEnabled(true)
        stock_item_lineChart.setPinchZoom(true)

        stock_item_lineChart.setNoDataText(getString(R.string.no_data))

        stock_item_lineChart.animateX(1000, Easing.EaseInExpo)

        val markerView = CustomMarker(this@DetailViewActivity, R.layout.marker_view)
        stock_item_lineChart.marker = markerView


        val yAxis = stock_item_lineChart.axisLeft
        val limitLine = LimitLine(data.maximum.toFloat(), getString(R.string.upper_limit))
        limitLine.lineWidth = 4f;
        limitLine.enableDashedLine(10f, 10f, 0f);
        limitLine.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP;
        limitLine.textSize = 10f;
        yAxis.addLimitLine(limitLine)
    }
}