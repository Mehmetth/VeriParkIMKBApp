package com.mehmetpetek.veriparkimkb.view.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mehmetpetek.veriparkimkb.R
import com.mehmetpetek.veriparkimkb.adapter.StockAdapter
import com.mehmetpetek.veriparkimkb.model.req.ReqStock
import com.mehmetpetek.veriparkimkb.utils.AESHelper
import com.mehmetpetek.veriparkimkb.utils.Constants
import com.mehmetpetek.veriparkimkb.view.activity.DetailViewActivity
import com.mehmetpetek.veriparkimkb.viewmodel.StockViewModel
import kotlinx.android.synthetic.main.fragment_stocks_indices.*
import kotlinx.android.synthetic.main.fragment_stocks_indices.view.*

class DecreasingFragment : Fragment() {

    private lateinit var viewModel : StockViewModel
    private lateinit var recyclerViewAdapter: StockAdapter

    private lateinit var recyclerView: RecyclerView
    private lateinit var search_editText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_stocks_indices, container, false)


        recyclerViewAdapter = StockAdapter(StockAdapter.OnClickListener{stockItem ->
            activity?.let{
                val intent = Intent (it, DetailViewActivity::class.java)
                intent.putExtra("id",stockItem.id)
                it.startActivity(intent)
            }
        },arrayListOf())

        viewModel = ViewModelProviders.of(this).get(StockViewModel::class.java)
        viewModel.refreshData(ReqStock(period = AESHelper.encrypt(Constants.Decreasing.toByteArray())))

        recyclerView = rootView.stockIndices_recyclerView
        search_editText = rootView.search_editText
        recyclerView.layoutManager= LinearLayoutManager( context)
        recyclerView.adapter = recyclerViewAdapter

        search_editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.isNullOrEmpty())
                {
                    observeLiveData()
                }
                else
                {
                    recyclerViewAdapter.getFilter().filter(s)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        return rootView
    }
    override fun onStart() {
        super.onStart()

        swipeRefreshLayout.setOnRefreshListener {
            recyclerView.visibility= View.GONE
            viewModel.refreshData(ReqStock(period = AESHelper.encrypt(Constants.Decreasing.toByteArray())))
            swipeRefreshLayout.isRefreshing=false
        }

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.data.observe(this, Observer  { data->
            data?.let {
                recyclerView.visibility= View.VISIBLE
                recyclerViewAdapter.updateDataList(data)
            }
        })
        viewModel.dataError.observe(this, Observer { error ->
            error?.let {}})
        viewModel.dataLoading.observe(this, Observer { loading->
            loading?.let {
            }

        })
    }
}